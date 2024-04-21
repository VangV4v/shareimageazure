package com.vang.shareimageazure.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenerateCodeRequestModel implements Serializable {
    private String email;
}