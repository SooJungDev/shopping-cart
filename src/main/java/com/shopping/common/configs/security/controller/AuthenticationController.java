package com.shopping.common.configs.security.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.APIResponse;
import com.shopping.common.configs.security.auth.JwtAuthenticationRequest;
import com.shopping.common.configs.security.auth.TokenHelper;
import com.shopping.common.configs.security.model.User;
import com.shopping.common.configs.security.model.UserTokenState;
import com.shopping.common.configs.security.service.CustomUserDetailsService;
import com.shopping.common.configs.security.service.UserServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthenticationController {

    TokenHelper tokenHelper;
    @Lazy
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String jws = tokenHelper.generateToken(username);
        int expiresIn = tokenHelper.getExpiredIn();

        // Return the token
        return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
    ) {

        String authToken = tokenHelper.getToken(request);

        if (authToken != null && principal != null) {
            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);
            int expiresIn = tokenHelper.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody User user) {
        APIResponse response = new APIResponse();
        userService.save(user);
        return ResponseEntity.ok(response);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}
