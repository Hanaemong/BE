package com.hana.hanalink.chatting.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.team.domain.Team;
import com.hana.hanalink.teammember.domain.TeamMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatting")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chatting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "team_member_id", nullable = false)
    private TeamMember teamMember;
}
