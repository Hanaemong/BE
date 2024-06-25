package com.hana.hanalink.teammember.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.teammember.dto.TeamMemberRes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @Column(name = "nickname",nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private TeamMemberRole role;

    @Column(name = "hello",nullable = false)
    private String hello;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public TeamMemberRes toDto(Member member){
        return TeamMemberRes.builder()
                .name(nickname)
                .gender(member.getGender())
                .profile(member.getProfile())
                .role(role)
                .build();
    }
}
