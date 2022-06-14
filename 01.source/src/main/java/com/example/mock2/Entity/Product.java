package com.example.mock2.Entity;

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
@Table(name ="product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productPrice")
    private long productPrice;

    @Column(name = "productQuantity")
    private int productQuantity;

    @Column(name = "productRating")
    private float productRating;

    @Column(name = "categoryId")
    private int categoryId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId",updatable = false,insertable = false)
    private Category category;

    @JsonBackReference
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "product")
    private Set<Rating> ratings;

    @JsonBackReference
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "product")
    private Set<Cart> carts;

    @JsonBackReference
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "product")
    private Set<BillDetail> billDetails;

    @JsonBackReference
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "product")
    private Set<ProductMedia> productMediaSet;

}
