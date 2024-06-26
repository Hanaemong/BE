package com.hana.hanalink.account.repository;

import com.hana.hanalink.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByMember_MemberId(Long memberId);

}
