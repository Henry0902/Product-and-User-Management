package com.project.repository;

import com.project.model.dto.UserInfoDto;
import com.project.table.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ReceiptRepository extends  JpaRepository<Receipt, Long> {

}
