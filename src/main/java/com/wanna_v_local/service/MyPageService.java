package com.wanna_v_local.service;

import com.wanna_v_local.domain.Address;
import com.wanna_v_local.domain.User;
import com.wanna_v_local.dto.MyPageUpdateDTO;
import com.wanna_v_local.dto.response.MyLikesResponseDTO;
import com.wanna_v_local.dto.response.MyPageResponseDTO;
import com.wanna_v_local.repository.UserRepository;
import com.wanna_v_local.repository.mypage.query.MyLikesDTORepository;
import com.wanna_v_local.repository.mypage.query.MyPageDTORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final MyPageDTORepository myPageDTORepository;
    private final MyLikesDTORepository myLikesDTORepository;

    /**
     * 마이페이지 메인 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public MyPageResponseDTO findMyPage(Long userId) {
        return myPageDTORepository.findMyPageById(userId);
    }

    /**
     * 마이페이지 수정
     *
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
                .address(new Address(myPageUpdateDTO.getZipCode(), myPageUpdateDTO.getRoadAddress(), myPageUpdateDTO.getLandLotAddress(), myPageUpdateDTO.getDetailAddress()))
                .referralCode(user.getReferralCode())
                .point(user.getPoint())
                .consent(user.getConsent())
                .isUnregistered(user.getIsUnregistered())
                .createdAt(user.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .unregisteredAt(user.getUnregisteredAt())
                .build());
    }

    /**
     * 마이페이지 찜 목록 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public List<MyLikesResponseDTO> findMyLikes(Long userId) {
        return myLikesDTORepository.findMyLikesById(userId);
    }
}
