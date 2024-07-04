package com.hana.hanalink.meetingacount.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.accountto.domain.AccountTo;
import com.hana.hanalink.accountto.repository.AccountToRepository;
import com.hana.hanalink.teammember.domain.TeamMember;
import com.hana.hanalink.teammember.domain.TeamMemberRole;
import com.hana.hanalink.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingAccountService {

    private final TeamMemberRepository teamMemberRepository;
    private final AccountRepository accountRepository;
    private final AccountToRepository accountToRepository;

    @Transactional
    public void changeMeetingAccount(Long teamId,Long nextChairId) {

        TeamMember curChair = teamMemberRepository.findTeamMemberByTeam_TeamIdAndRole(teamId, TeamMemberRole.CHAIR);
        TeamMember nextChair = teamMemberRepository.findById(nextChairId).orElseThrow();

        curChair.changeRole(TeamMemberRole.REGULAR);
        nextChair.changeRole(TeamMemberRole.CHAIR);
        teamMemberRepository.save(curChair);
        teamMemberRepository.save(nextChair);

        /*총무 계좌와 다음 총무 계좌 조회*/
        Account chairAccount = accountRepository.findAccountByMember_MemberId(curChair.getMember().getMemberId());
        Account nextChairAccount = accountRepository.findAccountByMember_MemberId(nextChair.getMember().getMemberId());

        /*총무 계좌 id 변경*/
        AccountTo chairAccountTo = accountToRepository.findAccountToByAccount_AccountId(nextChairAccount.getAccountId());
        chairAccountTo.changeChairAccount(nextChairAccount);


        accountToRepository.save(chairAccountTo);

    }
}
