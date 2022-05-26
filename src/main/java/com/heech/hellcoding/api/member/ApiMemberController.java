package com.heech.hellcoding.api.member;

import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberDto;
import com.heech.hellcoding.core.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members")
public class ApiMemberController {

    private final MemberService memberService;

    /**
     * 회원 목록
     */
    @GetMapping
    public JsonResult members() {
        System.out.println("ApiMemberController.members");

        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(member -> new MemberDto(
                        member.getName(),
                        member.getEmail(),
                        member.getBirthDate(),
                        member.getGenderCode() == GenderCode.M ? "남자" : "여자",
                        member.getMobile().fullPhoneNumber(),
                        member.getAddress().fullAddress()))
                .collect(Collectors.toList());

        System.out.println("collect = " + collect);

        return new JsonResult(collect.size(), collect);
    }

}
