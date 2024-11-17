package com.wanna_v_local.service;

import com.wanna_v_local.domain.Restaurant;
import com.wanna_v_local.dto.response.OCRResponseDTO;
import com.wanna_v_local.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OCRService {

    private final RestaurantRepository restaurantRepository;


    @Value("${naver.ocr.invoke_url}")
    private String url;

    @Value("${naver.ocr.secret_key}")
    private String secretKey;

    /**
     * 영수증 정보 분석 및 조회 - 식당명, 방문일자 추출
     *
     * @param file
     * @return
     */
    public OCRResponseDTO findReceiptData(MultipartFile file) throws IOException {

        try {
            RestTemplate restTemplate = new RestTemplate();

            //Request - Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("X-OCR-SECRET", secretKey);

            //Request - Body
            Map<String, Object> message = new HashMap<>();
            message.put("version", "V2");
            message.put("requestId", UUID.randomUUID().toString());
            message.put("timestamp", 0);
            message.put("images", new Object[]{new HashMap<String, Object>() {{
                put("format", "jpg");
                put("name", "receipt");
            }}});

            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("message", message);
            body.add("file", fileResource);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
            return restTemplate.postForObject(url, request, OCRResponseDTO.class);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * 영수증 정보에서 가져온 식당명을 기반으로 하여 DB에 저장된 식당 조회
     *
     * @param name
     * @return
     */
    public Restaurant findCorrectRestaurant(String name, String subName) {

        //지점명 없을 경우 기본 상호명으로 식당 검색
        if (subName == null) {
            return restaurantRepository.findByNameContaining(name);
        } else {

            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher matcher = pattern.matcher(subName);

            if (matcher.find()) {
                subName = matcher.group(1);
            }
            return restaurantRepository.findByNameContainingAndNameContaining(name, subName);
        }
    }
}