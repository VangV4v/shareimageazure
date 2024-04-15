package com.vang.shareimageazure.service.impl;

import org.modelmapper.ModelMapper;
import com.azure.storage.blob.BlobClient;
import com.vang.shareimageazure.configuation.AzureConfiguation;
import com.vang.shareimageazure.constant.Common;
import com.vang.shareimageazure.data.Images;
import com.vang.shareimageazure.data.ImagesRepository;
import com.vang.shareimageazure.data.Users;
import com.vang.shareimageazure.data.UsersRepository;
import com.vang.shareimageazure.model.ImageModel;
import com.vang.shareimageazure.service.ImageService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final UsersRepository usersRepository;

    private final ImagesRepository imagesRepository;

    private final AzureConfiguation azureConfiguation;

    @Autowired
    public ImageServiceImpl(UsersRepository usersRepository, ImagesRepository imagesRepository, AzureConfiguation azureConfiguation) {
        this.usersRepository = usersRepository;
        this.imagesRepository = imagesRepository;
        this.azureConfiguation = azureConfiguation;
    }

    @Override
    public ResponseEntity<ImageModel> viewDetail(long id) {
        Images images = imagesRepository.findById(id).orElse(new Images());
        ImageModel model = new ImageModel();
        if(StringUtils.isBlank(images.getImagename())) {
            return new ResponseEntity<>(new ImageModel(), HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(images, model);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ImageModel>> viewAll() {

        ModelMapper mapper = new ModelMapper();
        List<Images> listImages = imagesRepository.findAll();
        List<ImageModel> listModel = new ArrayList<>();
        listImages.forEach(e -> {
            ImageModel model = new ImageModel();
            BeanUtils.copyProperties(e, model);
            listModel.add(model);
        });
        return new ResponseEntity<>(listModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String imageName = username+"_"+(System.currentTimeMillis())+"_"+image.getOriginalFilename();
        BlobClient blobClient = azureConfiguation.containerClient().getBlobClient(imageName);
        Users users = usersRepository.getByUsername(username);
        Images images = new Images();
        ImageModel model = new ImageModel();
        model.setUserid(users);
        model.setStatus(Common.NumberCommon.ONE);
        model.setImagename(imageName);
        try {
            blobClient.upload(image.getInputStream(), image.getSize(), true);
            model.setImageurl(blobClient.getBlobUrl());
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(model, images);
        imagesRepository.save(images);
        return new ResponseEntity<>(blobClient.getBlobUrl(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> editStatusImage(ImageModel model) {

        Images images = new Images();
        int status = (model.getStatus() == 1 ? 0 : 1);
        model.setStatus(status);
        BeanUtils.copyProperties(model, images);
        imagesRepository.save(images);
        return new ResponseEntity<>("update Image success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeImage(ImageModel model) {

        BlobClient blobClient = azureConfiguation.containerClient().getBlobClient(model.getImagename());
        blobClient.delete();
        imagesRepository.deleteById(model.getImageid());
        return new ResponseEntity<>("delete Image success", HttpStatus.OK);
    }

}