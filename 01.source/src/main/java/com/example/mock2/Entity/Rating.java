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
@Table(name ="user_product_rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userProductRatingId")
    private long userProductRatingId;

    @Column(name = "vote")
    private int vote;

    private long productId;
    private long userId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId",updatable = false,insertable = false)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",updatable = false,insertable = false)
    private User user;



}
