package com.heech.hellcoding.core.survey.result.repository;

import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionnaireResultRepository extends JpaRepository<QuestionnaireResult, Long> {

    @Query("select count(qr)" +
            " from QuestionnaireResult qr" +
            " join qr.option op" +
            " join op.question qu" +
            " join qu.questionnaire qt " +
            " where qr.member.id = :memberId and qt.id = :questionnaireId")
    int countByMemberIdByQuestionnaireId(@Param("memberId") Long memberId, @Param("questionnaireId") Long questionnaireId);
}
