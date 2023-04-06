/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.security.jwt;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "zakura.app")
@Configuration
public class JwtProperties {

    private String secretKey;

    // validity in milliseconds
    private long validityInMs = 3_600_000; // 1h
}
