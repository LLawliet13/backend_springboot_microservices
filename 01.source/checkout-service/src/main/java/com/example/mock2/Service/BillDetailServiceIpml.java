package com.example.mock2.Service;


import com.example.mock2.DTO.BillDetailDTO;
import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Exceptions.InputException;
import com.example.mock2.Repository.BillDetailRepository;
import com.example.mock2.Repository.BillRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.mock2.filter.CustomAuthorizationFilter.token_in_header;

@AllArgsConstructor
@Service
public class BillDetailServiceIpml implements BillDetailService {

    private RestTemplate restTemplate;

    private BillDetailRepository billDetailRepository;

    private BillRepository billRepository;


    public void addBillDetails(Set<BillDetail> billDetails) {
        for (BillDetail billDetail : billDetails) {
            billDetailRepository.save(billDetail);
        }
    }

    @Override
    public Set<BillDetailDTO> convertBillDetailToBillDetailDTO(Set<BillDetail> billDetails) {
        //
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);



        Set<BillDetailDTO> billDetailDTOList = new TreeSet<>();

        for (BillDetail billDetail:billDetails) {
            BillDetailDTO billDetailDTO = new BillDetailDTO();

            long productId = billDetail.getProductId();
            ProductDTO productDTO = restTemplate.exchange("http://product-service/Product/SearchId/"+productId,
                    HttpMethod.GET,entity,ProductDTO.class).getBody();

            billDetailDTO.setProductName(productDTO.getProductName());
            billDetailDTO.setProductPrice(productDTO.getProductPrice());
            billDetailDTO.setQuantity(billDetail.getBillDetailQuantity());

            billDetailDTOList.add(billDetailDTO);
        }

        return billDetailDTOList;
    }



    public Set<BillDetail> findByBillId(long billId) {
        Bill bill = billRepository.findBillByBillId(billId);

        if (bill == null) {
            throw new InputException("Bill not exist. Please check again!");
        }

        return billDetailRepository.findBillDetailByBillId(billId);
    }

    public void deleteBillDetailsByBillId(long billId) {
        billDetailRepository.deleteBillDetailsByBillId(billId);
    }

}
