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
@Table(name ="delivery_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryStatusId")
    private long deliveryStatusId;

    @Column(name = "status")
    private String status;

    @Column(name = "deliveryTime")
    private String deliveryTime;

    @Column(name = "billId")
    private long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId",updatable = false,insertable = false)
    private Bill bill;

}
