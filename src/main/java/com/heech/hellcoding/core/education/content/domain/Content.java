package com.heech.hellcoding.core.education.content.domain;

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
public class Content {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_content_id")
    private Long id;
}
