package com.example.mock2.Service;


import com.example.mock2.DTO.CartDTO;
import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Entity.Cart;
import com.example.mock2.Exceptions.InputException;
import com.example.mock2.Repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;
import static com.example.mock2.filter.CustomAuthorizationFilter.token_in_header;


@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;

    private RestTemplate restTemplate;

    @Override
    public List<CartDTO> convertCartToCartDTO(List<Cart> carts) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);


        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Cart cart:carts) {

            CartDTO cartDTO = new CartDTO();
            long productId = cart.getProductId();
            ProductDTO productDTO = restTemplate.exchange("http://product-service/Product/SearchId/"+productId,
                    HttpMethod.GET,entity,ProductDTO.class).getBody();

            cartDTO.setProductName(productDTO.getProductName());
            cartDTO.setProductPrice(productDTO.getProductPrice());
            cartDTO.setQuantity(cart.getCartQuantity());

            cartDTOList.add(cartDTO);
        }

        return cartDTOList;
    }


    @Override
    public List<Cart> findCartByUserUsername(String username) {
        return cartRepository.findCartByUserUsername(username);
    }

    public void addCart(String productName, int quantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ProductDTO productDTO;
        try {
            productDTO = restTemplate.exchange("http://product-service/Product/SearchExact/" + productName,
                    HttpMethod.GET, entity, ProductDTO.class).getBody();
        } catch (Exception ex) {
            throw new InputException("Product not exist. Please check again!");
        }

        UserDTO userDTO = restTemplate.exchange("http://user-service/User/Search/"+USERNAME,
                HttpMethod.GET,entity,UserDTO.class).getBody();

        Cart cart = new Cart();
        cart.setCartQuantity(quantity);
        cart.setProductId(productDTO.getProductId());
        cart.setUserId(userDTO.getUserId());

        Cart old_cart = cartRepository.findCartByProductIdAndUserId(cart.getProductId(), cart.getUserId());

        if (old_cart == null) {

            cartRepository.save(cart);

        } else {
            int newQuantity = old_cart.getCartQuantity() + cart.getCartQuantity();

            old_cart.setCartQuantity(newQuantity);
            cartRepository.save(old_cart);
        }
    }

    public void updateCart(String[] productName, int[] quantity) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        UserDTO userDTO = restTemplate.exchange("http://user-service/User/Search/"+USERNAME,
                HttpMethod.GET,entity,UserDTO.class).getBody();


        for (int i = 0; i < productName.length; i++) {
            Cart cart = new Cart();
            cart.setCartQuantity(quantity[i]);

            try {
                ProductDTO productDTO = restTemplate.exchange("http://product-service/Product/SearchExact/"+productName[i],
                        HttpMethod.GET,entity,ProductDTO.class).getBody();

                cart.setProductId(productDTO.getProductId());
            } catch (Exception ex) {
                throw new InputException("Product name invalid. Please check again!");
            }


            cart.setUserId(userDTO.getUserId());

            Cart old_cart = cartRepository.findCartByProductIdAndUserId(cart.getProductId(), cart.getUserId());

            if (old_cart == null) {

                throw new InputException("Product name not exist in cart. Please check again!");

            } else {
                int newQuantity = old_cart.getCartQuantity() + cart.getCartQuantity();
                if (newQuantity == 0) {
                    cartRepository.delete(old_cart);
                } else {
                    old_cart.setCartQuantity(newQuantity);
                    cartRepository.save(old_cart);
                }
            }
        }
    }

    @Transactional
    public void resetCart() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        UserDTO userDTO = restTemplate.exchange("http://user-service/User/Search/"+USERNAME,
                HttpMethod.GET,entity,UserDTO.class).getBody();

        long userId = userDTO.getUserId();
        cartRepository.deleteCartsByUserId(userId);
    }

    public long getTotalPrice(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        List<Cart> carts = findCartByUserUsername(username);
        long totalPrice = 0;

        for (Cart cart : carts) {
            long productId = cart.getProductId();

            ProductDTO productDTO = restTemplate.exchange("http://product-service/Product/SearchId/"+productId,
                    HttpMethod.GET,entity,ProductDTO.class).getBody();

            totalPrice += productDTO.getProductPrice()
                            * cart.getCartQuantity();
        }
        return totalPrice;
    }


    public Set<BillDetail> convertToBillDetail(String username) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        Set<BillDetail> billDetails = new HashSet<>();
        List<Cart> carts = findCartByUserUsername(username);

        for (Cart cart : carts) {
            BillDetail billDetail = new BillDetail();
            long productId = cart.getProductId();

            ProductDTO productDTO = restTemplate.exchange("http://product-service/Product/SearchId/"+productId,
                    HttpMethod.GET,entity,ProductDTO.class).getBody();

            billDetail.setBillDetailQuantity(cart.getCartQuantity());
            billDetail.setProductId(productId);
            billDetail.setBillDetailPrice(productDTO.getProductPrice());

            billDetails.add(billDetail);
        }

        return billDetails;
    }



}
