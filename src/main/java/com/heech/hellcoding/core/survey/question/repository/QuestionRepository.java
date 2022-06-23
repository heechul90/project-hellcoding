package com.heech.hellcoding.core.survey.question.repository;

import com.heech.hellcoding.core.survey.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
