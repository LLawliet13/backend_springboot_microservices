package com.example.mock2.Repository;

import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query ("SELECT u.userId FROM User u WHERE u.username = ?1")
    long getUserIdByUsername(String username);

    @Query("SELECT u FROM User u")
    List<User> findAll();

    @Query("SELECT p FROM Product p")
    List<Product> findAllProduct();

    User findByUsername(String username);

    User findById(long id);
}
