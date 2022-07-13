package com.example.mock2.Repository;

import com.example.mock2.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    // USER role API

    List<Bill> findBillByUserId(long userId);


    @Query (value = "SELECT * FROM bill WHERE userId = " +
            "(SELECT userId FROM user WHERE username = ?1)", nativeQuery = true)
    List<Bill> findBillByUserUsername(String username);

    Bill findBillByBillId(long billId);

    //ADMIN role API

    @Query("SELECT b FROM Bill b")
    List<Bill> findAll();

}
