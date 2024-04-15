package com.vang.shareimageazure.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class ResponseCommonModel implements Serializable {

    private List<String> message;
    private boolean error = false;
}