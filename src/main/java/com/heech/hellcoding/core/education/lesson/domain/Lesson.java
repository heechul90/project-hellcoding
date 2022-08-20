package com.heech.hellcoding.core.education.lesson.domain;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.common.entity.BaseEntity;
import com.heech.hellcoding.core.education.lesson.dto.UpdateLessonParam;
import com.heech.hellcoding.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_lesson")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_lesson_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "lesson_title")
    private String title;

    private boolean isEditor;
    private String lessonIntroduction;
    private String lessonGoal;
    private String lessonTarget;

    private boolean isMaterial;
    private String materialIntroduction;
    private String materialLink;

    @Enumerated(EnumType.STRING)
    private AgeCode ageCode;
    private Integer minAge;
    private Integer maxAge;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member teacher;
    private String teacherIntroduction;

    private int inquiryCount;

    private boolean isDelete;

    //===생성 메서드===//
    /**
     * 레슨 생성
     */
    @Builder(builderClassName = "createLessonBuilder", builderMethodName = "createLessonBuilder")
    public Lesson(LessonType lessonType, Category category, String title, boolean isEditor, String lessonIntroduction, String lessonGoal, String lessonTarget, boolean isMaterial, String materialIntroduction, String materialLink, AgeCode ageCode, Integer minAge, Integer maxAge, Level level, Member teacher, String teacherIntroduction) {
        this.lessonType = lessonType;
        this.category = category;
        this.title = title;
        this.isEditor = isEditor;
        this.lessonIntroduction = lessonIntroduction;
        this.lessonGoal = lessonGoal;
        this.lessonTarget = lessonTarget;
        this.isMaterial = isMaterial;
        this.materialIntroduction = materialIntroduction;
        this.materialLink = materialLink;
        this.ageCode = ageCode;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.level = level;
        this.teacher = teacher;
        this.teacherIntroduction = teacherIntroduction;
        this.inquiryCount = 0;
        this.isDelete = false;
    }

    /**
     * 레슨 수정
     */
    @Builder(builderMethodName = "updateLessonBuilder", builderClassName = "updateLessonBuilder")
    public void updateLesson(UpdateLessonParam param) {

    }

    public void deleteLesson() {
        this.isDelete = true;
    }
}
