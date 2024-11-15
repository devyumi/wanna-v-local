package com.wanna_v_local.service;

import com.wanna_v_local.dto.request.OCRRequestDTO;
import com.wanna_v_local.dto.response.OCRResponseDTO;
import com.wanna_v_local.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OCRService {

    private final RestaurantRepository restaurantRepository;


    @Value("${naver.ocr.invoke_url}")
    private String url;

    @Value("${naver.ocr.secret_key}")
    private String secretKey;

    /**
     * 영수증 정보 조회 - 식당명, 주소, 방문일자 추출
     *
     * @param ocrRequest
     * @return
     */
    public OCRResponseDTO findReceiptData(OCRRequestDTO ocrRequest) {
        RestTemplate restTemplate = new RestTemplate();

        //Request - Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", secretKey);

        //Request - Body
        HttpEntity<OCRRequestDTO> body = new HttpEntity<>(ocrRequest, headers);

        return restTemplate.postForObject(url, body, OCRResponseDTO.class);
    }
}