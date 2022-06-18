package com.example.mock2.Service;


import com.example.mock2.Repository.DeliveryStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private DeliveryStatusRepository deliveryStatusRepository;
}
