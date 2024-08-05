package com.project.table;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "USER_GROUP")
@Data
public class UserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Vui lòng nhập tên nhóm")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String groupName;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String description;
	
	Integer status;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createTime;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String createBy;
}
