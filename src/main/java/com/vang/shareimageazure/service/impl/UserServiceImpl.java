package com.vang.shareimageazure.service.impl;

import com.vang.shareimageazure.constant.Common;
import com.vang.shareimageazure.data.Users;
import com.vang.shareimageazure.data.UsersRepository;
import com.vang.shareimageazure.model.UserModel;
import com.vang.shareimageazure.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UsersRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<List<UserModel>> getAll() {

        List<Users> listUsers = repository.findAll();
        List<UserModel> listModels = new ArrayList<>();
        listUsers.forEach(e -> {
            UserModel model = new UserModel();
            BeanUtils.copyProperties(e, model);
            listModels.add(model);
        });
        return new ResponseEntity<>(listModels, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserModel> addUser(UserModel model) {

        long countCheckUsername = repository.countByUsername(model.getUsername());
        long countCheckEmail = repository.countByEmail(model.getEmail());
        List<String> messageErr = new ArrayList<>();
        if(countCheckUsername > Common.NumberCommon.ZERO) {
            messageErr.add(Common.MessageCommon.USERNAME_EXIST);
            model.setError(true);
        }
        if(countCheckEmail > Common.NumberCommon.ZERO) {
            messageErr.add(Common.MessageCommon.EMAIL_EXIST);
            model.setError(true);
        }
        if(model.isError()) {
            model.setMessage(messageErr);
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }else {
            Users users = new Users();
            model.setAvatar(Common.ImageCommon.AVATAR_DEFAULT);
            model.setRole(Common.ROLE_USER);
            model.setPassword(passwordEncoder.encode(model.getPassword()));
            model.setActivestatus(Common.NumberCommon.ZERO);
            BeanUtils.copyProperties(model, users);
            repository.save(users);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<UserModel> editUser(UserModel model) {

        long countCheckUsername = repository.countByUpdateUsername(model.getUsername(), model.getHdnUsername());
        long countCheckEmail = repository.countByUpdateEmail(model.getEmail(), model.getHdnEmail());
        List<String> messageErr = new ArrayList<>();
        if(countCheckUsername > Common.NumberCommon.ZERO) {
            messageErr.add(Common.MessageCommon.USERNAME_EXIST);
            model.setError(true);
        }
        if(countCheckEmail > Common.NumberCommon.ZERO) {
            messageErr.add(Common.MessageCommon.EMAIL_EXIST);
            model.setError(true);
        }
        if(model.isError()) {
            model.setMessage(messageErr);
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }else {
            Users users = new Users();
            BeanUtils.copyProperties(model, users);
            repository.save(users);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(long id) {

        repository.deleteById(id);
        return new ResponseEntity<>(Common.MessageCommon.DELETE_USER_SUCCESS, HttpStatus.OK);
    }
}