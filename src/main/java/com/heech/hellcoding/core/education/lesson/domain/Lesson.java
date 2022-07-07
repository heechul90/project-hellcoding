package com.heech.hellcoding.core.education.lesson.domain;

import com.heech.hellcoding.core.common.entity.BaseEntity;
import lombok.AccessLevel;
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

    private String category1;
    private String category2;

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

    private String teacherName;
    private String teacherId;
    private String teacherIntroduction;

    private String copyright;

    private String copyrightAttachFileId;
    private String imageAttachFileId;
    private String attachFileId;

    private int inquiryCount;

    private boolean isOpen;
    private boolean isDelete;

}
