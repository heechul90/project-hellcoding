package com.heech.hellcoding.core.education.curriculum.domain;

import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_curriculum")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ContentType")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_curriculum_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_lesson_id")
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private int sortNo;
    private String CurriculumTitle;

}
