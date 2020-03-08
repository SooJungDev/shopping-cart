package com.shopping.cart.model;

import java.util.ArrayList;
import java.util.List;

import com.shopping.common.configs.security.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    private Long id;
    private User user;
    private List<CartGoods> goods_list = new ArrayList<>();

    public Cart toEntity() {
        return Cart.builder()
                   .id(id)
                   .user(user)
                   .goods_list(goods_list)
                   .build();
    }
}
