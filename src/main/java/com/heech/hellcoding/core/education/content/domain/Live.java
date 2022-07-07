package com.heech.hellcoding.core.education.content.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "education_content_live")
@DiscriminatorValue(value = "LIVE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Live extends Content {

    private LocalDateTime liveBeginDate;

}
