package com.hana.hanalink.account.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.dto.response.AccountResponseDto;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.exception.MemberNotFoundException;
import com.hana.hanalink.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    public AccountResponseDto getAccountByPhone(String phone){
        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(MemberNotFoundException::new);
        Account account = accountRepository.findAccountByMember_MemberId(member.getMemberId());

        return new AccountResponseDto(
                account.getAccountId(),
                account.getAccountName(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getBank()
        );
    }
}
