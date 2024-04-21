package com.vang.shareimageazure.service;

import com.vang.shareimageazure.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<List<UserModel>> getAll();

    ResponseEntity<UserModel> addUser(UserModel model);

    ResponseEntity<UserModel> editUser(UserModel model);

    ResponseEntity<String> deleteUser(long id);

    ResponseEntity<String> resetPassword(String email);

    ResponseEntity<VerifyCodeResponseModel> verifyCode(VerifyCodeModel model);

    ResponseEntity<Boolean> updatePassword(UpdatePasswordRequestModel model);
}