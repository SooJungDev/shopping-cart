package com.shopping.cart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.model.CartGoodsDto;
import com.shopping.cart.model.PurchaseCartGoddsDto;
import com.shopping.cart.model.PurchaseInfoDto;
import com.shopping.cart.service.CartService;
import com.shopping.common.APIResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
            return ResponseEntity.ok(response);
        }

        CartDto cartAmountInfo = cartService.getCartAmountInfo(cart);
        response.setData(cartAmountInfo);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity addGoodsToCart(@RequestBody CartDto cartDto) {
        APIResponse response = new APIResponse();
        int result = cartService.addGoodsToCart(cartDto);

        if (result <= 0) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity deleteGoodsToCart(@RequestBody CartDto cartDto) {
        APIResponse response = new APIResponse();
        cartService.deleteGoodsToCart(cartDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity updateGoodsToCart(@RequestBody CartGoodsDto cartGoodsDto) {
        APIResponse response = new APIResponse();
        cartService.updateGoodsToCart(cartGoodsDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase-info")
    public ResponseEntity getCheckGoodsPurchaseInfo(@RequestBody PurchaseCartGoddsDto purchaseCartGoddsDto) {
        APIResponse response = new APIResponse();
        List<CartGoodsDto> cartGoodsDtoList = purchaseCartGoddsDto.getCartGoodsList();
        if (cartGoodsDtoList.size() < 1) {
            throw new IllegalArgumentException();
        }

        PurchaseInfoDto purchaseInfo = cartService.getCheckGoodsPurchaseInfo(cartGoodsDtoList);
        response.setData(purchaseInfo);
        return ResponseEntity.ok(response);
    }

}
