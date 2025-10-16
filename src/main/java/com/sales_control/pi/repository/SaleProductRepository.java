package com.sales_control.pi.repository;

import com.sales_control.pi.entity.SaleProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProductEntity, Long> {}
