package com.vang.shareimageazure.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResetPasswordRequestRepository extends JpaRepository<ResetPasswordRequest, Integer> {

    @Query(value = "select id,email,code,createddate,status from resetpasswordrequest where email = ?1 and code = ?2 and status = 1", nativeQuery = true)
    ResetPasswordRequest getByEmailAndCode(String email, String code);

    @Query(value = "select top 1 id,email,code,createddate,status from resetpasswordrequest where email = ?1 and status = 1 order by createddate desc ", nativeQuery = true)
    ResetPasswordRequest getByEmail(String email);
}