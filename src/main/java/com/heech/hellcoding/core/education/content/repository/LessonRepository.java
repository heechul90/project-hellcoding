package com.heech.hellcoding.core.education.content.repository;

import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
