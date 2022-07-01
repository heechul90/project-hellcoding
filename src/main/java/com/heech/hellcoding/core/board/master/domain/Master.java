package com.heech.hellcoding.core.board.master.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
@Table(name = "board_master")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Master {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_master_id")
    private Long id;

    private Long siteId;

    @Column(name = "board_name")
    private String name;

    private Long categoryId;

    private String boardTemplateCode;

    private String isNotice;
    private String isSecret;
    private String isAnnymty;
    private String isUsePeriod;
    private String isReply;
    private String isComment;
    private String isFile;

}
