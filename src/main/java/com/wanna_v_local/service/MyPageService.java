package com.wanna_v_local.service;

import com.wanna_v_local.domain.User;
import com.wanna_v_local.dto.MyPageUpdateDTO;
import com.wanna_v_local.dto.response.MyPageResponseDTO;
import com.wanna_v_local.repository.UserRepository;
import com.wanna_v_local.repository.mypage.query.MyPageDTORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final MyPageDTORepository myPageDTORepository;

    /**
     * 마이페이지 메인 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public MyPageResponseDTO findMyPage(Long userId) {
        return myPageDTORepository.findMyPageById(userId);
    }

    /**
     * 마이페이지 수정
     * @param userId
     * @param myPageUpdateDTO
     */
    @Transactional
    public void updateMyPage(Long userId, MyPageUpdateDTO myPageUpdateDTO) {
        User user = userRepository.findById(userId).get();

        userRepository.save(User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .profile(user.getProfile())
                .email(user.getEmail())
                .name(myPageUpdateDTO.getName())
                .phone(user.getPhone())
                .zipCode(myPageUpdateDTO.getZipCode())
                .roadAddress(myPageUpdateDTO.getRoadAddress())
                .landLotAddress(myPageUpdateDTO.getLandLotAddress())
                .detailAddress(myPageUpdateDTO.getDetailAddress())
                .referralCode(user.getReferralCode())
                .point(user.getPoint())
                .consent(user.getConsent())
                .isUnregistered(user.getIsUnregistered())
                .createdAt(user.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .unregisteredAt(user.getUnregisteredAt())
                .build());
    }
}