package com.project.repository;

import com.project.model.dto.OrderDto;
import com.project.model.dto.ProductInfoDto;
import com.project.model.dto.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderDtoRepository extends CrudRepository<OrderDto,Long> {
    @Query(value = "SELECT c.* FROM CHECKOUT c " +
            "WHERE (:firstname is null or first_name like %:firstname%) " +
            "and (:lastname is null or last_name like %:lastname%) " +
            "and (:phone is null or phone like %:phone%) " +
            "and (:status is null or status = :status) "
            ,
            countQuery = "SELECT count(1) FROM CHECKOUT  " +
                    "WHERE (:firstname is null or first_name like %:firstname%) " +
                    "and (:lastname is null or last_name like %:lastname%) " +
                    "and (:phone is null or phone like %:phone%) " +
                    "and (:status is null or status = :status) "
            , nativeQuery = true)
    Page<OrderDto> selectParams(
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("phone") String phone,
            @Param("status") String status,
            Pageable pageable);
}
