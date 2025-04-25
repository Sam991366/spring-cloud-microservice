package com.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@RequestMapping("/fallback/auth")
	public ResponseEntity<String> authFallback() {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Authentication Service is temporarily unavailable. Please try again later.");
	}

	@RequestMapping("/fallback/complain")
	public ResponseEntity<String> complainFallback() {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Complain Service is currently down. Please try again later.");
	}
}
