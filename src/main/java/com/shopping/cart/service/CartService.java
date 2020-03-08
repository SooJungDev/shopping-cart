package com.shopping.cart.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.repository.CartRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;

    public Optional<Cart> getCart(Long id) {
        return cartRepository.findByUserId(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public Long updateGoodsToCart(CartDto cartDto) {
        AtomicReference<Long> result = new AtomicReference<>(0L);
        Optional<Cart> cart = cartRepository.findById(cartDto.getId());

        if (cart.isPresent()) {
            Cart isPresentCart = cart.get();
            isPresentCart.getGoods_list().clear();
            isPresentCart.getGoods_list().addAll(cartDto.getGoods_list());

            Cart save = cartRepository.save(isPresentCart);
            result.set(save.getId());

        } else {
            Cart firstAddCart = cartRepository.saveAndFlush(cartDto.toEntity());
            result.set(firstAddCart.getId());
        }

        return result.get();
    }

}
