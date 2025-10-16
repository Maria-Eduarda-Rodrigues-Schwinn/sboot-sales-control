package com.sales_control.pi.repository;

import com.sales_control.pi.entity.ProductEntity;
import com.sales_control.pi.enumeration.CategoryEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
  List<ProductEntity> findByNameContainingIgnoreCase(String name);

  List<ProductEntity> findByCategory(CategoryEnum category);
}
