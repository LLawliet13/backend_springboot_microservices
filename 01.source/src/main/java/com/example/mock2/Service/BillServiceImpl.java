package com.example.mock2.Service;

import com.example.mock2.DTO.BillDTO;
import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Exceptions.InputException;
import com.example.mock2.Repository.BillRepository;
import com.example.mock2.Repository.ProductRepository;
import com.example.mock2.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@AllArgsConstructor
@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    private DeliveryStatusService deliveryStatusService;
    private CartService cartService;
    private BillDetailService billDetailService;


    @Override
    public List<BillDTO> convertBillToBillDTO(List<Bill> bills) {
        List<BillDTO> billDTOList = new ArrayList<>();

        for (Bill bill:bills) {

            BillDTO billDTO = new BillDTO();
            billDTO.setBillId(bill.getBillId());
            billDTO.setPurchaseDate(bill.getPurchaseDate());
            billDTO.setTotalPrice(bill.getTotalPrice());
            billDTO.setBillDetailList(billDetailService.
                                        convertBillDetailToBillDetailDTO(bill.getBillDetails()));

            billDTO.setDeliverySet(deliveryStatusService.
                                        convertDeliveryToDeliveryDTO(bill.getDeliveryStatuses()));

            billDTOList.add(billDTO);
        }

        return billDTOList;
    }




    public List<Bill> findBillByUserId(long userId) {
        return billRepository.findBillByUserId(userId);
    }


    public List<Bill> findBillByUserUsername(String username) {
        return billRepository.findBillByUserUsername(username);
    }


    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill findBillByBillId(long billId) {
        Bill bill = billRepository.findBillByBillId(billId);
        if (bill == null) {
            throw new InputException("Bill not exist. Please check again!");
        }
        return bill;
    }

    @Override
    public long checkout() {
        Bill bill = new Bill();

        Set<BillDetail> billDetails = cartService.convertToBillDetail(USERNAME);

        bill.setUserId(userRepository.getUserIdByUsername(USERNAME));
        bill.setTotalPrice(cartService.getTotalPrice(USERNAME));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        bill.setPurchaseDate(LocalDate.now().format(formatter));

        try {
            billRepository.save(bill);
        } catch (Exception ex) {
            throw new InputException("Cart must not empty. Please check again!");
        }

        bill.setBillDetails(billDetails);

        billDetails.forEach(billDetail -> billDetail.setBillId(bill.getBillId()));
        billDetailService.addBillDetails(billDetails);
//        bill.setBillDetails(billDetails);

        deliveryStatusService.addDefaultDeliveryStatus(bill);

        return bill.getBillId();
    }


    @Transactional
    public void updateBill(long billId, String[] productName, int[] quantity) {

        Set<BillDetail> billDetailSet = new HashSet<>();
        long totalPrice = 0;

        for (int i = 0; i < productName.length; i++) {

            if (quantity[i] < 0) {
                throw new InputException("Quantity must greater than 0!");
            }

            BillDetail billDetail = new BillDetail();
            billDetail.setBillDetailQuantity(quantity[i]);
            long productId;
            try {
                productId = productRepository.getProductIdByProductName(productName[i]);
            } catch (Exception ex){
                throw new InputException("Product name: " + productName[i] + " invalid!");
            }
            totalPrice += productRepository.getProductPrice(productId) * quantity[i];

            billDetail.setProductId(productId);
            billDetail.setBillDetailPrice(productRepository.getProductPrice(productId));
            billDetail.setBillId(billId);

            billDetailSet.add(billDetail);
        }

        Bill old_bill = billRepository.findBillByBillId(billId);

        if (old_bill == null) {

            throw new InputException("Bill not exist. Please check again!");

        } else {
            billDetailService.deleteBillDetailsByBillId(billId);
            old_bill.setBillDetails(billDetailSet);
            old_bill.setTotalPrice(totalPrice);
            billRepository.save(old_bill);
        }
    }



    public void deleteBill(long billId) {
        Bill bill = billRepository.findBillByBillId(billId);

        if (bill == null) {

            throw new InputException("Bill not exist. Please check again!");

        } else {
            billRepository.delete(bill);
        }
    }



}
