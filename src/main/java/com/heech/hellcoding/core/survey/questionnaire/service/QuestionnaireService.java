package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.repository.QuestionRepository;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.UpdateQuestionnaireParam;
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
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    /**
     * 설문 목록 조회
     */
    public Page<Questionnaire> findQuestionnaires(QuestionnaireSearchCondition condition, Pageable pageable) {
        return questionnaireRepository.findQuestionnaires(condition, pageable);
    }

    /**
     * 설문 단건 조회
     */
    public Questionnaire findQuestionnaire(Long id) {
        return questionnaireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 설문 저장
     */
    @Transactional
    public Long saveQuestionnaire(Questionnaire questionnaire) {
        return questionnaireRepository.save(questionnaire).getId();
    }

    /**
     * 설문 수정
     */
    @Transactional
    public void updateQuestionnaire(Long id, UpdateQuestionnaireParam questionnaireParam) {
        Questionnaire findQuestionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));

        findQuestionnaire.updateQuestionnaireBuilder()
                .title(questionnaireParam.getQuestionnaireTitle())
                .description(questionnaireParam.getQuestionnaireDescription())
                .isPeriod(questionnaireParam.getIsPeriod())
                .beginDate(questionnaireParam.getBeginDate())
                .endDate(questionnaireParam.getEndDate())
                .build();

        questionnaireParam.getQuestions().stream()
                .forEach(questionParam -> {
                    Question updatedQuestion;
                    if (questionParam.getQuestionId() != null) {
                        updatedQuestion = findQuestionnaire.getQuestions().stream()
                                .filter(question -> question.getId().equals(questionParam.getQuestionId()))
                                .findAny()
                                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
                        updatedQuestion.updateQuestionBuilder()
                                .questionnaire(findQuestionnaire)
                                .title(questionParam.getQuestionTitle())
                                .questionOrder(questionParam.getQuestionOrder())
                                .setting(questionParam.getSetting())
                                .build();
                    } else {
                        updatedQuestion = Question.addQuestionBuilder()
                                .questionnaire(findQuestionnaire)
                                .title(questionParam.getQuestionTitle())
                                .questionOrder(questionParam.getQuestionOrder())
                                .setting(questionParam.getSetting())
                                .build();
                    }

                    questionParam.getOptions().stream()
                            .forEach(optionParam -> {
                                Option updatedOption;
                                if (optionParam.getOptionId() != null) {
                                    updatedOption = updatedQuestion.getOptions().stream()
                                            .filter(option -> option.getId().equals(optionParam.getOptionId()))
                                            .findAny()
                                            .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
                                    updatedOption.updateOptionBuilder()
                                            .question(updatedQuestion)
                                            .optionOrder(optionParam.getOptionOrder())
                                            .content(optionParam.getOptionContent())
                                            .build();
                                } else {
                                    updatedOption = Option.addOptionBuilder()
                                            .question(updatedQuestion)
                                            .optionOrder(optionParam.getOptionOrder())
                                            .content(optionParam.getOptionContent())
                                            .build();
                                }
                            });
                });
    }

    /**
     * 설문 삭제
     */
    @Transactional
    public void deleteQuestionnaire(Long id) {
        Questionnaire findQuestionnarie = questionnaireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
        findQuestionnarie.deleteQuestionnaire();
    }

}
