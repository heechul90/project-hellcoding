package com.heech.hellcoding.front.user.join.web;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/join")
public class JoinController {

    private final MemberService memberService;

    /**
     * 회원가입 폼
     * @param member
     * @return
     */
    @GetMapping
    public String addForm(@ModelAttribute("member") Member member) {
        return "front/user/join/joinForm";
    }

    /**
     * 회원가입
     * @param member
     * @param bindingResult
     * @return
     */
    @PostMapping
    public String save(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "front/user/join/joinForm";
        }
        Member savedMember = memberService.save(member);
        return "redirect:/";
    }

}
