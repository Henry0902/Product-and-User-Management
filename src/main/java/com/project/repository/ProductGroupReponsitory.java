package com.project.repository;

import com.project.table.ProductGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductGroupReponsitory extends CrudRepository<ProductGroup,Long> {
    @Query(value = "select * from product_group where (:gpname is null or group_product_name like %:gpname%) " +
            "and (:status is null or status = :status)",
            countQuery = "select count(1) from product_group where (:gpname is null or group_product_name like %:gpname%) " +
                    "and (:status is null or status = :status)", nativeQuery = true)
    Page<ProductGroup> selectParams(@Param("gpname") String gpname,
                                 @Param("status") Integer status,
                                 Pageable pageable);

    Iterable<ProductGroup> findByStatus(Integer ttNhomHoatdong);

    long countByGroupProductName(String name);

    @Query(nativeQuery = true,
            value = "select count(1) from product_group where group_product_name = :name and (:id is null or id != :id)")
    long countUpdate(@Param("name") String name,@Param("id") Long id);
}
