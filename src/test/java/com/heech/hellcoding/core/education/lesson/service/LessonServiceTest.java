package com.heech.hellcoding.core.education.lesson.service;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.education.lesson.domain.AgeCode;
import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import com.heech.hellcoding.core.education.lesson.domain.LessonType;
import com.heech.hellcoding.core.education.lesson.domain.Level;
import com.heech.hellcoding.core.education.lesson.dto.LessonSearchCondition;
import com.heech.hellcoding.core.education.lesson.repository.LessonQueryRepository;
import com.heech.hellcoding.core.education.lesson.repository.LessonRepository;
import com.heech.hellcoding.core.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @DisplayName(value = "lesson 목록 조회")
    void findLessonsTest() {
        //given
        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lessons.add(getLesson(LESSON_TYPE, CATEGORY, TITLE + i, IS_EDITOR, LESSON_INTRODUCTION, LESSON_GOAL, LESSON_TARGET, IS_MATERIAL, MATERIAL_INTRODUCTION, MATERIAL_LINK,
                    AGE_CODE, MIN_AGE, MAX_AGE, LEVEL, TEACHER, TEACHER_INTRODUCTION));
        }
        given(lessonService.findLessons(any(LessonSearchCondition.class), any(PageRequest.class))).willReturn(new PageImpl(lessons));

        //TODO 검색 작동 안함.
        LessonSearchCondition condition = new LessonSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Lesson> content = lessonService.findLessons(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(10);
        assertThat(content.getContent().size()).isEqualTo(10);
        assertThat(content.getContent()).extracting("title").contains(TITLE + 0, TITLE + 9);

        
        //verify
        verify(lessonQueryRepository, times(1)).findLessons(any(), any());
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