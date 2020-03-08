package com.shopping.cart.model;

import com.shopping.goods.model.Goods;
import com.shopping.goods.model.Option;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartGoodsDto {
    private Goods goods;
    private Option selectOption;
    private int buycount;

    public CartGoods toEntity() {
        return CartGoods.builder()
                        .goods(goods)
                        .selectOption(selectOption)
                        .buyCount(buycount)
                        .build();
    }
}
