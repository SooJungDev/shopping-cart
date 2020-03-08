package com.shopping.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.cart.model.CartGoods;

public interface CartGoodRepository extends JpaRepository<CartGoods, Long> {

}
