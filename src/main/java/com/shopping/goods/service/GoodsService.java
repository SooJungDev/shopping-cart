package com.shopping.goods.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.goods.model.Goods;
import com.shopping.goods.repository.GoodsRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public List<Goods> getGoodsList() {
        return goodsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Goods> getGoodsDetail(Long goods_id) {
        return goodsRepository.findById(goods_id);
    }
}
