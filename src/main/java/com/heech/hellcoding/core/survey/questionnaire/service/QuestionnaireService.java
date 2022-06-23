package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.option.dto.OptionDto;
import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import com.heech.hellcoding.core.survey.question.repository.QuestionRepository;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireDto;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.dto.UpdateQuestionnaireParam;
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
    public Long saveQuestionnaire(QuestionnaireDto saveParam) {
        Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                .title(saveParam.getQuestionnaireTitle())
                .description(saveParam.getQuestionnaireDescription())
                .periodAt(saveParam.getPeriodAt())
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
    public void updateQuestionnaire(Long id, QuestionnaireDto updateParam) {

        Questionnaire findQuestionnaire = questionnaireRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));

        findQuestionnaire.updateQuestionnaireBuilder()
                .title(updateParam.getQuestionnaireTitle())
                .description(updateParam.getQuestionnaireDescription())
                .periodAt(updateParam.getPeriodAt())
                .beginDate(updateParam.getBeginDate())
                .endDate(updateParam.getEndDate())
                .build();

        for (QuestionDto question : updateParam.getQuestions()) {
            Question findQuestion = questionRepository.findById(question.getQuestionId()).orElse(null);
            if (findQuestion != null) {
                findQuestion.updateQuestionBuilder()
                        .questionnaire(findQuestionnaire)
                        .title(question.getQuestionTitle())
                        .questionOrder(question.getQuestionOrder())
                        .setting(question.getSetting())
                        .build();
            } else {
                Question addQuestion = Question.createQuestionBuilder()
                        .title(question.getQuestionTitle())
                        .questionOrder(question.getQuestionOrder())
                        .setting(question.getSetting())
                        .build();
                findQuestion = questionRepository.save(addQuestion);
            }

            for (OptionDto option : question.getOptions()) {
                Option findOption = optionRepository.findById(option.getOptionId()).orElse(null);
                if (findOption != null) {
                    findOption.updateOptionBuilder()
                            .question(findQuestion)
                            .optionOrder(option.getOptionOrder())
                            .content(option.getOptionContent())
                            .build();
                } else {
                    Option addOption = Option.createOptionBuilder()
                            .optionOrder(option.getOptionOrder())
                            .content(option.getOptionContent())
                            .build();
                    optionRepository.save(addOption);
                }
            }

        }


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
