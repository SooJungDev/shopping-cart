package com.shopping.goods.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopping.goods.model.Goods;
import com.shopping.goods.repository.GoodsRepository;
import com.shopping.goods.service.GoodsService;
import com.shopping.shipping.model.Shipping;

@ExtendWith(MockitoExtension.class)
class GoodsControllerTest {

    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private GoodsService goodsService;

    @Test
    @DisplayName("전체 상품 목록을 조회한다")
    void getGoodsList() throws Exception {

        List<Goods> goodsList = new ArrayList<>();
        Goods goods1 = Goods.builder().id(1L).name("Python Hood T-Shirts").provider("StyleShare").price(20000)
                            .build();
        Goods goods2 = Goods.builder().id(1L).name("JAVA Round T-Shirts").provider("StyleShare").price(15000)
                            .build();
        Goods goods3 = Goods.builder().id(1L).name("PHP V Neck T-Shirts").provider("StyleShare").price(15000)
                            .build();
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);

        when(goodsService.getGoodsList()).thenReturn(goodsList);

        List<Goods> goodsListResult = goodsService.getGoodsList();

        assertAll("getGoodsList",
                  () -> assertEquals(goodsList.size(), goodsListResult.size()),
                  () -> assertEquals(goodsList.get(0), goodsListResult.get(0)),
                  () -> assertEquals(goodsList.get(1), goodsListResult.get(1)),
                  () -> assertEquals(goodsList.get(2), goodsListResult.get(2)));

    }

    @Test
    @DisplayName("특정 상품을 조회한다")
    void getGoodsDetail() throws Exception {
        Goods goods = Goods.builder()
                           .id(1L)
                           .name("Python Hood T-Shirts")
                           .provider("StyleShare")
                           .price(20000)
                           .shipping(Shipping.builder().method("FREE").price(0).canBundle(true).build())
                           .build();

        when(goodsService.getGoodsDetail(any())).thenReturn(Optional.of(goods));

        Optional<Goods> goodsDetail = goodsService.getGoodsDetail(1L);

        Goods getGoodsDetailResult = goodsDetail.get();

        assertAll("getGoodsDetail",
                  () -> assertEquals("Python Hood T-Shirts", getGoodsDetailResult.getName()),
                  () -> assertEquals("StyleShare", getGoodsDetailResult.getProvider()),
                  () -> assertEquals(20000, getGoodsDetailResult.getPrice()));
    }
}