package com.wanna_v_local.controller;

import com.wanna_v_local.dto.request.ReviewRequestDTO;
import com.wanna_v_local.dto.request.ReviewUpdateStatusDTO;
import com.wanna_v_local.service.OCRService;
import com.wanna_v_local.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final OCRService ocrService;

    /**********************USER************************/


    /**********************ADMIN************************/

    @GetMapping("admin-reviews")
    public String getReviews(ReviewRequestDTO reviewRequestDTO, Model model) {
        model.addAttribute("reviews", reviewService.findReviews(reviewRequestDTO));
        return "admin/admin-review";
    }

    @GetMapping("admin-reviews/{id}")
    public String getReview(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.findReview(id));
        model.addAttribute("reviewUpdateStatusDTO", new ReviewUpdateStatusDTO());
        return "admin/admin-review-details";
    }

    @PostMapping("admin-reviews/{id}/update-false")
    public String updateReviewFalse(@PathVariable Long id, Model model,
                                    @ModelAttribute @Validated ReviewUpdateStatusDTO reviewUpdateStatusDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("review", reviewService.findReview(id));
            printErrorLog(bindingResult);
            return "admin/admin-review-details";
        }
        reviewService.updateReviewActiveFalse(id, reviewUpdateStatusDTO.getNote());
        log.info("{}반 리뷰 숨김 완료", id);
        return "redirect:/admin-reviews";
    }

    @PostMapping("admin-reviews/{id}/update-true")
    public String updateReviewTrue(@PathVariable Long id) {
        reviewService.updateReviewActiveTrue(id);
        log.info("{}번 리뷰 게시 완료", id);
        return "redirect:/admin-reviews";
    }

    private static void printErrorLog(BindingResult result) {
        log.info("{}", "*".repeat(20));
        for (FieldError fieldError : result.getFieldErrors()) {
            log.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.info("{}", "*".repeat(20));
    }
}
