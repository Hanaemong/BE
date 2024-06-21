package com.hana.hanalink.meetingacount.domain;

import com.hana.hanalink.common.domain.BaseEntity;
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
    @Column(name = "meeting_acount_number",nullable = false)
    private String meetingAccountNumber;
    @Column(name = "group_id",nullable = false)
    private Long groupId;
    @Column(name = "account_id",nullable = false)
    private Long accountId;
}
