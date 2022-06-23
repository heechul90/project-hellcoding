package com.heech.hellcoding.api.survey.questionnaire;

import com.heech.hellcoding.api.survey.questionnaire.request.CreateQuestionnaireRequest;
import com.heech.hellcoding.api.survey.questionnaire.response.CreateQuestionnaireResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.survey.option.dto.OptionDto;
import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireDto;
import com.heech.hellcoding.core.survey.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey/questionnaires")
public class ApiQuestionnaireController {

    private final QuestionnaireService questionnaireService;


    /**
     * 설문 목록 조회
     */


    /**
     * 설문 단건 조회
     */


    /**
     * 설문 저장
     */
    @PostMapping
    public JsonResult saveQuestionnaire(@RequestBody CreateQuestionnaireRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        QuestionnaireDto questionnaireDto = new QuestionnaireDto(
                request.getQuestionnaireTitle(),
                request.getQuestionnaireDescription(),
                request.getPeriodAt(),
                LocalDateTime.parse(request.getBeginDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                LocalDateTime.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                request.getQuestions().stream()
                        .map(question -> new QuestionDto(
                                question.getQuestionTitle(),
                                question.getQuestionOrder(),
                                question.getSetting(),
                                question.getOptions().stream()
                                        .map(option -> new OptionDto(
                                                option.getOptionOrder(),
                                                option.getOptionContent()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList())
        );

        questionnaireService.saveQuestionnaire(questionnaireDto);
        return JsonResult.OK(new CreateQuestionnaireResponse(1L));
    }


    /**
     * 설문 수정
     */


    /**
     설문 삭제
     */
}
