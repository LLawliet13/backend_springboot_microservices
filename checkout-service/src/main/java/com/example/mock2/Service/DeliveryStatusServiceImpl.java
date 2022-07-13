package com.example.mock2.Service;


import com.example.mock2.DTO.DeliveryDTO;
import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.DeliveryStatus;
import com.example.mock2.Repository.BillRepository;
import com.example.mock2.Repository.DeliveryStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@AllArgsConstructor
@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private DeliveryStatusRepository deliveryStatusRepository;

    private BillRepository billRepository;


    @Override
    public Set<DeliveryDTO> convertDeliveryToDeliveryDTO(Set<DeliveryStatus> deliveryStatusSet) {
        Set<DeliveryDTO> deliveryDTOSet = new TreeSet<>();

        for (DeliveryStatus deliveryStatus:deliveryStatusSet) {
            DeliveryDTO deliveryDTO = new DeliveryDTO();

            deliveryDTO.setDeliveryTime(deliveryStatus.getDeliveryTime());
            deliveryDTO.setStatus(deliveryStatus.getStatus());

            deliveryDTOSet.add(deliveryDTO);
        }

        return deliveryDTOSet;
    }


    public void addDefaultDeliveryStatus(Bill bill) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        DeliveryStatus deliveryStatus = new DeliveryStatus();
        deliveryStatus.setStatus("Pending");
        deliveryStatus.setDeliveryTime(LocalDateTime.now().format(formatter));
        deliveryStatus.setBillId(bill.getBillId());

        Set<DeliveryStatus> deliveryStatusSet = new HashSet<>();
        deliveryStatusSet.add(deliveryStatus);
        bill.setDeliveryStatuses(deliveryStatusSet);

    }

    public void addDeliveryStatus(String status, Bill bill) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        Set<DeliveryStatus> deliveryStatusSet = bill.getDeliveryStatuses();

        DeliveryStatus new_deliveryStatus = new DeliveryStatus();
        new_deliveryStatus.setStatus(status);
        new_deliveryStatus.setDeliveryTime(LocalDateTime.now().format(formatter));
        new_deliveryStatus.setBillId(bill.getBillId());


        deliveryStatusSet.add(new_deliveryStatus);
        bill.setDeliveryStatuses(deliveryStatusSet);
        billRepository.save(bill);
    }


    @Transactional
    public void deleteLatestDeliveryStatus(Bill bill) {
        Set<DeliveryStatus> deliveryStatusSet = bill.getDeliveryStatuses();
        long maxId = 0;
        for (DeliveryStatus deliveryStatus: deliveryStatusSet) {
            if (deliveryStatus.getDeliveryStatusId() > maxId) {
                maxId = deliveryStatus.getDeliveryStatusId();
            }
        }
        System.out.println(maxId);
        deliveryStatusRepository.deleteLatestDeliveryStatus(maxId);
    }
}
