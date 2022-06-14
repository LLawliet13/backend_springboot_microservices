package com.example.mock2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billDetailId")
    private long billDetailId;

    @Column(name = "billDetailPrice")
    private long billDetailPrice;

    @Column(name = "billDetailQuantity")
    private int billDetailQuantity;

    @Column(name = "productId")
    private long productId;

    @Column(name = "billId")
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId",updatable = false,insertable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId",updatable = false,insertable = false)
    private Bill bill;


}