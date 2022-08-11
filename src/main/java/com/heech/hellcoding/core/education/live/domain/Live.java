package com.heech.hellcoding.core.education.live.domain;

import com.heech.hellcoding.core.education.curriculum.domain.Curriculum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "education_live")
@DiscriminatorValue(value = "LIVE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Live extends Curriculum {

    private LocalDateTime liveBeginDate;

    @Column(name = "liveContent")
    private String content;

    @Column(name = "liveTitle")
    private String title;
}
