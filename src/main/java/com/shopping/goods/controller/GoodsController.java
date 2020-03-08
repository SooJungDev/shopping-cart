package com.shopping.goods.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.APIResponse;
import com.shopping.goods.service.GoodsService;
import com.shopping.goods.model.Goods;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping
    public ResponseEntity getGoodsList() {
        APIResponse response = new APIResponse();
        List<Goods> goodsList = goodsService.getGoodsList();
        response.setList(goodsList);
        return ResponseEntity.ok(response);
    }
}
