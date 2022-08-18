package com.heech.hellcoding.core.education.lesson.service;

import com.heech.hellcoding.core.common.exception.EntityNotFound;
import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import com.heech.hellcoding.core.education.lesson.dto.LessonSearchCondition;
import com.heech.hellcoding.core.education.lesson.dto.UpdateLessonParam;
import com.heech.hellcoding.core.education.lesson.repository.LessonQueryRepository;
import com.heech.hellcoding.core.education.lesson.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    public static final String ENTITY_NAME = "lesson";
    private final LessonRepository lessonRepository;
    private final LessonQueryRepository lessonQueryRepository;

    /**
     * 강의 목록 조회
     */
    public Page<Lesson> findLessons(LessonSearchCondition condition, Pageable pageable) {
        return lessonQueryRepository.findLessons(condition, pageable);
    }

    /**
     * 강의 단건 조회
     */
    public Lesson findLessons(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFound(ENTITY_NAME, lessonId));
    }

    /**
     * 강의 저장
     */
    @Transactional
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    /**
     * 강의 수정
     */
    @Transactional
    public void updateLesson(Long lessonId, UpdateLessonParam param) {
        Lesson findLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFound(ENTITY_NAME, lessonId));
        findLesson.updateLessonBuilder()
                .param(param)
                .build();
    }

    /**
     * 강의 삭제
     */
    @Transactional
    public void deleteLesson(Long lessonId) {
        Lesson findLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFound(ENTITY_NAME, lessonId));
        findLesson.deleteLesson();
    }
}
