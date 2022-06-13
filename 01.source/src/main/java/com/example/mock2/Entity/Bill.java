package com.example.mock2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId")
    private long billId;

    @Column(name = "totalPrice")
    private int totalPrice;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;


    @OneToOne (mappedBy = "bill")
    private Delevery delevery;



    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "bill")

    private Set<BillDetail> billDetails;


}