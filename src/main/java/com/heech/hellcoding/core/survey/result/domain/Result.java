package com.heech.hellcoding.core.survey.result.domain;

import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "questionnaire_result")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result {

    @Id @GeneratedValue
    @Column(name = "result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "response_content")
    private String content;
    private LocalDateTime responseDate;

    //===생성 메서드===//

    //===수정 메서드===//
}
