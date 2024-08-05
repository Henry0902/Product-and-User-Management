package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.table.UserModule;

public interface UserModuleRepository extends CrudRepository<UserModule, Long>{

	@Query(value = "SELECT * FROM USER_MODULE WHERE status=?1 ORDER BY place", nativeQuery = true)
	Iterable<UserModule> selectAll(Integer ttModuleHoatdong);

	@Query(value = "SELECT * FROM USER_MODULE WHERE PARENT_ID is null or PARENT_ID=0 UNION ALL "
			+ "SELECT tbdm.* FROM USER_GROUP_PERMISSION tbpq INNER JOIN USER_MODULE tbdm ON tbpq.MODULE_ID=tbdm.id WHERE tbpq.GROUP_ID=?1 order by place", nativeQuery = true)
	List<UserModule> selectGroupId(long groupId);

}
