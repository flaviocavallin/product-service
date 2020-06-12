package com.example.productservice;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
@ActiveProfiles("test")
@SpringBootTest(classes = ProductServiceApplication.class)
public abstract class IntegrationBaseTest {
    //do nothing
}
