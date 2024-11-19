package com.wanna_v_local.controller;

import com.wanna_v_local.dto.request.ReviewRequestDTO;
import com.wanna_v_local.service.OCRService;
import com.wanna_v_local.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;

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

    private static void printErrorLog(BindingResult result) {
        log.info("{}", "*".repeat(20));
        for (FieldError fieldError : result.getFieldErrors()) {
            log.error("{}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.info("{}", "*".repeat(20));
    }
}
