package com.example.mock2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="productMedia")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productMediaId")
    private long productMediaId;

    @Column(name = "path")
    private String path;

    @Column(name = "type")
    private String type;

    @Column(name = "productId")
    private long productId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "productId", updatable = false,insertable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMedia)) return false;
        ProductMedia that = (ProductMedia) o;
        return getProductId() == that.getProductId() && Objects.equals(getPath(), that.getPath()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getType(), getProductId());
    }
}
