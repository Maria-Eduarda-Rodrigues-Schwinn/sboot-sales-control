package com.sales_control.pi.service;

import com.sales_control.pi.dto.CartItemDTO;
import com.sales_control.pi.exception.CartOperationException;
import com.sales_control.pi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  private final ProductRepository productRepo;

  @Transactional
  public void addItem(List<CartItemDTO> cart, Integer productId, Integer qty) {
    var product =
        productRepo
            .findById(productId)
            .orElseThrow(() -> new CartOperationException("Produto não encontrado"));

    if (qty <= 0 || product.getQuantity() < qty)
      throw new CartOperationException("Estoque insuficiente");

    var existingItem = cart.stream().filter(i -> i.productId().equals(productId)).findFirst();

    if (existingItem.isPresent()) {
      var item = existingItem.get();
      var newQty = item.quantity() + qty;

      cart.remove(item);
      cart.add(CartItemDTO.builder().productId(productId).quantity(newQty).build());
    } else {
      cart.add(CartItemDTO.builder().productId(productId).quantity(qty).build());
    }

    product.setQuantity(product.getQuantity() - qty);
  }

  @Transactional
  public void removeItem(List<CartItemDTO> cart, Integer productId) {
    var item =
        cart.stream()
            .filter(i -> i.productId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new CartOperationException("Produto não está no carrinho"));
    var p =
        productRepo
            .findById(productId)
            .orElseThrow(() -> new CartOperationException("Produto não encontrado"));
    p.setQuantity(p.getQuantity() + item.quantity());
    cart.remove(item);
  }

  @Transactional
  public void clearCart(List<CartItemDTO> cart) {
    for (var item : List.copyOf(cart)) {
      var p =
          productRepo
              .findById(item.productId())
              .orElseThrow(() -> new CartOperationException("Produto não encontrado"));
      p.setQuantity(p.getQuantity() + item.quantity());
      cart.remove(item);
    }
  }
}
