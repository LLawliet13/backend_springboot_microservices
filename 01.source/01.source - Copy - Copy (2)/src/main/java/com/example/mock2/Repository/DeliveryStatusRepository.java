package com.example.mock2.Repository;

import com.example.mock2.Entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {

    @Modifying
    @Query("DELETE FROM DeliveryStatus d WHERE d.deliveryStatusId = ?1")
    void deleteLatestDeliveryStatus(long id);
}
