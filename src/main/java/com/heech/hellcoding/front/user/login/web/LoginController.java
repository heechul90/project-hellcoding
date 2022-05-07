package com.heech.hellcoding.front.user.login.web;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.session.SessionConst;
import com.heech.hellcoding.core.session.SessionManager;
import com.heech.hellcoding.core.user.login.service.LoginService;
import com.heech.hellcoding.front.user.login.form.LoginForm;
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
@RequestMapping(value = "/user")
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    /**
     * 로그인 폼
     * @param form
     * @return
     */
    @GetMapping(value = "/login")
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "front/user/login/loginForm";
    }

    /**
     * 로그인
     * @param form
     * @param bindingResult
     * @param redirectUrl
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectUrl,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "front/user/login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail");
            log.info("bindingResult={}", bindingResult);
            return "front/user/login/loginForm";
        }

        //로그인 성공처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession(true);
        //세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectUrl;
    }

    /**
     * 로그아웃
     * @param request
     * @return
     */
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        //세션이 있으면 있는 세션 반환, 없으면 null 반환
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
