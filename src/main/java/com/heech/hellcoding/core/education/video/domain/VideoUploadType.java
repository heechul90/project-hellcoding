package com.heech.hellcoding.core.education.video.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VideoUploadType {

    SELF("동영상 등록"), YOUTUBE("유튜브 링크");

    private final String code;
}
