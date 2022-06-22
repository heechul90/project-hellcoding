package com.heech.hellcoding.core.forms.question.domain;

import com.heech.hellcoding.core.forms.form.domain.Form;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "forms_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    @Column(name = "question_title")
    private String title;

    private int questionOrder;

    @Enumerated(EnumType.STRING)
    private Setting setting;

    //===생성 메서드===//

    //===수정 메서드===//
}
