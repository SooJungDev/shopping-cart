package com.shopping.shipping.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.shopping.goods.model.Goods;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    @JsonIgnore
    private Long id;

    private String method; // 배송비 부과 여부. 무료배송(`FREE`), 유료배송(`PREPAY`) 존재.

    private int price; // 배송비

    private boolean canBundle;
    // 같은 입점사 상품의 묶음배송 가능 여부, 가능시 묶음배송가능한 상품의 배송비는 한번만 부과. 묶음 배송가능 상품들의 배송 비용이 다를경우 최저금액을 적용. 불가능시 상품마다 배송비 부과

    @JsonIgnore
    @OneToOne
    @JoinColumn(name ="goods_id", nullable = false, updatable = false)
    private Goods goods;
}
