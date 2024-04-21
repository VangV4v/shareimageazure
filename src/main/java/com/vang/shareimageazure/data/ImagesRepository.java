package com.vang.shareimageazure.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {

    @Query(value = "select imageid,userid,imagename,imageurl,status,createddate from images where userid = ?1 and status = 1 order by createddate DESC ", nativeQuery = true)
    List<Images> findAllByUserId(long userId);
}