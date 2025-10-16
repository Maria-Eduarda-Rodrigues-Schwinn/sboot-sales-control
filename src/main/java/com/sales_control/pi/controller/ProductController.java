package com.sales_control.pi.controller;

import com.sales_control.pi.dto.UpdateProductDTO;
import com.sales_control.pi.dto.request.CreateProductRequestDTO;
import com.sales_control.pi.dto.response.ProductResponseDTO;
import com.sales_control.pi.enumeration.CategoryEnum;
import com.sales_control.pi.enumeration.UnitOfMeasureEnum;
import com.sales_control.pi.service.ProductService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService service;

  @GetMapping
  public List<ProductResponseDTO> list() {
    return service.findAllDTO();
  }

  @GetMapping("/search")
  public List<ProductResponseDTO> search(@RequestParam String name) {
    return service.searchByName(name);
  }

  @PostMapping
  public ProductResponseDTO create(@RequestBody CreateProductRequestDTO dto) {
    return service.create(dto);
  }

  @PutMapping("/{id}")
  public ProductResponseDTO update(@PathVariable Integer id, @RequestBody UpdateProductDTO dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    service.delete(id);
  }

  @GetMapping("/categories")
  public List<String> getCategories() {
    return Arrays.stream(CategoryEnum.values()).map(CategoryEnum::getTranslation).toList();
  }

  @GetMapping("/units")
  public List<String> getUnitsOfMeasure() {
    return Arrays.stream(UnitOfMeasureEnum.values())
        .map(UnitOfMeasureEnum::getTranslation)
        .toList();
  }
}
