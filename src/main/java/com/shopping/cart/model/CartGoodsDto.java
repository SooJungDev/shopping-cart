package com.shopping.cart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shopping.goods.model.Goods;
import com.shopping.goods.model.Option;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CartGoodsDto {
    private Long id;
    private Cart cart;
    @NonNull
    private Goods goods;
    @NonNull
    private Option selectOption;
    @NonNull
    private int buyCount;

    @Builder
    public CartGoodsDto(Long id, Cart cart, @NonNull Goods goods,
                        @NonNull Option selectOption, @NonNull int buyCount) {
        this.id = id;
        this.cart = cart;
        this.goods = goods;
        this.selectOption = selectOption;
        this.buyCount = buyCount;
    }

    public CartGoods toEntity() {
        return CartGoods.builder()
                        .id(id)
                        .cart(cart)
                        .goods(goods)
                        .selectOption(selectOption)
                        .buyCount(buyCount)
                        .build();
    }
}
