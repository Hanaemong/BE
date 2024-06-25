package com.hana.hanalink.member.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.dto.request.JoinRequest;
import com.hana.hanalink.member.dto.response.JoinResponse;
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
    public SuccessResponse<JoinResponse> join(@RequestBody JoinRequest request) {
        return SuccessResponse.success(memberService.join(request));
    }
}
