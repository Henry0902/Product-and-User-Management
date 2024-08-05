package com.project.model.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserInfoDto {
    @Id
    private Long id;

    String username;

    String fullName;

    String address;

    String phone;

    Integer status;

    long groupId;

    Date createTime;

    String createBy;

    String email;
}
