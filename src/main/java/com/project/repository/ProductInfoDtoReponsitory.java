package com.project.repository;

import com.project.model.dto.ProductInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductInfoDtoReponsitory extends CrudRepository<ProductInfoDto,Long> {
    @Query(value = "SELECT p.* FROM PRODUCT_INFO p " +
            "WHERE (:pname is null or product_name like %:pname%) " +
            "and (:origin is null or product_origin like %:origin%) " +
            "and (:status is null or status = :status) "
            ,
            countQuery = "SELECT count(1) FROM PRODUCT_INFO  " +
                    "WHERE (:pname is null or product_name like %:pname%) " +
                    "and (:origin is null or product_origin like %:origin%) " +
                    "and (:status is null or status = :status) "
            , nativeQuery = true)
    Page<ProductInfoDto> selectParams(
            @Param("pname") String pname,
            @Param("origin") String origin,
            @Param("status") Integer status,
            Pageable pageable);
}
