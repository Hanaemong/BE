package com.hana.hanalink.member.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.dto.request.JoinRequest;
import com.hana.hanalink.member.dto.request.LoginRequest;
import com.hana.hanalink.member.dto.response.LoginResponse;
import com.hana.hanalink.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public SuccessResponse<LoginResponse> join(@RequestBody JoinRequest request) {
        memberService.join(request);
        return SuccessResponse.successWithNoData();
    }

    @PostMapping("/login")
    public SuccessResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return SuccessResponse.success(memberService.login(request));
    }
}
