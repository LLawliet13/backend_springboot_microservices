package com.example.mock2.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {

    private long billId;

    private String purchaseDate;

    private long totalPrice;

    private Set<BillDetailDTO> billDetailList;


}
