package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.repository.QuestionRepository;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.CreateUpdateQuestionnaireDto;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
    public Long saveQuestionnaire(CreateUpdateQuestionnaireDto saveParam) {
        Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                .title(saveParam.getQuestionnaireTitle())
                .description(saveParam.getQuestionnaireDescription())
                .isPeriod(saveParam.getIsPeriod())
                .beginDate(saveParam.getBeginDate())
                .endDate(saveParam.getEndDate())
                .questions(saveParam.getQuestions().stream()
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
    @Transactional
    public void updateQuestionnaire(Long id, CreateUpdateQuestionnaireDto updateParam) {
        Questionnaire findQuestionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));

        findQuestionnaire.updateQuestionnaireBuilder()
                .title(updateParam.getQuestionnaireTitle())
                .description(updateParam.getQuestionnaireDescription())
                .isPeriod(updateParam.getIsPeriod())
                .beginDate(updateParam.getBeginDate())
                .endDate(updateParam.getEndDate())
                .build();

        updateParam.getQuestions().stream()
                .forEach(questionDto -> {
                    Question updatedQuestion;
                    if (questionDto.getQuestionId() != null) {
                        updatedQuestion = findQuestionnaire.getQuestions().stream()
                                .filter(question -> question.getId().equals(questionDto.getQuestionId()))
                                .findAny()
                                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
                        updatedQuestion.updateQuestionBuilder()
                                .questionnaire(findQuestionnaire)
                                .title(questionDto.getQuestionTitle())
                                .questionOrder(questionDto.getQuestionOrder())
                                .setting(questionDto.getSetting())
                                .build();
                    } else {
                        updatedQuestion = Question.addQuestionBuilder()
                                .questionnaire(findQuestionnaire)
                                .title(questionDto.getQuestionTitle())
                                .questionOrder(questionDto.getQuestionOrder())
                                .setting(questionDto.getSetting())
                                .build();
                    }

                    questionDto.getOptions().stream()
                            .forEach(optionDto -> {
                                Option updatedOption;
                                if (optionDto.getOptionId() != null) {
                                    updatedOption = updatedQuestion.getOptions().stream()
                                            .filter(option -> option.getId().equals(optionDto.getOptionId()))
                                            .findAny()
                                            .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
                                    updatedOption.updateOptionBuilder()
                                            .question(updatedQuestion)
                                            .optionOrder(optionDto.getOptionOrder())
                                            .content(optionDto.getOptionContent())
                                            .build();
                                } else {
                                    updatedOption = Option.addOptionBuilder()
                                            .question(updatedQuestion)
                                            .optionOrder(optionDto.getOptionOrder())
                                            .content(optionDto.getOptionContent())
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
