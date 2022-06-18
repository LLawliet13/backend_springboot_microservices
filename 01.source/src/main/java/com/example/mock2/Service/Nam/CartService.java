package com.example.mock2.Service.Nam;

import com.example.mock2.DTO.CartDTO;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Entity.Cart;

import java.util.List;
import java.util.Set;

public interface CartService {

    List<CartDTO> convertCartToCartDTO(List<Cart> carts);

    List<Cart> findCartByUserUsername(String username);

    void addCart(String productName, int quantity);

    void updateCart(String[] productName, int[] quantity);

    void resetCart();

    long getTotalPrice(String username);

    Set<BillDetail> convertToBillDetail(String username);
}
