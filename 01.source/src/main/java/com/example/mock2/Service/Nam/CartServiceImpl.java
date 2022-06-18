package com.example.mock2.Service.Nam;


import com.example.mock2.DTO.CartDTO;
import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Entity.Cart;
import com.example.mock2.Repository.Nam.CartRepository;
import com.example.mock2.Repository.ProductRepository;
import com.example.mock2.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;


@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Override
    public List<CartDTO> convertCartToCartDTO(List<Cart> carts) {
        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Cart cart:carts) {

            CartDTO cartDTO = new CartDTO();
            long productId = cart.getProductId();

            cartDTO.setProductName(productRepository.getProductNameByProductId(productId));
            cartDTO.setProductPrice(productRepository.getProductPrice(productId));
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
        Cart cart = new Cart();
        cart.setCartQuantity(quantity);
        cart.setProductId(productRepository.getProductIdByProductName(productName));
        cart.setUserId(userRepository.getUserIdByUsername(USERNAME));

        Cart old_cart = cartRepository.findCartByProductIdAndUserId(cart.getProductId(), cart.getUserId());

        if (old_cart == null) {

            cartRepository.save(cart);

        } else if (cart.getCartQuantity() <= 0) {

            throw new RuntimeException();

        } else {
            int newQuantity = old_cart.getCartQuantity() + cart.getCartQuantity();

            old_cart.setCartQuantity(newQuantity);
            cartRepository.save(old_cart);
        }
    }

    public void updateCart(String[] productName, int[] quantity) {

        for (int i = 0; i < productName.length; i++) {
            Cart cart = new Cart();
            cart.setCartQuantity(quantity[i]);
            cart.setProductId(productRepository.getProductIdByProductName(productName[i]));
            cart.setUserId(userRepository.getUserIdByUsername(USERNAME));

            Cart old_cart = cartRepository.findCartByProductIdAndUserId(cart.getProductId(), cart.getUserId());

            if (old_cart == null) {

                throw new RuntimeException();

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
        long userId = userRepository.getUserIdByUsername(USERNAME);
        cartRepository.deleteCartsByUserUserId(userId);
    }

    public long getTotalPrice(String username) {
        List<Cart> carts = findCartByUserUsername(username);
        long totalPrice = 0;

        for (Cart cart : carts) {
            long productId = cart.getProductId();
            totalPrice += productRepository.getProductPrice(productId)
                            * cart.getCartQuantity();
        }
        return totalPrice;
    }


    public Set<BillDetail> convertToBillDetail(String username) {
        Set<BillDetail> billDetails = new HashSet<>();
        List<Cart> carts = findCartByUserUsername(username);

        for (Cart cart : carts) {
            BillDetail billDetail = new BillDetail();
            long productId = cart.getProductId();

            billDetail.setBillDetailQuantity(cart.getCartQuantity());
            billDetail.setProductId(productId);
            billDetail.setBillDetailPrice(productRepository.getProductPrice(productId));

            billDetails.add(billDetail);
        }

        return billDetails;
    }



}
