package com.heech.hellcoding.front.user.join.web;

import com.heech.hellcoding.core.member.domain.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.service.MemberService;
import com.heech.hellcoding.core.user.join.service.JoinService;
import com.heech.hellcoding.front.user.join.form.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/join")
public class JoinController {

    private final JoinService joinService;

    /**
     * 회원가입 폼
     * @return
     */
    @GetMapping
    public String addForm(@ModelAttribute("joinForm") JoinForm form) {
        return "front/user/join/joinForm";
    }

    /**
     * 회원가입
     * @param bindingResult
     * @return
     */
    @PostMapping
    public String save(@Validated @ModelAttribute("joinForm") JoinForm form, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "front/user/join/joinForm";
        }

        Member member = new Member(
                form.getLoginId(),
                form.getPassword(),
                form.getName(),
                form.getEmail(),
                form.getBirthYear() + form.getBirthMonth() + form.getBirthDay(),
                "M".equals(form.getGenderCode()) ? GenderCode.M : GenderCode.F,
                new Mobile(form.getMobileNumberFirst(), form.getMobileNumberMiddle(), form.getMobileNumberLast()),
                new Address(form.getZipcode(), form.getAddress(), form.getDetailAddress()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Member savedMember = joinService.save(member);
        return "redirect:/";
    }

}
