package com.example.mock2.Entity;

import com.example.mock2.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId")
    private long billId;

    @Column(name = "purchaseDate")
    private String purchaseDate;

    @Column(name = "totalPrice")
    private long totalPrice;

    @Column(name = "userId")
    private long userId;

//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", updatable = false,insertable = false)
    @Transient
    private UserDTO userDTO;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "bill")
    private Set<BillDetail> billDetails;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "bill")
    private Set<DeliveryStatus> deliveryStatuses;
}