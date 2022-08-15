package com.heech.hellcoding.core.education.quiz.domain;

import com.heech.hellcoding.core.common.entity.BaseTimeEntity;
import com.heech.hellcoding.core.education.curriculum.domain.Curriculum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_quiz")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_quiz_id")
    private Long id;

    private String explanation;

}
