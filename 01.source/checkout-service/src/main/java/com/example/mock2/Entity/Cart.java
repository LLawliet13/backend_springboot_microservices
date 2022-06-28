package com.example.mock2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="cart")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private long cartId;

    @Min(value = 1, message = "quantity must greater than 0")
    @Column(name = "cartQuantity")
    private int cartQuantity;

    @Column(name = "productId")
    private long productId;

    @JsonIgnore
    @Column(name = "userId")
    private long userId;







}
