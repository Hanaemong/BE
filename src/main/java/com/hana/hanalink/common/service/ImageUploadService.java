package com.hana.hanalink.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hana.hanalink.common.exception.ImageUploadFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String saveImage(final MultipartFile multipartFile){
        final String originName = multipartFile.getOriginalFilename();

        final String ext = Objects.requireNonNull(originName).substring(originName.lastIndexOf("."));
        final String changedImageName = changeImageName(ext);

        return uploadImage(multipartFile,ext,changedImageName);
    }

    private String uploadImage(final MultipartFile image, final String ext, final String changedImageName) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/"+ext.substring(1));

        try {
            amazonS3.putObject(new PutObjectRequest(bucket,changedImageName,image.getInputStream(),metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));


        } catch (final IOException e) {
            throw new ImageUploadFailException();
        }

        return amazonS3.getUrl(bucket,changedImageName).toString();
    }


    private String changeImageName(final String ext) {
        final String uuid = UUID.randomUUID().toString();
        return uuid+ext;
    }
}
