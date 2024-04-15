package com.vang.shareimageazure.service;

import com.vang.shareimageazure.model.ResponseCommonModel;
import com.vang.shareimageazure.model.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<List<UserModel>> getAll();

    ResponseEntity<UserModel> addUser(UserModel model);

    ResponseEntity<UserModel> editUser(UserModel model);

    ResponseEntity<String> deleteUser(long id);
}