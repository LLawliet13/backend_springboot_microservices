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
@Table(name ="user_product_rating")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userProductRatingId")
    private long userProductRatingId;

    @Column(name = "vote")
    private int vote;

    @Column(name = "productId")
    private long productId;

    @Column(name = "userId")
    private long userId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",updatable = false,insertable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId",updatable = false,insertable = false)
    private Product product;



}
