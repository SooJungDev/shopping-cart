package com;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopping.ShoppingCartApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ShoppingCartApplication.class)
@ActiveProfiles("test")
public abstract class SpringTestSupport {
}