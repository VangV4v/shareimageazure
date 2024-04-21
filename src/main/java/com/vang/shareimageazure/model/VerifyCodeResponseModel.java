package com.vang.shareimageazure.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeResponseModel implements Serializable {
    private String message;
    private boolean status;
}