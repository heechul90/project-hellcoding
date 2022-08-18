package com.heech.hellcoding.core.education.quiz.domain;

import com.heech.hellcoding.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "education_quiz_result")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizResult {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_quiz_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_quiz_question_option_id")
    private QuizQuestionOption option;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member respondent;

    private String responseContent;
    private LocalDateTime responseDate;


}
