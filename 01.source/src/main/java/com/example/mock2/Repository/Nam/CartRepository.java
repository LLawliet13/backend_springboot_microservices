package com.example.mock2.Repository.Nam;

import com.example.mock2.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUserUsername(String username);

    Cart findCartByProductIdAndUserId(long productId, long userId);

    void deleteCartsByUserUserId(long userId);
}
