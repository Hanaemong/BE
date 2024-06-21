package com.hana.hanalink.meetingacount.domain;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.team.domain.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meeting_account")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MeetingAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingAccountId;

    @Column(name = "meeting_account_number",nullable = false)
    private String meetingAccountNumber;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
