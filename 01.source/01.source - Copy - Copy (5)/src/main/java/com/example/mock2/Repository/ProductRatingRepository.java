package com.example.mock2.Repository;

import com.example.mock2.Entity.Rating;
import com.example.mock2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRatingRepository extends JpaRepository<Rating,Long> {
    Optional<Rating> findByUserIdAndProductId(long userid, long productId);

    List<Rating> findByProductId(long productId);


}
