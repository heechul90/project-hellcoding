package com.heech.hellcoding.api.member;

import com.heech.hellcoding.api.member.request.CreateMemberRequest;
import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.api.member.response.CreateMemberResponse;
import com.heech.hellcoding.api.member.response.UpdateMemberResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberDto;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public JsonResult findMembers(MemberSearchCondition condition, Pageable pageable) {
        Page<Member> content = memberService.findMembers(condition, pageable);
        List<MemberDto> collect = content.getContent().stream()
                .map(member -> new MemberDto(
                        member.getName(),
                        member.getEmail(),
                        member.getBirthDate(),
                        member.getGenderCode() == GenderCode.M ? "남자" : "여자",
                        member.getMobile().fullPhoneNumber(),
                        member.getAddress().fullAddress()))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 회원 조회(단건)
     */
    @GetMapping(value = "/{memberId}")
    public JsonResult findMember(@PathVariable("memberId") Long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberDto member = new MemberDto(
                findMember.getName(),
                findMember.getEmail(),
                findMember.getBirthDate(),
                findMember.getGenderCode().equals(GenderCode.M) ? "남자" : "여자",
                findMember.getMobile().fullPhoneNumber(),
                findMember.getAddress().fullAddress());
        return JsonResult.OK(member);
    }

    /**
     * 회원 저장
     */
    @PostMapping
    public JsonResult saveMember(@RequestBody @Validated CreateMemberRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        Long savedMemberId = memberService.saveMember(request.toEntity());
        return JsonResult.OK(new CreateMemberResponse(savedMemberId));
    }

    /**
     * 회원 수정
     */
    @PutMapping(value = "/{memberId}")
    public JsonResult updateMember(@PathVariable("memberId") Long memberId,
                                   @RequestBody @Validated UpdateMemberRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        memberService.updateMember(memberId, request.toUpdateMemberParam());
        Member findMember = memberService.findMember(memberId);
        return JsonResult.OK(new UpdateMemberResponse(findMember.getId()));
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping(value = "/{memberId}")
    public JsonResult deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return JsonResult.OK();
    }

}
