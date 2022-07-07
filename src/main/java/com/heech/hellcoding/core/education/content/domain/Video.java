package com.heech.hellcoding.core.education.content.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "education_content_video")
@DiscriminatorValue(value = "VIDEO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends Content {

    @Column(name = "video_content")
    private String content;
}
