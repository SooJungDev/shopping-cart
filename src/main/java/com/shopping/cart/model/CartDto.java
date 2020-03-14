package com.shopping.cart.model;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<CartGoods> goodsList = new LinkedHashSet<>();
    private PurchaseInfoDto purchaseInfo;

    @Builder
    public CartDto(Long id, User user, Set<CartGoods> goodsList, PurchaseInfoDto purchaseInfo) {
        this.id = id;
        this.user = user;
        this.goodsList = goodsList;
        this.purchaseInfo = purchaseInfo;
    }

    public Cart toEntity() {
        return Cart.builder()
                   .id(id)
                   .user(user)
                   .goodsList(goodsList)
                   .build();
    }
}
