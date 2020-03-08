package com.shopping.common.configs.security.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.common.configs.security.model.User;
import com.shopping.common.configs.security.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws AccessDeniedException {
        User user = userRepository.getOne(id);
        return user;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public List<User> findAll() throws AccessDeniedException {
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
