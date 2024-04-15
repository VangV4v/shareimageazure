package com.vang.shareimageazure.controller;

import com.vang.shareimageazure.model.UserModel;
import com.vang.shareimageazure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAlls() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserModel> addUser(@RequestBody UserModel model) {
        return userService.addUser(model);
    }

    @PutMapping
    public ResponseEntity<UserModel> editUser(@RequestBody UserModel model) {
        return userService.editUser(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        return userService.deleteUser(id);
    }
}
