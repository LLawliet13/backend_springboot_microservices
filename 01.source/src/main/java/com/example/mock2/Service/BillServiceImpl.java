package com.example.mock2.Service;

import com.example.mock2.DTO.BillDTO;
import com.example.mock2.Entity.Bill;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Repository.BillRepository;
import com.example.mock2.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@AllArgsConstructor
@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;
    private UserRepository userRepository;

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
        return billRepository.findBillByBillId(billId);
    }

    @Override
    public long checkout() {
        Bill bill = new Bill();
        Set<BillDetail> billDetails = cartService.convertToBillDetail(USERNAME);

        bill.setUserId(userRepository.getUserIdByUsername(USERNAME));
        bill.setTotalPrice(cartService.getTotalPrice(USERNAME));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        bill.setPurchaseDate(LocalDate.now().format(formatter));

        billRepository.save(bill);
        billDetails.forEach(billDetail -> billDetail.setBillId(bill.getBillId()));
        billDetailService.addBillDetails(billDetails);

        return bill.getBillId();
    }


}
