package com.vang.shareimageazure.controller;

import com.vang.shareimageazure.model.ImageModel;
import com.vang.shareimageazure.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
@PreAuthorize("hasRole('ROLE_USER')")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImageModel>> viewAll() {
        return imageService.viewAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageModel> viewDetail(@PathVariable("id") long id) {
        return imageService.viewDetail(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(MultipartFile image) {
        return imageService.uploadImage(image);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editImage(@RequestBody ImageModel model) {
        return imageService.editStatusImage(model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@RequestBody ImageModel model) {
        return imageService.removeImage(model);
    }
}