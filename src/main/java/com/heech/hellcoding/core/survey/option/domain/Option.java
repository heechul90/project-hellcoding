package com.heech.hellcoding.core.survey.option.domain;

import com.heech.hellcoding.core.survey.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "questionnaire_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private int optionOrder;

    @Column(name = "option_content")
    private String content;

    //===연관관계 메서드===//
    public void addQuestion(Question question) {
        this.question = question;
    }

    //===생성 메서드===//
    /**
     * 항목 생성
     */
    @Builder(builderClassName = "createOptionBuilder", builderMethodName = "createOptionBuilder")
    public Option(int optionOrder, String content) {
        this.optionOrder = optionOrder;
        this.content = content;
    }

    //===수정 메서드===//
    /**
     * 항목 수정(option_id 있을때)
     */
    @Builder(builderClassName = "updateOptionBuilder", builderMethodName = "updateOptionBuilder")
    public void updateOption(Question question, int optionOrder, String content) {
        this.question = question;
        this.optionOrder = optionOrder;
        this.content = content;
    }

    /**
     * 항목 추가(option_id 없을때)
     */
    @Builder(builderClassName = "addOptionBuilder", builderMethodName = "addOptionBuilder")
    public Option(Question question, int optionOrder, String content) {
        this.question = question;
        this.optionOrder = optionOrder;
        this.content = content;
        this.question.getOptions().add(this);
    }
}
