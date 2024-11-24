package com.wanna_v_local.controller;

import com.wanna_v_local.dto.request.TagRequestDTO;
import com.wanna_v_local.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("reviews/tags")
    public String getReviewTags(TagRequestDTO tagRequestDTO, Model model) {
        model.addAttribute("tags", tagService.findTags(tagRequestDTO));
        return "admin/admin-tag";
    }
}
