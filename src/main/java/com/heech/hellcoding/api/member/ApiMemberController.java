package com.heech.hellcoding.api.member;

import com.heech.hellcoding.api.member.request.CreateMemberRequest;
import com.heech.hellcoding.api.member.response.CreateMemberResponse;
import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.api.member.response.UpdateMemberResponse;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberDto;
import com.heech.hellcoding.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    /**
     * 회원 저장
     */
    @PostMapping
    public CreateMemberResponse saveMember(@RequestBody @Validated CreateMemberRequest request) {
        System.out.println("request.getMemberName = " + request.getMemberName());
        Member member = new Member(
                request.getLoginId(),
                request.getPassword(),
                request.getMemberName(),
                request.getEmail(),
                request.getBirthDate(),
                request.getGender().equals("M") ? GenderCode.M : GenderCode.F,
                new Mobile(request.getMobileNumberFirst(), request.getMobileNumberMiddle(), request.getMobileNumberLast()),
                new Address(request.getZipcode(), request.getAddress(), request.getDetailAddress()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Member savedMember = memberService.saveMember(member);
        return new CreateMemberResponse(savedMember.getId());
    }

    /**
     * 회원 수정
     */
    @PutMapping(value = "/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @RequestBody @Validated UpdateMemberRequest request) {

        System.out.println("id = " + id);
        memberService.updateMmeber(id, request.getPassword());
        Member findMember = memberService.findById(id).orElseGet(null);
        return new UpdateMemberResponse(findMember.getId());
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping(value = "/{id}")
    public String deleteMember(@PathVariable("id") Long id) {

        memberService.deleteMember(id);

        return "ok";

    }

}
