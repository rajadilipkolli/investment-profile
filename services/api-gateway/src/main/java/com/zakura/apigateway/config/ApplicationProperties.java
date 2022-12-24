/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application")
public class ApplicationProperties {
    private Cors cors = new Cors();

    @Data
    public static class Cors {
        private String pathPattern = "/**";
        private String allowedMethods = "*";
        private String allowedHeaders = "*";
        private String allowedOriginPatterns = "*";
        private boolean allowCredentials = true;
    }
}
