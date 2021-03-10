package com.example.apigateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests extends AbstractMongoDBTestContainer {

    @Test
    void contextLoads() {
        assertThat(MONGO_DB_CONTAINER.isRunning()).isTrue();
    }
}
