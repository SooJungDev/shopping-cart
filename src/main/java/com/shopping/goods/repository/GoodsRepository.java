package com.shopping.goods.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopping.goods.model.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Override
    @EntityGraph(attributePaths ={"options", "shipping"})
    List<Goods> findAll();
}
