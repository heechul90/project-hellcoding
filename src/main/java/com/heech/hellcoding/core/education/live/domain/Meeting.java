package com.heech.hellcoding.core.education.live.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    private String serviceId;
    private String contentId;
    private String meetingId;
    private String launchUrl;
    private String hostId;
    private String identityProvider;
    private String title;
    private String password;
    private Integer maxParticipantCount;
    private boolean enableJoinAnytime;
    private boolean enableWaitingRoom;
    private boolean enablePromptPassword;
    private boolean enableAccessGrant;
    private boolean enableEventCallbakc;

}
