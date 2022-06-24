package com.heech.hellcoding.api.survey.questionnaire;

import com.heech.hellcoding.api.survey.questionnaire.request.CreateQuestionnaireRequest;
import com.heech.hellcoding.api.survey.questionnaire.request.UpdateQuestionnaireRequest;
import com.heech.hellcoding.api.survey.questionnaire.response.CreateQuestionnaireResponse;
import com.heech.hellcoding.api.survey.questionnaire.response.UpdateQuestionnaireResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.survey.option.dto.OptionDetailDto;
import com.heech.hellcoding.core.survey.option.dto.UpdateOptionParam;
import com.heech.hellcoding.core.survey.question.dto.QuestionDetailDto;
import com.heech.hellcoding.core.survey.question.dto.UpdateQuestionParam;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.UpdateQuestionnaireParam;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireDetailDto;
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

import static org.springframework.util.StringUtils.*;

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
        Page<com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire> content = questionnaireService.findQuestionnaires(condition, pageable);
        List<QuestionnaireDto> collect = content.getContent().stream()
                .map(questionnaire -> new QuestionnaireDto (
                        questionnaire.getId(),
                        questionnaire.getTitle(),
                        questionnaire.getDescription(),
                        questionnaire.getIsPeriod(),
                        questionnaire.getBeginDate(),
                        questionnaire.getEndDate()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 설문 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findQuestionnaire(@PathVariable("id") Long questionnaireId) {
        com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(questionnaireId);
        QuestionnaireDetailDto questionnaire = new QuestionnaireDetailDto(
                findQuestionnaire.getId(),
                findQuestionnaire.getTitle(),
                findQuestionnaire.getDescription(),
                findQuestionnaire.getIsPeriod(),
                findQuestionnaire.getBeginDate(),
                findQuestionnaire.getEndDate(),
                findQuestionnaire.getQuestions().stream()
                        .map(question -> new QuestionDetailDto(
                                question.getId(),
                                question.getTitle(),
                                question.getQuestionOrder(),
                                question.getSetting(),
                                question.getOptions().stream()
                                        .map(option -> new OptionDetailDto(
                                                option.getId(),
                                                option.getOptionOrder(),
                                                option.getContent()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList())
        );
        return JsonResult.OK(questionnaire);
    }

    /**
     * 설문 저장
     */
    @PostMapping
    public JsonResult saveQuestionnaire(@RequestBody @Validated CreateQuestionnaireRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        Questionnaire questionnaire = com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire.createQuestionnaireBuilder()
                .title(request.getQuestionnaireTitle())
                .description(request.getQuestionnaireDescription())
                .isPeriod(request.getIsPeriod())
                .beginDate(hasText(request.getBeginDate()) ? LocalDateTime.parse(request.getBeginDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) : null)
                .endDate(hasText(request.getEndDate()) ? LocalDateTime.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) : null)
                .questions(
                        request.getQuestions().stream()
                                .map(question -> com.heech.hellcoding.core.survey.question.domain.Question.createQuestionBuilder()
                                        .title(question.getQuestionTitle())
                                        .questionOrder(question.getQuestionOrder())
                                        .setting(question.getSetting())
                                        .options(
                                                question.getOptions().stream()
                                                        .map(option -> com.heech.hellcoding.core.survey.option.domain.Option.createOptionBuilder()
                                                                .optionOrder(option.getOptionOrder())
                                                                .content(option.getOptionContent())
                                                                .build()
                                                        )
                                                        .collect(Collectors.toList())

                                        )
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();

        Long savedId = questionnaireService.saveQuestionnaire(questionnaire);
        return JsonResult.OK(new CreateQuestionnaireResponse(savedId));
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

        UpdateQuestionnaireParam questionnaireParam = new UpdateQuestionnaireParam(
                request.getQuestionnaireTitle(),
                request.getQuestionnaireDescription(),
                request.getIsPeriod(),
                LocalDateTime.parse(request.getBeginDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                LocalDateTime.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                request.getQuestions().stream()
                        .map(questionRequest -> new UpdateQuestionParam(
                                questionRequest.getQuestionId() != null ? questionRequest.getQuestionId() : null,
                                questionRequest.getQuestionTitle(),
                                questionRequest.getQuestionOrder(),
                                questionRequest.getSetting(),
                                questionRequest.getOptions().stream()
                                        .map(optionRequest -> new UpdateOptionParam(
                                                optionRequest.getOptionId() != null ? optionRequest.getOptionId() : null,
                                                optionRequest.getOptionOrder(),
                                                optionRequest.getOptionContent()
                                        ))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList())
        );
        questionnaireService.updateQuestionnaire(questionnaireId, questionnaireParam);

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
