package com.heech.hellcoding.core.education.curriculum.domain;

import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import com.heech.hellcoding.core.education.live.domain.Live;
import com.heech.hellcoding.core.education.quiz.domain.Quiz;
import com.heech.hellcoding.core.education.video.domain.Video;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_curriculum")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curriculum {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_curriculum_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_lesson_id")
    private Lesson lesson;

    private int sortNo;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private String CurriculumTitle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_video_id")
    private Video video;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_live_id")
    private Live live;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_quiz_id")
    private Quiz quiz;

}
