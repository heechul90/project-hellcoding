package com.heech.hellcoding.core.education.live.domain;

import com.heech.hellcoding.core.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "education_live")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "live_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Live extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_live_id")
    private Long id;

    @Column(name = "live_content")
    private String content;

    private LocalDateTime liveBeginDate;
    private LocalDateTime liveEndDate;

    @Builder
    public Live(String content, LocalDateTime liveBeginDate, LocalDateTime liveEndDate) {
        this.content = content;
        this.liveBeginDate = liveBeginDate;
        this.liveEndDate = liveEndDate;
    }
}
