package com.vang.shareimageazure.service.impl;

import com.vang.shareimageazure.auth.JwtService;
import com.vang.shareimageazure.constant.Common;
import com.vang.shareimageazure.model.AuthRequestModel;
import com.vang.shareimageazure.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public ResponseEntity<String> authenticate(AuthRequestModel model) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));
            if(authentication.isAuthenticated()) {
                return new ResponseEntity<>(jwtService.generateToken(model.getUsername()), HttpStatus.OK);
            }
        }catch (BadCredentialsException e) {
            return new ResponseEntity<>(Common.MessageCommon.LOGIN_FAIL, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(Common.MessageCommon.LOGIN_FAIL, HttpStatus.UNAUTHORIZED);
    }
}