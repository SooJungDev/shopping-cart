package com.shopping.cart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PurchaseInfoDto {
    private int totalGoodsAmount = 0;
    private int totalShippingAmount = 0;
    private int totalPaymentAmount = 0;

    @Builder
    public PurchaseInfoDto(int totalGoodsAmount, int totalShippingAmount) {
        this.totalGoodsAmount = totalGoodsAmount;
        this.totalShippingAmount = totalShippingAmount;
        this.totalPaymentAmount = totalShippingAmount + totalGoodsAmount;
    }
}
