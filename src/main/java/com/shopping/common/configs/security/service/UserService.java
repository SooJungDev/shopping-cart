package com.shopping.common.configs.security.service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import com.shopping.common.configs.security.model.User;

public interface UserService {
    void save(User user);

    User findById(Long id) throws AccessDeniedException;

    User findByUsername(String username);

    List<User> findAll();

}
