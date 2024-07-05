package com.hana.hanalink.plan.controller;

import com.hana.hanalink.common.dto.SuccessResponse;
import com.hana.hanalink.plan.dto.request.PlanPostReq;
import com.hana.hanalink.plan.dto.response.PlanRes;
import com.hana.hanalink.common.service.ImageUploadService;
import com.hana.hanalink.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plan")
public class PlanController {

    private final PlanService planService;
    private final ImageUploadService imageUploadService;

    @PostMapping(value = "/{teamId}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<Long> postPlan(@PathVariable("teamId") Long teamId, @RequestPart("planPost") PlanPostReq planPostReq, @RequestPart(value = "planImg", required = false)MultipartFile image) {
        String imagePath = "https://hanalinkbucket.s3.ap-northeast-2.amazonaws.com/planImg2.png";

        if (image != null && !image.isEmpty()) {
            imagePath = imageUploadService.saveImage(image);
        }
        return SuccessResponse.success(planService.postPlan(teamId,planPostReq,imagePath));
    }

    @GetMapping("/{teamId}")
    SuccessResponse<List<PlanRes>> getPlans(@PathVariable("teamId") Long teamId) {
        return SuccessResponse.success(planService.getPlans(teamId));
    }
}
