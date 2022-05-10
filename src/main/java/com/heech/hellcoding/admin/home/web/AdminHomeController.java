package com.heech.hellcoding.admin.home.web;

import com.heech.hellcoding.core.common.annotation.Login;
import com.heech.hellcoding.core.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminHomeController {

    @GetMapping(value = "/admin")
    public String adminHome(@Login Member loginMember) throws Exception {
        //세션에 회원 데이터가 없으면 home
        /*if (loginMember == null) {
            return "front/home/home";
        }*/
        return "admin/home/adminHome";
    }
}
