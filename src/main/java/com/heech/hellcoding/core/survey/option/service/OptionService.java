package com.heech.hellcoding.core.survey.option.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    /**
     * 옵션 삭제
     */
    @Transactional
    public void deleteOption(Long id) {
        Option findOption = optionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
        optionRepository.delete(findOption);
    }
}
