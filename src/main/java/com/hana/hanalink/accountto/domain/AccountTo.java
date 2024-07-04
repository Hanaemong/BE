package com.hana.hanalink.accountto.domain;


import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_to")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountToId;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void changeChairAccount(Account account) {
        this.account = account;
    }
}

