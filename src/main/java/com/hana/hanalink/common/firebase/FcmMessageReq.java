package com.hana.hanalink.common.firebase;

import com.hana.hanalink.alarm.domain.AlarmType;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import lombok.Builder;

@Builder
public record FcmMessageReq(String title, String body, String image, AlarmType type, Team team, Member member) { }
