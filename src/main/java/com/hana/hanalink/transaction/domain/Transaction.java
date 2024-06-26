package com.hana.hanalink.transaction.domain;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.common.domain.BaseEntity;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.transaction.dto.response.TransactionRes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "trans_to", nullable = false)
    private String transTo;

    @Column(name = "trans_from", nullable = false)
    private String transFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private Account accountTo;

    @ManyToOne
    @JoinColumn(name = "account_from_id", nullable = false)
    private Account accountFrom;


    public TransactionRes toTransMember(Member member) {
        return TransactionRes.builder()
                .memberGender(member.getGender())
                .memberName(member.getName())
                .memberGender(member.getGender())
                .amount(amount)
                .memberImg(member.getProfile())
                .type(type)
                .build();
    }
}
