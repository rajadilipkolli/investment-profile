/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping("/status")
    public Mono<String> healthCheck() {
        return Mono.just("API Service is UP!");
    }
}
