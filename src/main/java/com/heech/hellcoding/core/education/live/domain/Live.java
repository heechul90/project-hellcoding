package com.heech.hellcoding.core.education.live.domain;

import com.heech.hellcoding.core.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "education_live")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Live extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_live_id")
    private Long id;

    @Column(name = "liveContent")
    private String content;

    private LocalDateTime liveBeginDate;
    private LocalDateTime liveEndDate;

    @Embedded
    private Meeting meeting;

}
