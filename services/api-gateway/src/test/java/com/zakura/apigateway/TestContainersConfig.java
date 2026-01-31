/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway;

import java.time.Duration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfig {

    @Bean
    @ServiceConnection
    public MongoDBContainer mongoDBContainer() {
        return new MongoDBContainer(DockerImageName.parse("mongo").withTag("8.2.4"))
                .withSharding()
                .withStartupAttempts(3)
                .withStartupTimeout(Duration.ofMinutes(2))
                .withReuse(true);
    }
}
