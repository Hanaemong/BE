package com.hana.hanalink.team.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.common.firebase.FirebaseFcmService;
import com.hana.hanalink.common.service.ImageUploadService;
import com.hana.hanalink.member.domain.MemberDetails;
import com.hana.hanalink.team.dto.request.CreateTeamReq;
import com.hana.hanalink.team.dto.request.JoinTeamReq;
import com.hana.hanalink.team.dto.response.CreateTeamRes;
import com.hana.hanalink.team.dto.response.DetailTeamRes;
import com.hana.hanalink.team.dto.response.TeamRes;
import com.hana.hanalink.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;
    private final ImageUploadService imageUploadService;
    private final FirebaseFcmService firebaseFcmService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<CreateTeamRes> createTeam(@AuthenticationPrincipal MemberDetails member,
                                                     @RequestPart("createTeam") CreateTeamReq req,
                                                     @RequestPart(value = "thumbNail") MultipartFile img) {
        return SuccessResponse.success(teamService.createTeam(member.getUsername(), req, imageUploadService.saveImage(img)));
    }


    @PutMapping(value = "/{teamId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<Void> updateBanner(@PathVariable("teamId") Long teamId,
                                              @RequestPart(value = "banner") MultipartFile img) {
        teamService.updateBanner(teamId, imageUploadService.saveImage(img));
        return SuccessResponse.successWithNoData();
    }


    @PostMapping("/{teamId}")
    SuccessResponse<Void> joinTeam(@AuthenticationPrincipal MemberDetails member,
                                   @PathVariable("teamId") Long teamId,
                                   @RequestBody JoinTeamReq req) throws FirebaseMessagingException {
        teamService.joinTeam(member.getUsername(), teamId, req);
        if (!member.getMemberFcmToken().isEmpty()) {
            firebaseFcmService.subscribeToTopic(member.getMemberFcmToken(), teamId.toString());
        }
        return SuccessResponse.successWithNoData();
    }

    @GetMapping("")
    SuccessResponse<List<TeamRes>> getTeamList(@AuthenticationPrincipal MemberDetails member) {
        return SuccessResponse.success(teamService.getTeamList(member.getUsername()));
    }

    @GetMapping("/category")
    SuccessResponse<List<TeamRes>> getCategoryTeamList(@AuthenticationPrincipal MemberDetails member,
                                                       @RequestParam("keyword") String keyword) {
        return SuccessResponse.success(teamService.getCategoryTeamList(member.getUsername(), keyword));
    }

    @GetMapping("/search")
    SuccessResponse<List<TeamRes>> getSearchTeamList(@AuthenticationPrincipal MemberDetails member,
                                                     @RequestParam("keyword") String keyword) {
        return SuccessResponse.success(teamService.getSearchTeamList(member.getUsername(), keyword));
    }

    @GetMapping("/my")
    SuccessResponse<List<TeamRes>> getMyTeamList(@AuthenticationPrincipal MemberDetails member) {
        return SuccessResponse.success(teamService.getMyTeamList(member.getUsername()));
    }

    @GetMapping("/{teamId}")
    SuccessResponse<DetailTeamRes> getDetailTeam(@AuthenticationPrincipal MemberDetails member,
                                                 @PathVariable("teamId") Long teamId) {
        return SuccessResponse.success(teamService.getDetailTeam(member.getUsername(), teamId));
    }
}
