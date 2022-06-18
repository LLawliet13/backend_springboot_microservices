package com.example.mock2.Controller;


import com.example.mock2.Service.DeliveryStatusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DeliveryStatusController {

    private DeliveryStatusService deliveryStatusService;
}
