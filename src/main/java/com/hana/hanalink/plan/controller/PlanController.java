package com.hana.hanalink.plan.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.plan.dto.PlanPostReq;
import com.hana.hanalink.plan.service.ImageUploadService;
import com.hana.hanalink.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlanController {

    private final PlanService planService;
    private final ImageUploadService imageUploadService;

    @PostMapping(value = "/plan/{teamId}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<Long> postPlan(@PathVariable("teamId") Long teamId, @RequestPart("planPost") PlanPostReq planPostReq, @RequestPart(value = "planImg")MultipartFile image){
        String imagePath = imageUploadService.saveImage(image);
        return SuccessResponse.success(planService.postPlan(teamId,planPostReq,imagePath));
    }
}
