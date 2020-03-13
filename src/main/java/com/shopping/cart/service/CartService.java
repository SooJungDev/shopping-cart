package com.shopping.cart.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.model.CartGoods;
import com.shopping.cart.repository.CartRepository;
import com.shopping.goods.model.Goods;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public Optional<Cart> getCart(Long id) {
        return cartRepository.findByUserId(id);
    }

    public CartDto getCartAmountInfo(Optional<Cart> cart) {
        Cart cartResult = cart.get();
        Set<CartGoods> goodsList =  cartResult.getGoodsList();

        int totalGoodsAmount = 0;
        int totalShippingAmount = 0;

        for (CartGoods goods : goodsList) {
            int buyCount = goods.getBuyCount();
            Goods goodsInfo = goods.getGoods();
            int goodsPrice = goodsInfo.getPrice();
            int shippingPrice = goodsInfo.getShipping().getPrice();
            totalGoodsAmount += buyCount * goodsPrice;
            totalShippingAmount += shippingPrice;

        }

        return CartDto.builder()
                      .id(cartResult.getId())
                      .goodsList(goodsList)
                      .totalGoodsAmount(totalGoodsAmount)
                      .totalShippingAmount(totalShippingAmount)
                      .totalPaymentAmount(totalGoodsAmount + totalShippingAmount)
                      .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateGoodsToCart(CartDto cartDto) {
        AtomicReference<Long> result = new AtomicReference<>(0L);
        Optional<Cart> cart = cartRepository.findById(cartDto.getId());

        if (cart.isPresent()) {
            Cart isPresentCart = cart.get();
            isPresentCart.getGoodsList().clear();
            isPresentCart.getGoodsList().addAll(cartDto.getGoodsList());

            Cart save = cartRepository.save(isPresentCart);
            result.set(save.getId());

        } else {
            Cart firstAddCart = cartRepository.saveAndFlush(cartDto.toEntity());
            result.set(firstAddCart.getId());
        }

        return result.get();
    }

}
