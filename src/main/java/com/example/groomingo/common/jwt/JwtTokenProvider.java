package com.example.groomingo.common.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.groomingo.common.exception.ExceptionState;
import com.example.groomingo.common.exception.detail.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.secret-key}")
	private String SECRET_KEY;
	private static final long JWT_EXPIRE = 3600000;	//1시간

	public String generateToken(Authentication authentication) {
		return Jwts.builder()
			.setSubject(authentication.getName())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE))
			.signWith(getSigningKey(), SignatureAlgorithm.HS512)
			.compact();
	}

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.error("[Expired JWT token] {}", e.getMessage());
			throw new TokenException(ExceptionState.TOKEN_EXPIRED, e.getMessage());
		} catch (JwtException e) {
			log.error("[JwtException] {}", e.getMessage());
			throw new TokenException(ExceptionState.INVALID_TOKEN, e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("[Invalid JWT token] {}", e.getMessage());
			throw new TokenException(ExceptionState.ILLEGAL_ARGUMENT_TOKEN, e.getMessage());
		}
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		List<String> roles = (List<String>) Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token).getBody().get("roles");
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

}
