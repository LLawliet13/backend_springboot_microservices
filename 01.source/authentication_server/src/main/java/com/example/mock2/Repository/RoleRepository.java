package com.example.mock2.Repository;

import com.example.mock2.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = "select * from role r where r.roleName = ?1",nativeQuery = true)
    Role findByRoleName(String role_user);
}
