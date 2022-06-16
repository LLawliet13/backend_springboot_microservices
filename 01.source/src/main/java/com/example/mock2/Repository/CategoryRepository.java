package com.example.mock2.Repository;

import com.example.mock2.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Set<Category> findByCategoryName(String name);
    Page<Category> findAll( Pageable pageable);
}
