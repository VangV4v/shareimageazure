package com.vang.shareimageazure.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadImageModel {

    private MultipartFile image;
}