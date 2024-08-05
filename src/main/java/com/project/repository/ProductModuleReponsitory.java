package com.project.repository;

import com.project.table.ProductModule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductModuleReponsitory extends CrudRepository<ProductModule,Long> {
    @Query(value = "SELECT * FROM PRODUCT_MODULE WHERE status=?1 ORDER BY place", nativeQuery = true)
    Iterable<ProductModule> selectAll(Integer ttModuleHoatdong);

    @Query(value = "SELECT * FROM PRODUCT_MODULE WHERE PARENT_PRODUCT_ID is null or PARENT_PRODUCT_ID=0 UNION ALL "
            + "SELECT tbdm.* FROM USER_GROUP_PERMISSION tbpq INNER JOIN USER_MODULE tbdm ON tbpq.MODULE_ID=tbdm.id WHERE tbpq.GROUP_ID=?1 order by place", nativeQuery = true)
    List<ProductModule> selectGroupId(long groupId);
}
