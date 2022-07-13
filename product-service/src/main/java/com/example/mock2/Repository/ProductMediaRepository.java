package com.example.mock2.Repository;

import com.example.mock2.Entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMediaRepository extends JpaRepository<ProductMedia,Long> {
    List<ProductMedia> findAllByProductId(long productId);

}
