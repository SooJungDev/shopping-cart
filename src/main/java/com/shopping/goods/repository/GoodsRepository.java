package com.shopping.goods.repository;

import com.shopping.goods.model.Goods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Override
    @EntityGraph(attributePaths ={"options", "shipping"})
    List<Goods> findAll();

    @Override
    @EntityGraph(attributePaths ={"options", "shipping"})
    Optional<Goods> findById(Long aLong);
}
