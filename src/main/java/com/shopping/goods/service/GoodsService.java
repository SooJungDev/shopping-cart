package com.shopping.goods.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.goods.model.Goods;
import com.shopping.goods.repository.GoodsRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;

    public List<Goods> getGoodsList() {
        return goodsRepository.findAll();
    }
}
