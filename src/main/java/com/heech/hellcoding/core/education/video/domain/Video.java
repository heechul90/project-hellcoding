package com.heech.hellcoding.core.education.video.domain;

import com.heech.hellcoding.core.common.entity.BaseTimeEntity;
import com.heech.hellcoding.core.education.curriculum.domain.Curriculum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_video")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_video_id")
    private Long id;

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
