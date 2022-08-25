package com.heech.hellcoding.core.education.lesson.service;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.education.lesson.domain.AgeCode;
import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import com.heech.hellcoding.core.education.lesson.domain.LessonType;
import com.heech.hellcoding.core.education.lesson.domain.Level;
import com.heech.hellcoding.core.education.lesson.repository.LessonQueryRepository;
import com.heech.hellcoding.core.education.lesson.repository.LessonRepository;
import com.heech.hellcoding.core.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    //LESSON DATA
    public static final LessonType LESSON_TYPE = LessonType.CONTENT;
    public static final Category CATEGORY = null;
    public static final String TITLE = "test_title";
    public static final boolean IS_EDITOR = true;
    public static final String LESSON_INTRODUCTION = "test_introduction";
    public static final String LESSON_GOAL = "test_goal";
    public static final String LESSON_TARGET = "test_target";
    public static final boolean IS_MATERIAL = false;
    public static final String MATERIAL_INTRODUCTION = null;
    public static final String MATERIAL_LINK = null;
    public static final AgeCode AGE_CODE = AgeCode.DETAIL;
    public static final int MIN_AGE = 10;
    public static final int MAX_AGE = 20;
    public static final Level LEVEL = Level.LEVEL003;
    public static final Member TEACHER = null;
    public static final String TEACHER_INTRODUCTION = null;

    //UPDATE_LESSON DATA

    @InjectMocks LessonService lessonService;

    @Mock LessonRepository lessonRepository;

    @Mock LessonQueryRepository lessonQueryRepository;

    private Lesson getLesson(LessonType lessonType, Category category, String title, boolean isEditor, String lessonIntroduction, String lessonGoal, String lessonTarget, boolean isMaterial, String materialIntroduction, String materialLink, AgeCode ageCode, Integer minAge, Integer maxAge, Level level, Member teacher, String teacherIntroduction) {
        return Lesson.createLessonBuilder()
                .lessonType(lessonType)
                .category(category)
                .title(title)
                .isEditor(isEditor)
                .lessonIntroduction(lessonIntroduction)
                .lessonGoal(lessonGoal)
                .lessonTarget(lessonTarget)
                .isMaterial(isMaterial)
                .materialIntroduction(materialIntroduction)
                .materialLink(materialLink)
                .ageCode(ageCode)
                .minAge(minAge)
                .maxAge(maxAge)
                .level(level)
                .teacher(teacher)
                .teacherIntroduction(teacherIntroduction)
                .build();
    }

    @Test
    void findLessonsTest() {
    }

    @Test
    void findLessonTest() {
    }

    @Test
    void saveLessonTest() {
    }

    @Test
    void updateLessonTest() {
    }

    @Test
    void deleteLessonTest() {
    }
}