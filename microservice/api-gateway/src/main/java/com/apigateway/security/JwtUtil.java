package com.apigateway.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public Claims getClaims(final String token) {
		try {
			return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
		} catch (Exception e) {
			System.out.println("Invalid JWT token: " + e.getMessage());
			return null;
		}
	}

	public void validateToken(final String token) throws Exception {
		Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}