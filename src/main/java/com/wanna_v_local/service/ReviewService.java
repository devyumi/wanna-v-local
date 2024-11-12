package com.wanna_v_local.service;

import com.wanna_v_local.domain.Review;
import com.wanna_v_local.dto.request.ReviewRequestDTO;
import com.wanna_v_local.dto.response.ReviewResponseDTO;
import com.wanna_v_local.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 전체 조회 (페이징 및 필터링 적용)
     *
     * @param reviewRequestDTO
     * @return
     */
    @Transactional(readOnly = true)
    public ReviewResponseDTO findReviews(ReviewRequestDTO reviewRequestDTO) {
        return ReviewResponseDTO.builder()
                .reviewRequestDto(reviewRequestDTO)
                .reviews(reviewRepository.findAll(reviewRequestDTO))
                .total(reviewRepository.count(reviewRequestDTO))
                .build();
    }

    /**
     * 리뷰 상세 조회
     *
     * @param reviewId
     * @return
     */
    @Transactional(readOnly = true)
    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("잘못된 id 값입니다."));
    }

    /**
     * 리뷰 숨기기 (== 상태 false 업데이트)
     *
     * @param reviewId
     */
    @Transactional
    public void updateReviewActiveFalse(Long reviewId, String note) {
        Review review = findReview(reviewId);
        reviewRepository.save(
                Review.builder()
                        .id(review.getId())
                        .restaurant(review.getRestaurant())
                        .user(review.getUser())
                        .rating(review.getRating())
                        .content(review.getContent())
                        .image(review.getImage())
                        .visitDate(review.getVisitDate())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .isActive(false)
                        .note(note)
                        .build());
    }

    /**
     * 리뷰 보이기 (== 상태 true 업데이트)
     *
     * @param reviewId
     */
    public void updateReviewActiveTrue(Long reviewId) {
        Review review = findReview(reviewId);
        reviewRepository.save(
                Review.builder()
                        .id(review.getId())
                        .restaurant(review.getRestaurant())
                        .user(review.getUser())
                        .rating(review.getRating())
                        .content(review.getContent())
                        .image(review.getImage())
                        .visitDate(review.getVisitDate())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .isActive(true)
                        .note(null)
                        .build());
    }
}
