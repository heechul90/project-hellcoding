package com.heech.hellcoding.core.forms.form.domain;

import com.heech.hellcoding.core.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "forms_form")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Form extends BaseEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long id;

    @Column(name = "form_title")
    private String title;

    private String description;

    @Column(columnDefinition = "char")
    private String period_at; //Y,N

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    @Column(columnDefinition = "char")
    private String useAt; //Y, N

    //===생성 메서드===//
    @Builder(builderClassName = "createFormBuilder", builderMethodName = "createFormBuilder")
    public Form(String title, String description, String period_at, LocalDateTime beginDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.period_at = period_at;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.useAt = "Y";
    }

    //===수정 메서드===//


}
