package com.hana.hanalink.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse<T> {

    private boolean success;
    private String type;
    private T data;

    public static <T> SuccessResponse<T> success(T data) {
        return new SuccessResponse<>(true,"", data);
    }

    public static <T> SuccessResponse<T> successWithNoData() {
        return new SuccessResponse<>(true,"",null);
    }

}