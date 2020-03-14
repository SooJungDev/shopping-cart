package com.shopping.cart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shopping.goods.model.Goods;
import com.shopping.goods.model.Option;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CartGoodsDto {
    private Long id;
    private Cart cart;
    private Goods goods;
    private Option selectOption;
    private int buycount;

    public CartGoods toEntity() {
        return CartGoods.builder()
                        .id(id)
                        .cart(cart)
                        .goods(goods)
                        .selectOption(selectOption)
                        .buyCount(buycount)
                        .build();
    }
}
