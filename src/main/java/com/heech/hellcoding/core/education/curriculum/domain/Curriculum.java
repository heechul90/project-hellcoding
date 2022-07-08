package com.heech.hellcoding.core.education.curriculum.domain;

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


}
