package com.example.mock2.Controller;

import com.example.mock2.DTO.CartDTO;
import com.example.mock2.Entity.Cart;
import com.example.mock2.Service.CartService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
public class CartController {

    private CartService cartService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);


//    @GetMapping("/cart")
//    public ResponseEntity<List<CartDTO>> getCartFromUser() {
//
//        List<Cart> carts = cartService.findCartByUserUsername(USERNAME);
//        List<CartDTO> cartDTOList = cartService.convertCartToCartDTO(carts);
//        return ResponseEntity.status(HttpStatus.OK).body(cartDTOList);
//    }


    @GetMapping("/cart")
    public ResponseEntity<?> getCartFromUser() {

        List<Cart> carts = cartService.findCartByUserUsername(USERNAME);
        List<CartDTO> cartDTOList = cartService.convertCartToCartDTO(carts);
        long totalPrice = cartService.getTotalPrice(USERNAME);

        Map<String, Object> result = new LinkedHashMap<String,Object>();

        result.put("All products in cart", cartDTOList);
        result.put("Total price:", totalPrice);


        LOGGER.warn("User " + USERNAME + " has been view on cart");
        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);

    }


//    POST cart: nếu quantity <= 0 ném Exception
//    Chỉ cho phép add 1 product duy nhất cho 1 request
    @PostMapping("/cart")
    public String addCart(@RequestParam String productName, @RequestParam @Min(value = 1, message = "quantity must greater than 0") int quantity) {

        cartService.addCart(productName, quantity);

        return "Product add to cart successfully!";
    }

//    PUT cart: không cho phép add new product,
//    Cho phép chỉnh sửa số lượng nhiều sản phẩm cùng lúc
    @PutMapping("/cart")
    public String updateCart(@RequestParam String[] productName, @RequestParam int[] quantity) {

        cartService.updateCart(productName, quantity);

        return "Cart has been updated";
    }



    @DeleteMapping("/cart/reset")
    public String resetCart() {
        cartService.resetCart();
        return "Cart has been reset";
    }


}
