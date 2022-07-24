package com.heech.hellcoding.api.member.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateMemberResponse {

    private Long updatedMemberId;

    @Builder
    public UpdateMemberResponse(Long updatedMemberId) {
        this.updatedMemberId = updatedMemberId;
    }
}
