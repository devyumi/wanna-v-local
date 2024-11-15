package com.wanna_v_local.controller;

import com.wanna_v_local.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("my")
    public String getMyPage(Model model) {
        model.addAttribute("my", myPageService.findMyPage(1L));
        return "user/mypage";
    }
}
