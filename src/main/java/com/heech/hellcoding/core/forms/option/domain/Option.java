package com.heech.hellcoding.core.forms.option.domain;

import com.heech.hellcoding.core.forms.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "forms_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private int OptionOrder;

    @Column(name = "option_content")
    private String content;

    //===생성 메서드===//

    //===수정 메서드===//
}
