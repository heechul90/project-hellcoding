package com.heech.hellcoding.core.education.lesson.domain;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LessonTest {

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

    @PersistenceContext EntityManager em;

    @Test
    @DisplayName(value = "lesson entity 생성 후 저장 확인")
    @Rollback(value = false)
    void createLessonTest() {
        //given
        Lesson lesson = getLesson(LESSON_TYPE, CATEGORY, TITLE, IS_EDITOR, LESSON_INTRODUCTION, LESSON_GOAL, LESSON_TARGET, IS_MATERIAL, MATERIAL_INTRODUCTION, MATERIAL_LINK,
                AGE_CODE, MIN_AGE, MAX_AGE, LEVEL, TEACHER, TEACHER_INTRODUCTION);

        //when
        em.persist(lesson);

        //then
        Lesson findLesson = em.find(Lesson.class, lesson.getId());
        assertThat(findLesson.getLessonType()).isEqualTo(LESSON_TYPE);
        assertThat(findLesson.getCategory()).isEqualTo(CATEGORY);
        assertThat(findLesson.getTitle()).isEqualTo(TITLE);
        assertThat(findLesson.isEditor()).isEqualTo(IS_EDITOR);
        assertThat(findLesson.getLessonIntroduction()).isEqualTo(LESSON_INTRODUCTION);
        assertThat(findLesson.getLessonGoal()).isEqualTo(LESSON_GOAL);
        assertThat(findLesson.getLessonTarget()).isEqualTo(LESSON_TARGET);
        assertThat(findLesson.isMaterial()).isEqualTo(IS_MATERIAL);
        assertThat(findLesson.getMaterialIntroduction()).isEqualTo(MATERIAL_INTRODUCTION);
        assertThat(findLesson.getMaterialLink()).isEqualTo(MATERIAL_LINK);
        assertThat(findLesson.getAgeCode()).isEqualTo(AGE_CODE);
        assertThat(findLesson.getMinAge()).isEqualTo(MIN_AGE);
        assertThat(findLesson.getMaxAge()).isEqualTo(MAX_AGE);
        assertThat(findLesson.getLevel()).isEqualTo(LEVEL);
        assertThat(findLesson.getTeacher()).isEqualTo(TEACHER);
        assertThat(findLesson.getTeacherIntroduction()).isEqualTo(TEACHER_INTRODUCTION);

    }

}