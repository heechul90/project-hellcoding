package com.heech.hellcoding.core.survey.result.domain;

import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "questionnaire_result")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionnaireResult {

    @Id @GeneratedValue
    @Column(name = "result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Column(name = "response_content")
    private String content;
    private LocalDateTime responseDate;

    //===생성 메서드===//
    @Builder(builderClassName = "createQuestionnaireResultBuilder", builderMethodName = "createQuestionnaireResultBuilder")
    public QuestionnaireResult(Member member, Option option, String content) {
        this.member = member;
        this.option = option;
        this.content = content;
        this.responseDate = LocalDateTime.now();
    }

    //===수정 메서드===//
}
