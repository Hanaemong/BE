package com.hana.hanalink.accountto.repository;

import com.hana.hanalink.accountto.domain.AccountTo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountToRepository extends JpaRepository<AccountTo,Long> {

    AccountTo findAccountToByAccount_AccountId(Long accountId);
}
