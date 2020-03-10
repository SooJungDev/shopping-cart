package com.shopping.cart.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shopping.common.configs.security.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CartDto {
    private Long id;
    private User user;
    private List<CartGoods> goodsList = new ArrayList<>();
    private int totalGoodsAmount;
    private int totalShippingAmount;
    private int totalPaymentAmount;

    @Builder
    public CartDto(Long id, User user, List<CartGoods> goodsList, int totalGoodsAmount, int totalShippingAmount,
                   int totalPaymentAmount) {
        this.id = id;
        this.user = user;
        this.goodsList = goodsList;
        this.totalGoodsAmount = totalGoodsAmount;
        this.totalShippingAmount = totalShippingAmount;
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public Cart toEntity() {
        return Cart.builder()
                   .id(id)
                   .user(user)
                   .goodsList(goodsList)
                   .build();
    }
}
