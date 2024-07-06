package com.hana.hanalink.teammember.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private TeamMemberRole role;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public TeamMemberRes toDto(String gender,String profile,String name){
        return TeamMemberRes.builder()
                .teamMemberId(teamMemberId)
                .nickName(name)
                .gender(gender)
                .profile(profile)
                .role(role)
                .build();
    }

    public void changeRole(TeamMemberRole role) {
        this.role = role;
    }
}
