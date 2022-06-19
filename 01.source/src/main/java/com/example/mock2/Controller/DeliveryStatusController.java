package com.example.mock2.Controller;


import com.example.mock2.Entity.Bill;
import com.example.mock2.Service.BillService;
import com.example.mock2.Service.DeliveryStatusService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DeliveryStatusController {

    private DeliveryStatusService deliveryStatusService;

    private BillService billService;

    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/delivery/{billId}")
    public String addDelivery(@PathVariable long billId, @RequestParam String status) {

        Bill bill = billService.findBillByBillId(billId);
        deliveryStatusService.addDeliveryStatus(status, bill);

        return "Delivery of bill id: " + billId + " updated";
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/delivery/{billId}")
    public String addDelivery(@PathVariable long billId) {

        Bill bill = billService.findBillByBillId(billId);
        deliveryStatusService.deleteLatestDeliveryStatus(bill);

        return "Delete latest delivery status of bill id: " + billId;
    }
}
