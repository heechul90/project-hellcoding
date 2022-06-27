package com.heech.hellcoding.core.survey.question.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 질문 단건 조회
     */
    public Question findQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
    }


    /**
     * 질문 삭제
     */
    @Transactional
    public void deleteQuestion(Long id) {
        Question findQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
        questionRepository.delete(findQuestion);
    }
}
