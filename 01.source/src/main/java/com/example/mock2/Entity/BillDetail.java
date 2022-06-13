package com.example.mock2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="bill_detail")
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billDetailId")
    private long billDetailId;

    @Column(name = "billDetailQuantity")
    private int billDetailQuantity;

    @Column(name = "billDetailTotalPrice")
    private long billDetailTotalPrice;

    @Column(name = "productId")
    private long productId;

    @Column(name = "billId")
    private long billId;


}