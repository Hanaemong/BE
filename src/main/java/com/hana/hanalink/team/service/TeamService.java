package com.hana.hanalink.team.service;

import com.hana.hanalink.account.domain.Account;
import com.hana.hanalink.account.repository.AccountRepository;
import com.hana.hanalink.meetingacount.repository.MeetingAccountRepository;
import com.hana.hanalink.member.domain.Member;
import com.hana.hanalink.member.exception.MemberNotFoundException;
import com.hana.hanalink.member.repository.MemberRepository;
import com.hana.hanalink.team.dto.request.CreateTeamReq;
import com.hana.hanalink.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final MeetingAccountRepository meetingAccountRepository;
    @Transactional
    public void createTeam(String phone, CreateTeamReq req) {
        Member member = memberRepository.findByPhone(phone).orElseThrow(MemberNotFoundException::new);
        Account account = accountRepository.findAccountByMember_MemberId(member.getMemberId());
//        MeetingAccount meetingAccount =

    }
}
