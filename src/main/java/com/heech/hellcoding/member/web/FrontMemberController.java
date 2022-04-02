package com.heech.hellcoding.member.web;

import com.heech.hellcoding.member.domain.Member;
import com.heech.hellcoding.member.service.MemberService;
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
@RequestMapping(value = "/front/member")
public class FrontMemberController {

    private final MemberService memberService;

    @GetMapping(value = "/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "front/member/addMemberForm";
    }

    @PostMapping(value = "/add")
    public String save(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "front/member/addMemberForm";
        }
        memberService.save(member);
        return "redirect:/";
    }



}
