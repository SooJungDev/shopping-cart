package com.shopping.goods.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.shopping.shipping.model.Shipping;

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

public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id; //상품 id

    private String name; //상품 이름
    private String provider;// 입점사 이름

    private int price;

    @OneToMany(mappedBy = "goods",cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Option> options; // 상품옵션

    @OneToOne(mappedBy = "goods", cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.EAGER)
    private Shipping shipping;

}
