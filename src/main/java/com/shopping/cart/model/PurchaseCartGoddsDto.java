package com.shopping.cart.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseCartGoddsDto {
    List<CartGoodsDto> cartGoodsList;
}
