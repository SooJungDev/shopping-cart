package com.shopping.cart.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.model.CartGoods;
import com.shopping.cart.repository.CartRepository;
import com.shopping.cart.service.CartService;
import com.shopping.common.configs.security.model.User;
import com.shopping.goods.model.Goods;
import com.shopping.goods.model.Option;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 담긴 목록을 업데이트한다.")
    void updateGoodsToCart() throws Exception {
     /*   CartDto cartDto = getCartDto();
        when(cartService.updateGoodsToCart(any(CartDto.class))).thenReturn(1L);
        cartService.updateGoodsToCart(cartDto);

        verify(cartRepository, times(1)).findById(cartDto.getId());
        verify(cartRepository, times(1)).save(cartDto.toEntity());*/

    }

    @Test
    @DisplayName("장바구니 목록을 가져온다.")
    void getCart() {
        CartDto cartDto = getCartDto();
        when(cartService.getCart(any())).thenReturn(Optional.of(cartDto.toEntity()));

        Optional<Cart> cart = cartService.getCart(1L);
        Cart cartResult = cart.get();

        assertAll("getCart",
                  () -> assertEquals(cartDto.getId(), cartResult.getId()),
                  () -> assertEquals(cartDto.getGoodsList().size(), cartResult.getGoodsList().size()),
                  () -> assertEquals(cartDto.getGoodsList(), cartResult.getGoodsList()),
                  () -> assertEquals(cartDto.getGoodsList().iterator().next().getSelectOption(),
                                     cartDto.getGoodsList().iterator().next().getSelectOption()));

    }

    private CartDto getCartDto() {
        CartDto cartDto = new CartDto();
        LinkedHashSet<CartGoods> goodsList = new LinkedHashSet<>();
        Goods goods = Goods.builder().id(1L).name("Python Hood T-Shirts").provider("StyleShare").price(20000)
                           .build();
        Option option = Option.builder().id(1001L).color("yellow").size("S").stock(10).build();
        CartGoods cartGoods = CartGoods.builder().buyCount(2).goods(goods).selectOption(option).build();
        goodsList.add(cartGoods);

        User user = new User();
        user.setId(1L);
        cartDto.setId(1L);
        cartDto.setUser(user);
        cartDto.setGoodsList(goodsList);
        return cartDto;
    }

}