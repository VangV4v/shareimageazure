package com.vang.shareimageazure.model;

import com.vang.shareimageazure.data.Users;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ResetPasswordRequestModel implements Serializable {

    private int id;
    private String email;
    private String code;
    private LocalDateTime createddate;
    private int status;
}