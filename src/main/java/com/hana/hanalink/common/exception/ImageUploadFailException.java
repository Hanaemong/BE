package com.hana.hanalink.common.exception;

import com.hana.hanalink.common.exception.AccessDeniedException;

public class ImageUploadFailException extends AccessDeniedException {
    public ImageUploadFailException(){
        super("Could not upload image");
    }

    public ImageUploadFailException(String message){
        super(message);
    }
}
