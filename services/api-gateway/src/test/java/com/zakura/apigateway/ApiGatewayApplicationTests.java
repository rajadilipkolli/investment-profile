/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.cloud.service-registry.auto-registration.enabled=false"})
class ApiGatewayApplicationTests extends AbstractMongoDBTestContainer {

    @Test
    void contextLoads() {
        assertThat(MONGO_DB_CONTAINER.isRunning()).isTrue();
    }
}
