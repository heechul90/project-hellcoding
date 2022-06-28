package com.heech.hellcoding.core.survey.result.service;

import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import com.heech.hellcoding.core.survey.result.repository.QuestionnaireResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionnaireResultService {

    private final QuestionnaireResultRepository questionnaireResultRepository;

    /**
     * 설문 결과 저장
     */
    @Transactional
    public Long saveQuestionnaireResult(QuestionnaireResult questionnaireResult) {
        return questionnaireResultRepository.save(questionnaireResult).getId();
    }
}
