package com.heech.hellcoding.api.survey.questionnaire;

import com.heech.hellcoding.api.survey.questionnaire.request.CreateQuestionnaireRequest;
import com.heech.hellcoding.api.survey.questionnaire.request.UpdateQuestionnaireRequest;
import com.heech.hellcoding.api.survey.questionnaire.response.CreateQuestionnaireResponse;
import com.heech.hellcoding.api.survey.questionnaire.response.UpdateQuestionnaireResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.survey.option.dto.OptionDto;
import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.CreateUpdateQuestionnaireDto;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireDto;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @GetMapping
    public JsonResult findQuestionnaires(QuestionnaireSearchCondition condition, Pageable pageable) {
        Page<Questionnaire> content = questionnaireService.findQuestionnaires(condition, pageable);
        List<QuestionnaireDto> collect = content.getContent().stream()
                .map(questionnaire -> new QuestionnaireDto (
                        questionnaire.getId(),
                        questionnaire.getTitle(),
                        questionnaire.getDescription(),
                        questionnaire.getPeriodAt(),
                        questionnaire.getBeginDate(),
                        questionnaire.getEndDate()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 설문 단건 조회
     */
    @GetMapping(value = "{id}")
    public JsonResult findQuestionnaire(@PathVariable("id") Long questionnaireId) {
        Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(questionnaireId);
        //TODO dto로 변환하기
        return JsonResult.OK(findQuestionnaire);
    }

    /**
     * 설문 저장
     */
    @PostMapping
    public JsonResult saveQuestionnaire(@RequestBody CreateQuestionnaireRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        CreateUpdateQuestionnaireDto questionnaireDto = new CreateUpdateQuestionnaireDto(
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
    @PutMapping(value = "/{id}")
    public JsonResult updateQuestionnaire(@PathVariable("id") Long questionnaireId,
                                          @RequestBody @Validated UpdateQuestionnaireRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        CreateUpdateQuestionnaireDto questionnaireDto = new CreateUpdateQuestionnaireDto(
                request.getQuestionnaireTitle(),
                request.getQuestionnaireDescription(),
                request.getPeriodAt(),
                LocalDateTime.parse(request.getBeginDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                LocalDateTime.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                request.getQuestions().stream()
                        .map(question -> new QuestionDto(
                                question.getQuestionId() != null ? question.getQuestionId() : null,
                                question.getQuestionTitle(),
                                question.getQuestionOrder(),
                                question.getSetting(),
                                question.getOptions().stream()
                                        .map(option -> new OptionDto(
                                                option.getOptionId() != null ? option.getOptionId() : null,
                                                option.getOptionOrder(),
                                                option.getOptionContent()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList())
        );
        questionnaireService.updateQuestionnaire(questionnaireId, questionnaireDto);


        Questionnaire questionnaire = questionnaireService.findQuestionnaire(questionnaireId);
        return JsonResult.OK(new UpdateQuestionnaireResponse(questionnaire.getId()));
    }

    /**
     * 설문 삭제
     */
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteQuestionnaire(@PathVariable("id") Long questionnaireId) {
        questionnaireService.deleteQuestionnaire(questionnaireId);
        return JsonResult.OK();
    }
}
