package com.hana.hanalink.account.controller;

import com.hana.hanalink.account.dto.response.AccountResponseDto;
import com.hana.hanalink.account.service.AccountService;
import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public SuccessResponse<AccountResponseDto> getAccount(@AuthenticationPrincipal MemberDetails memberDetails) {
        AccountResponseDto account = accountService.getAccountByPhone(memberDetails.getUsername());
        return SuccessResponse.success(account);
    }

}
