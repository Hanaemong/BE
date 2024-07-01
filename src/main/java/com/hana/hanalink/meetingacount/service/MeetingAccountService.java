package com.hana.hanalink.meetingacount.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingAccountService {

    private final TeamMemberRepository teamMemberRepository;
    private final AccountRepository accountRepository;


    public void changeMeetingAccount(Long chairId,Long nextChairId) {

        TeamMember teamMember = teamMemberRepository.findById(chairId).orElseThrow();
        TeamMember nextTeamMember = teamMemberRepository.findById(nextChairId).orElseThrow();

        /*총무 계좌와 다음 총무 계좌 조회*/
        Account chairAccount = accountRepository.findAccountByMember_MemberId(teamMember.getMember().getMemberId());
        Account nextChairAccount = accountRepository.findAccountByMember_MemberId(nextTeamMember.getMember().getMemberId());

        /*총무 계좌 id 변경*/
        Long beforeAccountId = chairAccount.getAccountId();
        chairAccount.changeAccount(nextChairAccount.getAccountId());
        nextChairAccount.changeAccount(beforeAccountId);

        accountRepository.save(chairAccount);
        accountRepository.save(nextChairAccount);

    }
}
