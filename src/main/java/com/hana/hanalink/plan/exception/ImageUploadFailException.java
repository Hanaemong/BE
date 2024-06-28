package com.hana.hanalink.plan.exception;

import com.hana.hanalink.common.exception.AccessDeniedException;

public class ImageUploadFailException extends AccessDeniedException {
    public ImageUploadFailException(){
        super("Could not upload image");
    }

    public ImageUploadFailException(String message){
        super(message);
    }
}
