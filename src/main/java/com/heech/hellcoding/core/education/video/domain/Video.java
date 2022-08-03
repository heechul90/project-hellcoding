package com.heech.hellcoding.core.education.video.domain;

import com.heech.hellcoding.core.education.curriculum.domain.Curriculum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "education_video")
@DiscriminatorValue(value = "VIDEO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends Curriculum {

    @Column(name = "video_content")
    private String content;

    private int videoDuration;
    private VideoUploadType videoUploadType;
    private String videoUrl;
    private int learningTime;

    private String imageAttachFileId;
    private String videoAttachFileId;
    private String subtitleAttachFileId;
    private String attachFileId;
}
