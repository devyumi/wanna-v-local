package com.wanna_v_local.service;

import com.wanna_v_local.domain.Review;
import com.wanna_v_local.dto.request.ReviewRequestDTO;
import com.wanna_v_local.dto.response.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ReviewRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewResponseDTO findReviews(ReviewRequestDTO reviewRequestDTO) {
        return ReviewResponseDTO.builder()
                .reviewRequestDto(reviewRequestDTO)
                .reviews(reviewRepository.findAll())
                .total(reviewRepository.count())
                .build();
    }

    @Transactional
    public void updateReviewActive(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        reviewRepository.save(review.get());
    }
}
