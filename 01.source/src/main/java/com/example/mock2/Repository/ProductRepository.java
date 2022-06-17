package com.example.mock2.Repository;

import com.example.mock2.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductName(String productName);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByProductName(String name,Pageable pageable);
    @Query(nativeQuery = true,value = "select p.* from product p join category c on p.categoryId = c.categoryId " +
            "where c.categoryName = ?1")
    List<Product> findByCategoryName(String name);

    @Query(nativeQuery = true,value = "select p.* from product p join category c on p.categoryId = c.categoryId " +
            "where c.categoryName = ?1",countQuery = "select count(*) from product p join category c on p.categoryId = c.categoryId " +
            "where c.categoryName = ?1"
    )
    Page<Product> findByCategoryNamePagination(String name,Pageable pageable);
    Product save(Product product);

}
