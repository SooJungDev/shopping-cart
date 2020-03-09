package com.shopping.goods.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{goods_id}")
    public ResponseEntity getGoodsDetail(@PathVariable Long goods_id) {
        APIResponse response = new APIResponse();
        Optional<Goods> goodsDetail = goodsService.getGoodsDetail(goods_id);

        if (goodsDetail.isEmpty()) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        } else {
            response.setData(goodsDetail.get());
        }

        return ResponseEntity.ok(response);
    }
}
