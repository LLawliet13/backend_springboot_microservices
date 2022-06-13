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
@Table(name ="delivery_status")
public class Delevery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryStatusId")
    private long deliveryStatusId;

    @Column(name = "status")
    private String status;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "billId")
    private Bill bill;

}
