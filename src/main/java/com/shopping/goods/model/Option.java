package com.shopping.goods.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Option {

    @Id
    private Long id; // 옵션 ID

    private String color;// 옵션 제목: 옵션명

    private String size;// 옵션 제목: 옵션명

    private int stock; // 재고

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="goods_id", nullable = false, updatable = false)
    private Goods goods;


}
