package com.heech.hellcoding.api.survey.option;

import com.heech.hellcoding.core.common.json.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/survey/options")
public class ApiOptionController {

    @DeleteMapping(value = "/{id}")
    public JsonResult deleteOption(@PathVariable("id") Long optionId) {

        return JsonResult.OK();
    }
}
