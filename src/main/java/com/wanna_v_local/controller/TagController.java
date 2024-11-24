package com.wanna_v_local.controller;

import com.wanna_v_local.dto.request.TagRequestDTO;
import com.wanna_v_local.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping("reviews/tags")
    public String getReviewTags(TagRequestDTO tagRequestDTO, Model model) {
        model.addAttribute("tags", tagService.findTags(tagRequestDTO));
        return "admin/admin-tag";
    }

    @PostMapping("reviews/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        log.info("{}번 태그 삭제 완료", id);
        redirectAttributes.addFlashAttribute("alertMessage", "삭제 되었습니다.");
        return "redirect:/reviews/tags";
    }
}
