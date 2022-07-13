package com.example.mock2.Service;

import com.example.mock2.DTO.BillDTO;
import com.example.mock2.Entity.Bill;

import java.util.List;

public interface BillService {

    List<BillDTO> convertBillToBillDTO(List<Bill> bills);

    List<Bill> findBillByUserId(long userId);

    List<Bill> findBillByUserUsername(String username);
    List<Bill> findAll();

    Bill findBillByBillId(long billId);

    long checkout();

    void updateBill(long billId, String[] productName, int[] quantity);

    void deleteBill(long billId);
}
