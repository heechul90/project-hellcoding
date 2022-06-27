package com.heech.hellcoding.core.survey.option.repository;


import com.heech.hellcoding.core.survey.option.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
