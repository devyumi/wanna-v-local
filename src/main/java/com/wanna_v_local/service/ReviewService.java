package com.wanna_v_local.service;

import com.wanna_v_local.domain.Review;
import com.wanna_v_local.domain.ReviewTag;
import com.wanna_v_local.dto.request.FileDTO;
import com.wanna_v_local.dto.request.ReviewRequestDTO;
import com.wanna_v_local.dto.request.ReviewSaveDTO;
import com.wanna_v_local.dto.response.ReviewResponseDTO;
import com.wanna_v_local.repository.ReviewRepository;
import com.wanna_v_local.repository.ReviewTagRepository;
import com.wanna_v_local.repository.TagRepository;
import com.wanna_v_local.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final FileService fileService;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;

    @Value("${file.upload.review}")
    private String path;

    /**********************USER************************/

    /**
     * 리뷰 저장
     *
     * @param userId
     * @param reviewSaveDTO
     */
    @Transactional
    public void saveReview(Long userId, ReviewSaveDTO reviewSaveDTO) {
        String imgUrl = "";

        if (!reviewSaveDTO.getFiles().get(0).isEmpty()) {
            List<FileDTO> files = fileService.uploadFiles(reviewSaveDTO.getFiles(), path);
            imgUrl = createImageUrl(files);
        }

        Review review = reviewRepository.save(Review.builder()
                .restaurant(reviewSaveDTO.getRestaurant())
                .user(userRepository.findById(userId).get())
                .rating(reviewSaveDTO.getRating())
                .content(reviewSaveDTO.getContent())
                .image(imgUrl.equals("") ? null : imgUrl)
                .visitDate(reviewSaveDTO.getVisitDate())
                .createdAt(LocalDateTime.now())
                .build());

        for (String tagName : reviewSaveDTO.getTagNames()) {
            reviewTagRepository.save(ReviewTag.builder()
                    .review(review)
                    .tag(tagRepository.findByName(tagName))
                    .build());
        }
    }

    /**
     * DB에 저장되는 imgUrl 생성 - "url,url,url" 형식
     *
     * @param fileDTOS
     * @return
     */
    private String createImageUrl(List<FileDTO> fileDTOS) {
        StringBuilder imgUrl = new StringBuilder();
        for (FileDTO fileDTO : fileDTOS) {
            imgUrl.append(fileDTO.getUploadFileUrl()).append(",");
        }
        return imgUrl.deleteCharAt(imgUrl.length() - 1).toString();
    }

    /**
     * 리뷰 수정
     *
     * @param reviewId
     * @param reviewUpdateDTO
     */
    @Transactional
    public void updateReview(Long reviewId, ReviewSaveDTO reviewUpdateDTO) {
        String imgUrl = "";

        if (!reviewUpdateDTO.getFiles().get(0).isEmpty()) {
            List<FileDTO> files = fileService.uploadFiles(reviewUpdateDTO.getFiles(), path);
            imgUrl = createImageUrl(files);
        }

        Review originalReview = reviewRepository.findById(reviewId).get();
        List<ReviewTag> reviewTags = reviewTagRepository.findAllByReviewId(originalReview.getId());

        Review review = reviewRepository.save(Review.builder()
                .id(originalReview.getId())
                .restaurant(originalReview.getRestaurant())
                .user(originalReview.getUser())
                .rating(reviewUpdateDTO.getRating())
                .content(reviewUpdateDTO.getContent())
                .image(imgUrl.equals("") ? null : imgUrl)
                .visitDate(originalReview.getVisitDate())
                .createdAt(LocalDateTime.now())
                .build());

        if (reviewTags != null) {
            for (ReviewTag reviewTag : reviewTags) {
                reviewTagRepository.deleteById(reviewTag.getId());
            }
        }

        for (String tagName : reviewUpdateDTO.getTagNames()) {
            reviewTagRepository.save(ReviewTag.builder()
                    .review(review)
                    .tag(tagRepository.findByName(tagName))
                    .build());
        }
    }

    /**
     * 리뷰 수정 가능 여부 판별
     *
     * @param reviewId
     */
    @Transactional(readOnly = true)
    public void checkReviewUpdate(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();

        //리뷰 작성 후 3일 이내만 수정 가능
        if (review.getCreatedAt().plusDays(3).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("리뷰 작성 3일 이후부터 수정할 수 없습니다.");
        }
    }

    /**********************ADMIN************************/

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
