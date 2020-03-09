package com.shopping.goods.model;

import com.shopping.shipping.model.Shipping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GoodsDto {
    private Long id; //상품 id

    private String name; //상품 이름
    private String provider;// 입점사 이름

    private int price;

    private List<Option> options; // 상품옵션
    private Shipping shipping; // 배송 정보

    public Goods toEntity() {
        return Goods.builder()
                .id(id)
                .name(name)
                .price(price)
                .options(options)
                .shipping(shipping)
                .build();
    }
}
