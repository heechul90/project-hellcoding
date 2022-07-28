package com.heech.hellcoding.api.member.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMemberResponse {

    private Long savedMemberId;

    @Builder
    public CreateMemberResponse(Long savedMemberId) {
        this.savedMemberId = savedMemberId;
    }
}
