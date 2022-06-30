package com.heech.hellcoding.core.survey.result.repository;

import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionnaireResultRepository extends JpaRepository<QuestionnaireResult, Long> {

    /**
     * 설문참여여부
     */
    @Query("select count(qtnr)" +
            " from QuestionnaireResult qtnr" +
            " join qtnr.option op" +
            " join op.question qt" +
            " join qt.questionnaire qtn " +
            " where qtnr.member.id = :memberId and qtn.id = :questionnaireId")
    int countQuestionnaireResult(@Param("memberId") Long memberId, @Param("questionnaireId") Long questionnaireId);
}
