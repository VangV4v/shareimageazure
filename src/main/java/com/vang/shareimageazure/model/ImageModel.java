package com.vang.shareimageazure.model;

import com.vang.shareimageazure.data.Users;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ImageModel implements Serializable {

    private long imageid;
    private String imagename;
    private String imageurl;
    private int status;
    private LocalDateTime createddate;
    private Users userid;
}