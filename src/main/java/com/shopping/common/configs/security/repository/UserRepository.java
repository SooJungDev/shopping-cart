package com.shopping.common.configs.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.common.configs.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
