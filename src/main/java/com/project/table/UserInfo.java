package com.project.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "USER_INFO")
@Data
public class UserInfo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Tên đăng nhập không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String username;
	
	@NotEmpty(message = "Mật khẩu không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String password;
	
	@NotEmpty(message = "Họ và tên không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String fullName;

	@NotEmpty(message = "Địa chỉ không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String address;

	@NotEmpty(message = "Số điện thoại không được để trống")
	@Nationalized
	@Size(max = 20, message = "Độ dài không quá 20 kí tự")
	String phone;
	
	Integer status;
	
	long groupId;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createTime;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String createBy;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String email;

	@OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Receipt> Receipts = new ArrayList<>();
}
