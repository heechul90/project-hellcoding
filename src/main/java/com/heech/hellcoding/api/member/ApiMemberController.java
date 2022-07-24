package com.heech.hellcoding.api.member;

import com.heech.hellcoding.api.member.request.CreateMemberRequest;
import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.api.member.response.CreateMemberResponse;
import com.heech.hellcoding.api.member.response.UpdateMemberResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
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
        List<MemberDto> members = content.getContent().stream()
                .map(member -> MemberDto.builder()
                        .memberId(member.getId())
                        .memberName(member.getName())
                        .email(member.getEmail())
                        .birthDate(member.getBirthDate())
                        .author(member.getAuthorCode().getCodeName())
                        .gender(member.getGenderCode().getCodeName())
                        .phoneNumber(member.getMobile().fullPhoneNumber())
                        .address(member.getAddress().fullAddress())
                        .build()
                )
                .collect(Collectors.toList());
        return JsonResult.OK(members);
    }

    /**
     * 회원 조회(단건)
     */
    @GetMapping(value = "/{memberId}")
    public JsonResult findMember(@PathVariable("memberId") Long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberDto member = MemberDto.builder()
                .memberId(findMember.getId())
                .memberName(findMember.getName())
                .email(findMember.getEmail())
                .birthDate(findMember.getBirthDate())
                .author(findMember.getAuthorCode().getCodeName())
                .gender(findMember.getGenderCode().getCodeName())
                .phoneNumber(findMember.getMobile().fullPhoneNumber())
                .address(findMember.getAddress().fullAddress())
                .build();
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
