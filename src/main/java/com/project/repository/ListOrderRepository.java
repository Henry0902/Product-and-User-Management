package com.project.repository;

import com.project.table.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListOrderRepository extends JpaRepository<Checkout, Long> {
    // Custom query methods if needed
}
