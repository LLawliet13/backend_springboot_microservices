package com.example.mock2.Service;


import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.DeliveryStatus;
import com.example.mock2.Repository.BillRepository;
import com.example.mock2.Repository.DeliveryStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private DeliveryStatusRepository deliveryStatusRepository;

    private BillRepository billRepository;

    public void addDefaultDeliveryStatus(Bill bill) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        DeliveryStatus deliveryStatus = new DeliveryStatus();
        deliveryStatus.setStatus("Pending");
        deliveryStatus.setDeliveryTime(LocalDateTime.now().format(formatter));
        deliveryStatus.setBillId(bill.getBillId());

        Set<DeliveryStatus> deliveryStatusSet = new HashSet<>();
        deliveryStatusSet.add(deliveryStatus);
        bill.setDeliveryStatuses(deliveryStatusSet);

        billRepository.save(bill);

    }
}
