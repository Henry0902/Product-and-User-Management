package com.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.table.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);

    long countByUsername(String userName);
}
