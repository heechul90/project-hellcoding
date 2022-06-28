package com.heech.hellcoding.api.survey.result;

import com.heech.hellcoding.api.survey.result.request.CreateQuestionnaireResultRequest;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import com.heech.hellcoding.core.survey.result.dto.CreateQuestionnaireResultDto;
import com.heech.hellcoding.core.survey.result.service.QuestionnaireResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey/results")
public class ApiQuestionnaireResultController {

    private final QuestionnaireResultService questionnaireResultService;

    @PostMapping
    public JsonResult saveQuestionnaireResult(@RequestBody @Validated CreateQuestionnaireResultRequest request, BindingResult bindingResult) {


        //TODO object validate 있으면 추가하기
        //설문 응답했는지 안했는지 체크
        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        List<CreateQuestionnaireResultDto> questionnaireResults = request.getQuestionnaireResults().stream()
                .map(result -> new CreateQuestionnaireResultDto(
                        request.getMemberId(),
                        result.getOptionId(),
                        result.getSetting().equals(Setting.SUBJECTIVE) ? result.getResponseContent() : null
                ))
                .collect(Collectors.toList());
        questionnaireResultService.saveQuestionnaireResult(questionnaireResults);
        return JsonResult.OK();
    }
}
