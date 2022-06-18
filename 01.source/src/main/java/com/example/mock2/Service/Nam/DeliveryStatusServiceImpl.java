package com.example.mock2.Service.Nam;


import com.example.mock2.Repository.Nam.DeliveryStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private DeliveryStatusRepository deliveryStatusRepository;
}
