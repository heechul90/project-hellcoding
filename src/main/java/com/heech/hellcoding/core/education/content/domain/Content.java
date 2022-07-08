package com.heech.hellcoding.core.education.content.domain;

import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_content")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "contentType")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Content {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private ContentType contentType;

    private int classNumber;
    private String classTitle;
    private boolean isDelete;

}
