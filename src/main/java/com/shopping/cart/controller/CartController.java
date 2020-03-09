package com.shopping.cart.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.service.CartService;
import com.shopping.common.APIResponse;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;


    @GetMapping("/{user_id}")
    public ResponseEntity getCart(@PathVariable Long user_id) {
        APIResponse response = new APIResponse();
        Optional<Cart> cart = cartService.getCart(user_id);

        if (cart.isEmpty()) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        } else {
            response.setData(cart.get());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity updateGoodsToCart(@RequestBody CartDto cartDto) {
        APIResponse response = new APIResponse();
        Long result = cartService.updateGoodsToCart(cartDto);

        if (result <= 0) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        }

        return ResponseEntity.ok(response);
    }

}
