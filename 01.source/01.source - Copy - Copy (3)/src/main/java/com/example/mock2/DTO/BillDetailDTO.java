package com.example.mock2.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailDTO implements Comparable<BillDetailDTO> {

    private String productName;

    private long productPrice;

    private int quantity;

    @Override
    public int compareTo(BillDetailDTO o) {
        return this.getProductName().compareTo(o.getProductName());
    }

}
