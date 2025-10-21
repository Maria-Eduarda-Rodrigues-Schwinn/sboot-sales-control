package com.sales_control.pi.controller;

import com.sales_control.pi.dto.CartDTO;
import com.sales_control.pi.dto.SaleDTO;
import com.sales_control.pi.dto.SaleFilterDTO;
import com.sales_control.pi.dto.SaleProductDTO;
import com.sales_control.pi.service.SaleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SalesController {

  private final SaleService saleService;

  @PostMapping
  public SaleDTO finalize(@RequestBody CartDTO cart) {
    return saleService.finalizeSale(cart);
  }

  @GetMapping
  public List<SaleDTO> list() {
    return saleService.findAllDTO();
  }

  @GetMapping("/filter")
  public List<SaleDTO> filter(
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      @RequestParam(required = false) String name,
      @RequestParam(defaultValue = "Todas") String category) {
    return saleService.filter(
        SaleFilterDTO.builder().fromDate(from).toDate(to).name(name).category(category).build());
  }

  @PostMapping("/calculate-total")
  public Double calculateTotalSalesValue(@RequestBody List<SaleProductDTO> items) {
    return saleService.calculateTotalSalesValue(items);
  }
}
