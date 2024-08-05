package com.project.table;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "USER_GROUP_PERMISSION")
@Data
public class UserGroupPermission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	long groupId;
	long moduleId;


}
