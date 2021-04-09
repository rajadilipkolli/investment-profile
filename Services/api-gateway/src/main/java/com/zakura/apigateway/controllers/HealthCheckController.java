package com.zakura.apigateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health-check")
public class HealthCheckController {

	@GetMapping("/status")
	public String healthCheck() {
		return "API Service is UP!";
	}
}
