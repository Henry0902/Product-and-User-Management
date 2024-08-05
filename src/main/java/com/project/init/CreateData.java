package com.project.init;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.common.CommonUtils;
import com.project.common.Contains;
import com.project.repository.UserGroupPermissionRepository;
import com.project.repository.UserGroupRepository;
import com.project.repository.UserInfoRepository;
import com.project.repository.UserModuleRepository;
import com.project.table.UserGroup;
import com.project.table.UserGroupPermission;
import com.project.table.UserInfo;
import com.project.table.UserModule;



@Component
public class CreateData {
	@Autowired UserModuleRepository userModuleRepository;
	@Autowired UserGroupRepository userGroupRepository;
	@Autowired UserInfoRepository userInfoRepository;
	@Autowired UserGroupPermissionRepository userGroupPermissionRepository;

	@PostConstruct
	void started() {
		UserInfo userInfo = userInfoRepository.findByUsername("supper_admin");
		if(userInfo == null) {
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupName("supper_admin");
			userGroup.setStatus(Contains.TT_NHOM_HOATDONG);
			userGroup.setCreateTime(new Date());
			userGroupRepository.save(userGroup);

			long idGroup = userGroup.getId();

			UserInfo info = new UserInfo();
			info.setFullName("supper_admin");
			info.setGroupId(idGroup);
			info.setPassword(CommonUtils.getMD5("abcD1234"));
			info.setUsername("supper_admin");
			info.setAddress("Hà Nội");
			info.setPhone("0123456789");
			info.setStatus(1);
			info.setCreateTime(new Date());
			userInfoRepository.save(info);


		}
	}
}
