package com.project.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "USER_MODULE")
@Data
public class UserModule implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String name;
	
	Integer status;
	
	Long parentId;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String url;
	
	long place;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String icon;
}
