package com.heech.hellcoding.user.sign.web;

import com.heech.hellcoding.member.domain.Member;
import com.heech.hellcoding.session.SessionConst;
import com.heech.hellcoding.session.SessionManager;
import com.heech.hellcoding.user.sign.service.LoginService;
import com.heech.hellcoding.user.sign.web.form.LoginForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user/sign")
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping(value = "/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "front/user/sign/loginForm";
    }

    @PostMapping(value = "/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectUrl,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "front/user/sign/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            log.info("bindingResult={}", bindingResult);
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "front/user/sign/loginForm";
        }

        //로그인 성공처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession(true);
        //세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectUrl;
    }

    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        //세션이 있으면 있는 세션 반환, 없으면 null 반환
        HttpSession session = request.getSession(false);
        if (session == null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
