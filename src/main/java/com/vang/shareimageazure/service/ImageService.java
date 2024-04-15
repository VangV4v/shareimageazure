package com.vang.shareimageazure.service;

import com.vang.shareimageazure.model.ImageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    public ResponseEntity<List<ImageModel>> viewAll();

    public ResponseEntity<ImageModel> viewDetail(long id);
    public ResponseEntity<String> uploadImage(MultipartFile image);

    public ResponseEntity<String> editStatusImage(ImageModel model);

    public ResponseEntity<String> removeImage(ImageModel model);
}
