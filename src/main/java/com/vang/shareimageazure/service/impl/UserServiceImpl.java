package com.vang.shareimageazure.service.impl;

import com.vang.shareimageazure.constant.Common;
import com.vang.shareimageazure.data.ResetPasswordRequest;
import com.vang.shareimageazure.data.ResetPasswordRequestRepository;
import com.vang.shareimageazure.data.Users;
import com.vang.shareimageazure.data.UsersRepository;
import com.vang.shareimageazure.model.*;
import com.vang.shareimageazure.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;

    private final ResetPasswordRequestRepository resetPasswordRequestRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final TemplateEngine templateEngine;

    private JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(UsersRepository repository, BCryptPasswordEncoder passwordEncoder, JavaMailSender mailSender, TemplateEngine templateEngine, ResetPasswordRequestRepository resetPasswordRequestRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.resetPasswordRequestRepository = resetPasswordRequestRepository;
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
            model.setActivestatus(Common.NumberCommon.ONE);
            BeanUtils.copyProperties(model, users);
            repository.save(users);
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<UserModel> editUser(UserModel model) {

        long countCheckUsername = repository.countByUpdateUsername(model.getUsername(), model.getHdnUsername());
        long countCheckEmail = repository.countByUpdateEmail(model.getEmail(), model.getHdnEmail());
          boolean checkPassword = checkPassword(model.getPassword());
        List<String> messageErr = new ArrayList<>();
        if(countCheckUsername > Common.NumberCommon.ZERO) {
            messageErr.add(Common.MessageCommon.USERNAME_EXIST);
            model.setError(true);
        }
        if(countCheckEmail > Common.NumberCommon.ZERO) {
            messageErr.add(Common.MessageCommon.EMAIL_EXIST);
            model.setError(true);
        }
        if(!checkPassword) {
            model.setPassword(passwordEncoder.encode(model.getPassword()));
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

    @Override
    public ResponseEntity<String> resetPassword(String email) {
        int sendCodeStatus = sendCode(email);
        if(sendCodeStatus == Common.NumberCommon.ONE) { //why condition = 1 because sendCode() return 1 if success
            return new ResponseEntity<>("Code has seen your Email", HttpStatus.OK);
        }else if(sendCodeStatus == Common.NumberCommon.TWO) {//why condition = 2 because sendCode() return 2 if error
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }else {//why condition = 3 because sendCode() return 3 if fail
            return new ResponseEntity<>("Email is not exist in system !", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<VerifyCodeResponseModel> verifyCode(VerifyCodeModel model) {

        ResetPasswordRequest resetPasswordRequest = resetPasswordRequestRepository.getByEmailAndCode(model.getEmail(), model.getCode());
        ResetPasswordRequestModel resetPasswordRequestModel = new ResetPasswordRequestModel();
        VerifyCodeResponseModel responseModel = new VerifyCodeResponseModel();
        if(ObjectUtils.isEmpty(resetPasswordRequest)) {
            responseModel.setStatus(false);
            responseModel.setMessage(Common.MessageCommon.VERIFY_002);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(resetPasswordRequest, resetPasswordRequestModel);
        boolean checkCode = resetPasswordRequestModel.getCode().equals(model.getCode());
        LocalDateTime timeAfter20Minute = resetPasswordRequestModel.getCreateddate().plusMinutes(20);//get time after 20 minute from time generate code add 20 minute
        boolean checkExpirationCode = LocalDateTime.now().isBefore(timeAfter20Minute);//if now less than the time after 20 minute then code can be used
        if(checkCode && checkExpirationCode) {
            responseModel.setStatus(true);
            responseModel.setMessage(Common.MessageCommon.VERIFY_002);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
        responseModel.setStatus(false);
        responseModel.setMessage(Common.MessageCommon.VERIFY_001);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> updatePassword(UpdatePasswordRequestModel model) {
        Users users = repository.getByEmail(model.getEmail());
        ResetPasswordRequest passwordRequest = resetPasswordRequestRepository.getByEmail(model.getEmail());
        ResetPasswordRequestModel passwordRequestModel = new ResetPasswordRequestModel();
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(passwordRequest, passwordRequestModel);
        BeanUtils.copyProperties(users, userModel);
        passwordRequestModel.setStatus(Common.NumberCommon.ZERO);
        userModel.setPassword(passwordEncoder.encode(model.getPassword()));
        BeanUtils.copyProperties(userModel, users);
        BeanUtils.copyProperties(passwordRequestModel, passwordRequest);
        repository.save(users);
        resetPasswordRequestRepository.save(passwordRequest);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private int sendCode(String email) {
        long countCheckEmail = repository.countByEmail(email);
        Context context = new Context();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        String code = generateCode();
        if(countCheckEmail > Common.NumberCommon.ZERO) {
            try {
                context.setVariable(Common.VariableCommon.VAR_CODE, code);
                context.setVariable(Common.VariableCommon.VAR_EMAIL,email);
                String body = templateEngine.process(Common.EMAIL_TEMPLATE,context);
                message.setTo(email);
                message.setSubject(Common.SUBJECT_EMAIL);
                message.setText(body, true);
                mailSender.send(mimeMessage);
                saveData(code, email);
                return Common.NumberCommon.ONE;
            }catch (MessagingException e) {
                return Common.NumberCommon.TWO;
            }
        }else {
            return Common.NumberCommon.THREE;
        }
    }

    private void saveData(String code, String email) {
        ResetPasswordRequestModel model = new ResetPasswordRequestModel();
        ResetPasswordRequest passwordRequest = new ResetPasswordRequest();
        model.setCode(code);
        model.setStatus(Common.NumberCommon.ONE);
        model.setCreateddate(LocalDateTime.now());
        model.setEmail(email);
        BeanUtils.copyProperties(model, passwordRequest);
        resetPasswordRequestRepository.save(passwordRequest);
    }

    private boolean checkPassword(String password) {

        //this function uses check if u enter new password then return true, because when u add or edit, i create pattern u cannot enter '$'
        return password.contains(Common.CharacterSpecial.CHARACTER_001) && countOfCharacter(password) > Common.NumberCommon.TWO;
    }

    private int countOfCharacter(String str) {
        int count = 0;
        String[] arrStr = str.split("");
        for (String elm : arrStr) {
            if(elm.equals(Common.CharacterSpecial.CHARACTER_001)) {
                count++;
            }
        }
        return count;
    }

    private String generateCode() {

        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0;i < 6; i++) {
            buffer.append(random.nextInt(0, 9));
        }
        return buffer.toString();
    }
}