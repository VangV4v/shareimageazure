package com.vang.shareimageazure.auth;

import com.vang.shareimageazure.constant.Common;
import com.vang.shareimageazure.data.Users;
import com.vang.shareimageazure.data.UsersRepository;
import com.vang.shareimageazure.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UsersRepository repository;

    @Autowired
    public MyUserDetailService(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = repository.getByUsername(username);
        UserModel model = new UserModel();
        if(ObjectUtils.isEmpty(users)) {
            throw new UsernameNotFoundException(Common.MessageCommon.USERNAME_NOTFOUND);
        }
        BeanUtils.copyProperties(users, model);
        List<GrantedAuthority> listGrant = List.of(new SimpleGrantedAuthority(model.getRole()));
        return new User(model.getUsername(), model.getPassword(), listGrant);
    }
}