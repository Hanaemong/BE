package com.hana.hanalink.account.domain;

import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "bank", nullable = false)
    private String bank;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void withDraw(Long amount) {
        this.balance -= amount;
    }
}
