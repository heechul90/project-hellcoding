package com.heech.hellcoding.api.survey.question;

import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.survey.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey/questions")
public class ApiQuestionController {

    private final QuestionService questionService;

    /**
     * 질문 삭제
     */
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteQuestion(@PathVariable("id") Long questionId) {
        questionService.deleteQuestion(questionId);
        return JsonResult.OK();
    }
}
