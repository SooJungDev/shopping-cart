package com.shopping.goods.repository;

import com.shopping.goods.model.Goods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Override
    @EntityGraph(attributePaths ={"options", "shipping"})
    List<Goods> findAll();
}
