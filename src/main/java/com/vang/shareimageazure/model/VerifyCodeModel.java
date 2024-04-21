package com.vang.shareimageazure.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeModel implements Serializable {
    private String email;
    private String code;
}