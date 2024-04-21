package com.vang.shareimageazure.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "select userid,firstname,lastname,username,password,email,role,avatar,activestatus from users where username = ?1 and activestatus = 1", nativeQuery = true)
    Users getByUsername(String username);

    @Query(value = "select userid,firstname,lastname,username,password,email,role,avatar,activestatus from users where email = ?1 and activestatus = 1", nativeQuery = true)
    Users getByEmail(String email);

    @Query(value = "select count(username) from users where username = ?1 and activestatus = 1", nativeQuery = true)
    long countByUsername(String username);

    @Query(value = "select count(email) from users where email =?1 and activestatus = 1", nativeQuery = true)
    long countByEmail(String email);

    @Query(value = "select count(username) from users where username = ?1 and username != ?2 and activestatus = 1", nativeQuery = true)
    long countByUpdateUsername(String username, String oldUsername);

    @Query(value = "select count(email) from users where email = ?1 and email != ?2 and activestatus = 1", nativeQuery = true)
    long countByUpdateEmail(String username, String oldEmail);

    @Query(value = "select userid from users where username = ?1 and activestatus = 1", nativeQuery = true)
    long getUserIdByUsername(String username);
}