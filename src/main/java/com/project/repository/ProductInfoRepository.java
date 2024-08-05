package com.project.repository;

import com.project.table.ProductInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends CrudRepository<ProductInfo,Long> {
    ProductInfo findByProductName(String productname);

    long countByProductName(String ProductName);
}
