package com.shopping.common.configs.security.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.SpringMockMvcTestSupport;
import com.TimeProvider;
import com.shopping.common.configs.security.auth.TokenHelper;
import com.shopping.common.configs.security.model.Authority;
import com.shopping.common.configs.security.model.User;
import com.shopping.common.configs.security.model.UserRoleName;
import com.shopping.common.configs.security.service.CustomUserDetailsService;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest extends SpringMockMvcTestSupport {

    private static final String TEST_USERNAME = "testUser";

    @MockBean
    private TimeProvider timeProviderMock;

    @Autowired
    private TokenHelper tokenHelper;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = new User();
        user.setUsername("username");
        Authority authority = new Authority();
        authority.setId(0L);
        authority.setName(UserRoleName.ROLE_USER);
        Set<Authority> authorities = new LinkedHashSet<>();
        user.setAuthorities(authorities);
        user.setLastPasswordResetDate(new Timestamp(DateUtil.yesterday().getTime()));
        when(userDetailsService.loadUserByUsername(eq("testUser"))).thenReturn(user);
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 100);
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "queenvictoria");
    }

    @Test
    void shouldGetEmptyTokenStateWhenGivenValidOldToken() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday());
        mockMvc.perform(post("/auth/refresh")
                                .header("Authorization", "Bearer 123"))
               .andExpect(content().json("{access_token:null,expires_in:0}"));
    }

    @Disabled
    @Test
    @WithMockUser(roles = "USER")
    void shouldRefreshNotExpiredWebToken() throws Exception {

        given(timeProviderMock.now())
                .willReturn(new Date(30L));

        String token = createToken();
        String refreshedToken = tokenHelper.refreshToken(token);

        System.out.println(refreshedToken);

        mockMvc.perform(post("/auth/refresh")
                                .header("Authorization", "Bearer " + token))
               .andDo(print())
               .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:100}"));
    }

    @Disabled
    @Test
    @WithMockUser(roles = "USER")
    void shouldRefreshNotExpiredMobileToken() throws Exception {
        given(timeProviderMock.now())
                .willReturn(new Date(30L));

        String token = createToken();
        String refreshedToken = tokenHelper.refreshToken(token);

        mockMvc.perform(post("/auth/refresh")
                                .header("Authorization", "Bearer " + token))
               .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:200}"));
    }

    @Test
    void shouldNotRefreshExpiredToken() throws Exception {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
        when(timeProviderMock.now())
                .thenReturn(beforeSomeTime);

        String token = createToken();
        mockMvc.perform(post("/auth/refresh")
                                .header("Authorization", "Bearer " + token))
               .andExpect(content().json("{access_token:null,expires_in:0}"));
    }


    private String createToken() {
        return tokenHelper.generateToken(TEST_USERNAME);
    }
}