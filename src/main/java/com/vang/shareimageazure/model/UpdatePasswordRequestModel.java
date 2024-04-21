package com.vang.shareimageazure.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordRequestModel implements Serializable {

    private String email;
    private String password;
}