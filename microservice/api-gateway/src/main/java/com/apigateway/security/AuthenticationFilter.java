package com.apigateway.security;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.commonlib.security.JwtUtil;
import com.commonlib.security.RouterValidator;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Autowired
	private RouterValidator routerValidator;
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (routerValidator.isSecured.test(exchange.getRequest())) {

				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				try {
					jwtUtil.validateToken(authHeader);
				} catch (Exception ex) {
					return onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
				}
			}

			return chain.filter(exchange);
		});
	}

	private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

		String body = "{\"error\": \"" + errorMsg + "\"}";
		DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));

		return response.writeWith(Mono.just(buffer));
	}

	public static class Config {
	}
}