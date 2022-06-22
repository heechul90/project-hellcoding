package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.repository.QuestionnaireRepository;
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
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    /**
     * 설문 목록 조회
     */
    public Page<Questionnaire> findQuestionnaires(QuestionnaireSearchCondition condition, Pageable pageable) {
        return questionnaireRepository.findQuestionnaires(condition, pageable);
    }

    /**
     * 설문 단건 조회
     */
    public Questionnaire findQuestionnaires(Long id) {
        return questionnaireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 설문 저장
     */

    /**
     * 설문 수정
     */

    /**
     * 설문 삭제
     */


}
