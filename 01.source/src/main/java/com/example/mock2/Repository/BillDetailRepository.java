package com.example.mock2.Repository;

import com.example.mock2.Entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    Set<BillDetail> findBillDetailByBillId(long billId);

}
