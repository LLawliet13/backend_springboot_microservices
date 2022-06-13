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
@Table(name ="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private long userId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productPrice")
    private long productPrice;

    @Column(name = "productQuantity")
    private int productQuantity;

    @Column(name = "productRating")
    private float productRating;

}
