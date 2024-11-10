package com.wanna_v_local.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @GetMapping
    public String getReviews() {
        return "admin-review";
    }

    @PostMapping("/{id}/update")
    public String updateReviewActiveForm(Long id) {
        return "admin-review";
    }

}
