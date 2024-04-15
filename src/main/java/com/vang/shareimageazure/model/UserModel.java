package com.vang.shareimageazure.model;

import com.vang.shareimageazure.data.Images;
import lombok.Data;

import java.io.Serializable;
import java.util.SortedSet;

@Data
public class UserModel extends ResponseCommonModel implements Serializable {

    private long userid;
    private String firstname;
    private String lastname;
    private String username;
    private String hdnUsername;
    private String password;
    private String email;
    private String hdnEmail;
    private String role;
    private String avatar;
    private int activestatus;
    private SortedSet<Images> listImages;
}