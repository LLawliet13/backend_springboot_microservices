package com.example.mock2.Repository;

import com.example.mock2.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query(value = "SELECT * FROM cart WHERE userId = " +
            "(SELECT userId FROM user WHERE username = ?1)", nativeQuery = true)
    List<Cart> findCartByUserUsername(String username);

    Cart findCartByProductIdAndUserId(long productId, long userId);

    void deleteCartsByUserId(long userId);
}
