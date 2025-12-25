/* Licensed under Apache-2.0 2025 */
package com.zakura.portfolioservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health-check")
public class HealthCheckController {

    @GetMapping("/status")
    public String healthCheck() {
        return "Portfolio Service is UP!";
    }
}
