package com.hana.hanalink.teammember.dto;

import com.hana.hanalink.teammember.domain.TeamMemberRole;
import lombok.Builder;

@Builder
public record TeamMemberRes(Long teamMemberId,String profile, String name, String gender, TeamMemberRole role) {
}
