package com.vang.shareimageazure.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private long userid;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "activestatus")
    private int activestatus;
    @JsonIgnore
    @OneToMany(mappedBy = "userid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Images> listImages;
}