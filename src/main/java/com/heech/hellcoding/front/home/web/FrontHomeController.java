package com.heech.hellcoding.front.home.web;

import com.heech.hellcoding.core.common.annotation.Login;
import com.heech.hellcoding.core.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FrontHomeController {

    /**
     * 홈 화면
     * @param loginMember
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String home(@Login Member loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "front/home/home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "front/home/loginHome";
    }

}
