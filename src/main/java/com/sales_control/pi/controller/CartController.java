package com.sales_control.pi.controller;

import com.sales_control.pi.dto.CartItemDTO;
import com.sales_control.pi.service.CartService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;
  private final List<CartItemDTO> cart = new ArrayList<>();

  @PostMapping("/add")
  public List<CartItemDTO> add(@RequestParam Integer productId, @RequestParam Integer qty) {
    cartService.addItem(cart, productId, qty);
    return cart;
  }

  @PostMapping("/remove")
  public List<CartItemDTO> remove(@RequestParam Integer productId) {
    cartService.removeItem(cart, productId);
    return cart;
  }

  @PostMapping("/clear")
  public List<CartItemDTO> clear() {
    cartService.clearCart(cart);
    return cart;
  }

  @GetMapping
  public List<CartItemDTO> get() {
    return cart;
  }
}
