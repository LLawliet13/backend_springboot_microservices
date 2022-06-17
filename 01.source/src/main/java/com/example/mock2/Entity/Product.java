package com.example.mock2.Entity;

import com.example.mock2.DTO.ProductDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
    private long categoryId;


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

    public Product(long productId, String productName, long productPrice, int productQuantity, float productRating, long categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productRating = productRating;
        this.categoryId = categoryId;
    }

    public ProductDTO convertToProductDTO(){
        Float rating ;
        getRatings();
        if(ratings == null) rating = null;
        else {
            rating = 0f;

            for (Iterator<Rating> it = ratings.iterator(); it.hasNext(); ) {
                Rating r = it.next();
                rating += r.getVote();
            }
            rating/=ratings.size();

        }
        getCategory();
        getProductMediaSet();
        return new ProductDTO(productId,productName,productPrice,productQuantity,
                rating,category,productMediaSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductPrice() == product.getProductPrice() && getProductName().equals(product.getProductName()) && (getCategoryId() == product.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getProductPrice(), getCategoryId());
    }
}
