package com.hana.hanalink.teammember.dto;

import com.hana.hanalink.teammember.domain.TeamMemberRole;

public record MyTeamMemberRes(String nickname, String profile, TeamMemberRole role) {
}
