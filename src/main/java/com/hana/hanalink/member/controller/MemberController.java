package com.hana.hanalink.member.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.member.dto.request.*;
import com.hana.hanalink.member.dto.response.*;
import com.hana.hanalink.member.service.MemberService;
import com.hana.hanalink.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @PostMapping("/message")
    public SuccessResponse<MemberMessageResponse> memberMessage(@RequestBody MemberMessageRequest memberMessageRequest) {
        memberService.memberMessage(memberMessageRequest);
        return SuccessResponse.successWithNoData();
    }

    @PostMapping("/messageCheck")
    public SuccessResponse<MemberMsgCheckResponse> memberMsgCheck(@RequestBody MemberMsgCheckRequest memberMsgCheckRequest) {
        MemberMsgCheckResponse memberMsgCheckResponse = memberService.memberMsgCheck(memberMsgCheckRequest);
        return SuccessResponse.success(memberMsgCheckResponse);
    }

    @PostMapping("/regionCheck")
    public Mono<SuccessResponse<CheckLocationResponse>> checkLocation(@RequestBody CheckLocationRequest request){
        return memberService.checkLocation(request.latitude(), request.longitude(), request.siGunId(), request.siGunGuId())
                .map(SuccessResponse::success);
    }

    @PutMapping("/regionChange")
    public SuccessResponse<Void> changeRegion(@RequestBody ChangeRegionRequest request, @AuthenticationPrincipal MemberDetails memberDetails){
        memberService.changeRegion(request, memberDetails.getUsername());
        return SuccessResponse.successWithNoData();
    }

    @GetMapping
    public SuccessResponse<MemberResponse> getMember(@AuthenticationPrincipal MemberDetails memberDetails){
        MemberResponse member = memberService.getMemberByPhone(memberDetails.getUsername());
        return SuccessResponse.success(member);
    }
}
