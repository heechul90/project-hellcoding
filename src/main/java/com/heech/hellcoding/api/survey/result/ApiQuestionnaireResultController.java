package com.heech.hellcoding.api.survey.result;

import com.heech.hellcoding.api.survey.result.request.CreateQuestionnaireResultRequest;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import com.heech.hellcoding.core.survey.result.service.QuestionnaireResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey/results")
public class ApiQuestionnaireResultController {

    private final QuestionnaireResultService questionnaireResultService;

    @PostMapping
    public JsonResult saveQuestionnaireResult(@Validated CreateQuestionnaireResultRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        QuestionnaireResult questionnaireResult = QuestionnaireResult.createQuestionnaireResultBuilder()
                .build();

        questionnaireResultService.saveQuestionnaireResult(questionnaireResult);

        return JsonResult.OK();

    }

}
