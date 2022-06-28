package com.example.mock2.Service;

import com.example.mock2.DTO.DeliveryDTO;
import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.DeliveryStatus;

import java.util.Set;

public interface DeliveryStatusService {


    Set<DeliveryDTO> convertDeliveryToDeliveryDTO(Set<DeliveryStatus> deliveryStatusSet);
    void addDefaultDeliveryStatus(Bill bill);

    void addDeliveryStatus(String status, Bill bill);

    void deleteLatestDeliveryStatus(Bill bill);
}
