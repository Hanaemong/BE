package com.hana.hanalink.common.firebase;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessageRes {
    private String title;
    private String body;
    private String image;
    private Long teamId;
}
