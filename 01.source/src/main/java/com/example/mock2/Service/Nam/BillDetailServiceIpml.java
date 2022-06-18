package com.example.mock2.Service.Nam;


import com.example.mock2.DTO.BillDetailDTO;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Repository.Nam.BillDetailRepository;
import com.example.mock2.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class BillDetailServiceIpml implements BillDetailService {

    private BillDetailRepository billDetailRepository;

    private ProductRepository productRepository;

    public void addBillDetails(Set<BillDetail> billDetails) {
        for (BillDetail billDetail : billDetails) {
            billDetailRepository.save(billDetail);
        }
    }

    @Override
    public Set<BillDetailDTO> convertBillDetailToBillDetailDTO(Set<BillDetail> billDetails) {
        Set<BillDetailDTO> billDetailDTOList = new TreeSet<>();

        for (BillDetail billDetail:billDetails) {
            BillDetailDTO billDetailDTO = new BillDetailDTO();

            if(billDetail.getProduct() == null) {
                long productId = billDetail.getProductId();
                billDetail.setProduct(productRepository.getProductByProductId(productId));
            }

            billDetailDTO.setProductName(billDetail.getProduct().getProductName());
            billDetailDTO.setProductPrice(billDetail.getProduct().getProductPrice());
            billDetailDTO.setQuantity(billDetail.getBillDetailQuantity());

            billDetailDTOList.add(billDetailDTO);
        }

        return billDetailDTOList;
    }



    public Set<BillDetail> findByBillId(long billId) {
        return billDetailRepository.findBillDetailByBillId(billId);
    }

}
