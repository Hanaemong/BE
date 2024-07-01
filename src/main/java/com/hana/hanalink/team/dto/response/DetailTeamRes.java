package com.hana.hanalink.team.dto.response;

import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.teammember.domain.TeamMemberRole;

public record DetailTeamRes(
        Long teamId,
        String siGunGu,
        String teamName,
        String teamDesc,
        String category,
        Float score,
        String thumbNail,
        String banner,
        TeamMemberRole role
) {
    public DetailTeamRes(Team team, TeamMemberRole role) {
        this(
                team.getTeamId(),
                team.getSiGunGu().getSiGunGu(),
                team.getTeamName(),
                team.getTeamDesc(),
                team.getCategory(),
                team.getScore(),
                team.getThumbNail(),
                team.getBanner(),
                role
        );
    }
}
