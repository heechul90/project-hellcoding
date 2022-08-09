package com.heech.hellcoding.core.education.lesson.domain;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.common.entity.BaseEntity;
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

    private boolean isCourse;

    @Column(name = "lesson_title")
    private String title;

    private boolean isEditor;
    private String lessonIntroduction;
    private String lessonGoal;
    private String lessonTarget;

    private String thumbnailUrl;

    private boolean isMaterial;
    private String materialIntroduction;
    private String materialLink;

    @Enumerated(EnumType.STRING)
    private AgeCode ageCode;
    private int minAge;
    private int maxAge;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member teacher;
    private String teacherIntroduction;

    private boolean isCopyright;
    @Enumerated(EnumType.STRING)
    private CopyRight copyRight;

    private String imageAttachFileId;
    private String documentAttachFileId;

    private int inquiryCount;

    private boolean isOpen;
    private boolean isDelete;

    @Builder(builderClassName = "createLessonBuilder", builderMethodName = "createLessonBuilder")
    public Lesson(LessonType lessonType, Category category, boolean isCourse, String title, boolean isEditor, String lessonIntroduction, String lessonGoal, String lessonTarget, String thumbnailUrl, boolean isMaterial, String materialIntroduction, String materialLink, AgeCode ageCode, int minAge, int maxAge, Level level, Member teacher, String teacherIntroduction, boolean isCopyright, CopyRight copyRight, String imageAttachFileId, String documentAttachFileId, int inquiryCount, boolean isOpen, boolean isDelete) {
        this.lessonType = lessonType;
        this.category = category;
        this.isCourse = isCourse;
        this.title = title;
        this.isEditor = isEditor;
        this.lessonIntroduction = lessonIntroduction;
        this.lessonGoal = lessonGoal;
        this.lessonTarget = lessonTarget;
        this.thumbnailUrl = thumbnailUrl;
        this.isMaterial = isMaterial;
        this.materialIntroduction = materialIntroduction;
        this.materialLink = materialLink;
        this.ageCode = ageCode;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.level = level;
        this.teacher = teacher;
        this.teacherIntroduction = teacherIntroduction;
        this.isCopyright = isCopyright;
        this.copyRight = copyRight;
        this.imageAttachFileId = imageAttachFileId;
        this.documentAttachFileId = documentAttachFileId;
        this.inquiryCount = inquiryCount;
        this.isOpen = isOpen;
        this.isDelete = isDelete;
    }
}
