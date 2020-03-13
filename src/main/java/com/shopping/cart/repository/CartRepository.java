package com.shopping.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopping.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = { "goodsList.goods", "goodsList.goods.options", "goodsList.goods.shipping" })
    Optional<Cart> findByUserId(Long userId);

}
