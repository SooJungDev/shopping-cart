package com.shopping.cart.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.model.CartGoods;
import com.shopping.cart.model.CartGoodsDto;
import com.shopping.cart.model.PurchaseInfoDto;
import com.shopping.cart.repository.CartGoodRepository;
import com.shopping.cart.repository.CartRepository;
import com.shopping.cart.service.CartService;
import com.shopping.common.configs.security.model.User;
import com.shopping.goods.model.Goods;
import com.shopping.goods.model.Option;
import com.shopping.shipping.model.Shipping;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartGoodRepository cartGoodRepository;

    @InjectMocks
    private CartService cartService;

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

    @Test
    @DisplayName("장바구니에 상품을 추가한다")
    void addGoodsToCart() {
        CartDto cartDto = getCartDto();
        cartService.addGoodsToCart(cartDto);
        verify(cartRepository, times(1)).findByUserId(cartDto.getUser().getId());
        verify(cartRepository, times(0)).save(cartDto.toEntity());
        verify(cartGoodRepository, times(1)).saveAll(cartDto.getGoodsList());

    }

    @Test
    @DisplayName("장바구니에 상품을 삭제한다")
    void deleteGoodsToCart() {
        CartDto cartDto = getCartDto();
        cartService.deleteGoodsToCart(cartDto);
        verify(cartRepository, times(1)).findByUserId(cartDto.getUser().getId());
        verify(cartGoodRepository, times(1)).deleteAll(cartDto.getGoodsList());

    }

    @Test
    @DisplayName("장바구니에 상품을 정보를 업데이트한다")
    void updateGoodsToCart() {
        CartGoodsDto cartGoodsDto = getCartGoodsDto();
        cartGoodRepository.save(cartGoodsDto.toEntity());
    }

    @Test
    @DisplayName("체크한 장바구니의 상품 가격을 계산해준다.")
    void getCheckGoodsPurchaseInfo() {
        CartGoodsDto cartGoodsDto = getCartGoodsDto();

        PurchaseInfoDto checkGoodsPurchaseInfo = cartService.getGoodsPurchaseInfo(List.of(cartGoodsDto));
        int goodsAmount = cartGoodsDto.getGoods().getPrice() * cartGoodsDto.getBuyCount();
        int shippingAmount = cartGoodsDto.getGoods().getShipping().getPrice();
        int total = goodsAmount + shippingAmount;
        assertAll("getCheckGoodsPurchaseInfo",
                  () -> assertEquals(checkGoodsPurchaseInfo.getTotalGoodsAmount(), goodsAmount),
                  () -> assertEquals(checkGoodsPurchaseInfo.getTotalShippingAmount(), shippingAmount),
                  () -> assertEquals(checkGoodsPurchaseInfo.getTotalPaymentAmount(), total)
        );

    }

    private CartDto getCartDto() {
        CartDto cartDto = new CartDto();
        LinkedHashSet<CartGoods> goodsList = new LinkedHashSet<>();
        Goods goods = getGoods();
        Option option = getOption();
        CartGoods cartGoods = CartGoods.builder().buyCount(2).goods(goods).selectOption(option).build();
        goodsList.add(cartGoods);

        User user = new User();
        user.setId(1L);
        cartDto.setId(1L);
        cartDto.setUser(user);
        cartDto.setGoodsList(goodsList);
        return cartDto;
    }

    private Option getOption() {
        return Option.builder().id(1001L).color("yellow").size("S").stock(10).build();
    }

    private Goods getGoods() {
        return Goods.builder().id(1L).name("Python Hood T-Shirts").provider("StyleShare").price(20000)
                    .shipping(Shipping.builder().price(2000).method("PREPAY").build())
                    .build();
    }

    private CartGoodsDto getCartGoodsDto() {
        return CartGoodsDto.builder()
                           .id(1L).cart(getCartDto().toEntity()).goods(getGoods())
                           .selectOption(
                                   getOption())
                           .buyCount(3)
                           .build();
    }

}
