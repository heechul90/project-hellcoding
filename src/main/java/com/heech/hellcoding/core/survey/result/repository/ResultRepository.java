package com.heech.hellcoding.core.survey.result.repository;

import com.heech.hellcoding.core.survey.result.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
