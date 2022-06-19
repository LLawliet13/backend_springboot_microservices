package com.example.mock2.Controller;

import com.example.mock2.DTO.BillDTO;
import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Service.BillDetailService;
import com.example.mock2.Service.BillService;
import com.example.mock2.Service.CartService;
import com.example.mock2.Service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BillController {


    private BillService billService;
    private BillDetailService billDetailService;
    private CartService cartService;

    private LogService logService;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/bill")
    public ResponseEntity<List<BillDTO>> getBillFromUser() {
        logService.info(USERNAME,"View Bill");
        List<Bill> bills = billService.findBillByUserUsername(USERNAME);
        List<BillDTO> billDTOList = billService.convertBillToBillDTO(bills);

        return ResponseEntity.status(HttpStatus.OK).body(billDTOList);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/bill/{billId}")
    public ResponseEntity<?> getBillFromBillId(@PathVariable long billId) {

        Map<String, Object> result = new LinkedHashMap<String,Object>();
        Bill bill = billService.findBillByBillId(billId);

        Set<BillDetail> billDetailSet = billDetailService.findByBillId(billId);
        result.put("Bill id", billId);
        result.put("Bill detail information", billDetailService
                .convertBillDetailToBillDetailDTO(billDetailSet));
        result.put("Total price", bill.getTotalPrice());

        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
    }


//    Checkout giỏ hàng: POST /bill/checkout chuyển thông tin cart
//    sang BillDetail thêm vào 1 bill được tạo mới
//    Xóa thông tin cart (giỏ hàng) hiện tại

    @Secured({ "ROLE_USER" })
    @PostMapping("/bill/checkout")
    public ResponseEntity<?> checkout() {

        long billId = billService.checkout();

        Bill bill = billService.findBillByBillId(billId);

        Map<String, Object> result = new LinkedHashMap<String,Object>();

        Set<BillDetail> billDetailSet = billDetailService.findByBillId(billId);
        result.put("Add successfully bill id", billId);
        result.put("Bill detail information", billDetailService
                                            .convertBillDetailToBillDetailDTO(billDetailSet));
        result.put("Total price", bill.getTotalPrice());

        cartService.resetCart();

        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
    }


    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/bill/{billId}")
    public ResponseEntity<?> updateBill(@PathVariable long billId,
                             @RequestParam String[] productName, @RequestParam int[] quantity) {

        billService.updateBill(billId, productName, quantity);

        Map<String, Object> result = new LinkedHashMap<String,Object>();

        Set<BillDetail> billDetailSet = billDetailService.findByBillId(billId);
        Bill bill = billService.findBillByBillId(billId);

        result.put("Updated successfully bill id", billId);
        result.put("Bill detail information", billDetailService
                .convertBillDetailToBillDetailDTO(billDetailSet));
        result.put("Total price", bill.getTotalPrice());


        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/bill/{billId}")
    public String deleteBill(@PathVariable long billId) {

        billService.deleteBill(billId);

        return "Bill id: " + billId + " has been deleted";
    }
}