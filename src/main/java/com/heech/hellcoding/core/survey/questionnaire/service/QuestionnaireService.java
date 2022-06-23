package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import com.heech.hellcoding.core.survey.question.repository.QuestionRepository;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireDto;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

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
    public Long saveQuestionnaire(QuestionnaireDto dto) {
        Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                .title(dto.getQuestionnaireTitle())
                .description(dto.getQuestionnaireDescription())
                .periodAt(dto.getPeriodAt())
                .beginDate(dto.getBeginDate())
                .endDate(dto.getEndDate())
                .questions(dto.getQuestions().stream()
                        .map(questionDto -> new Question(
                                questionDto.getQuestionTitle(),
                                questionDto.getQuestionOrder(),
                                questionDto.getSetting(),
                                questionDto.getOptions().stream()
                                        .map(optionDto -> new Option(
                                                optionDto.getOptionOrder(),
                                                optionDto.getOptionContent()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList())
                )
                .build();
        Questionnaire savedQuestionnaire = questionnaireRepository.save(questionnaire);

        return savedQuestionnaire.getId();
    }

    /**
     * 설문 수정
     */

    /**
     * 설문 삭제
     */


}
