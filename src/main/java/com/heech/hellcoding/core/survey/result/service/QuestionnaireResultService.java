package com.heech.hellcoding.core.survey.result.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import com.heech.hellcoding.core.survey.result.dto.CreateQuestionnaireResultDto;
import com.heech.hellcoding.core.survey.result.repository.QuestionnaireResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionnaireResultService {

    private final QuestionnaireResultRepository questionnaireResultRepository;
    private final MemberRepository memberRepository;
    private final OptionRepository optionRepository;

    /**
     * 설문 결과 저장
     */
    @Transactional
    public void saveQuestionnaireResult(List<CreateQuestionnaireResultDto> questionnaireResults) {
        questionnaireResults.stream().forEach(result -> {
            Member findMember = memberRepository.findById(result.getMemberId()).orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
            Option findOption = optionRepository.findById(result.getOptionId()).orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));

            questionnaireResultRepository.save(QuestionnaireResult.createQuestionnaireResultBuilder()
                    .member(findMember)
                    .option(findOption)
                    .content(result.getResponseContent())
                    .build());
        });
    }

}
