package com.example.mock2.Service;

import com.example.mock2.DTO.BillDetailDTO;
import com.example.mock2.Entity.BillDetail;

import java.util.Set;

public interface BillDetailService {

    void addBillDetails(Set<BillDetail> billDetails);

    Set<BillDetailDTO> convertBillDetailToBillDetailDTO(Set<BillDetail> billDetails);


    Set<BillDetail> findByBillId(long billId);

    void deleteBillDetailsByBillId(long billId);
}
