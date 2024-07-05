package com.hana.hanalink.common.exception;


public class ImageUploadFailException extends AccessDeniedException {
    public ImageUploadFailException(){
        super("Could not upload image");
    }

    public ImageUploadFailException(String message){
        super(message);
    }
}
