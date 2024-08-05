package com.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.model.dto.UserInfoDto;

public interface UserInfoDtoRepository extends CrudRepository<UserInfoDto, Long> {
    @Query(value = "SELECT u.* FROM USER_INFO u " +
            "WHERE (:uname is null or username like %:uname%) " +
            "and (:fname is null or full_name like %:fname%) " +
            "and (:email is null or email like %:email%) " +
            "and (:status is null or status = :status) " 
            ,
            countQuery = "SELECT count(1) FROM USER_INFO  " +
                    "WHERE (:uname is null or username like %:uname%) " +
                    "and (:fname is null or full_name like %:fname%) " +
                    "and (:email is null or email like %:email%) " +
                    "and (:status is null or status = :status) " 
            , nativeQuery = true)
    Page<UserInfoDto> selectParams(
            @Param("uname") String uname,
            @Param("fname") String fname,
            @Param("email") String email,
            @Param("status") Integer status,
            Pageable pageable);
}
