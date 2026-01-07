package br.com.medmentor.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger; // Usar Java Logger

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JwtUtil {

	private static final Logger LOGGER = Logger.getLogger(JwtUtil.class.getName());
	private static final String SECRET = "c2luZ2hlbHBjZW50cmFsY29tbW9uZXhjaXRlbWVudGRlYXRoc3ViamVjdGFybXlicnU=";
	private static final long EXPIRATION_TIME = 3600000;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			LOGGER.warning("Invalid JWT signature: " + e.getMessage());
			throw new RuntimeException("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			LOGGER.warning("Invalid JWT token: " + e.getMessage());
			throw new RuntimeException("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			LOGGER.warning("JWT token is expired: " + e.getMessage());
			throw new RuntimeException("JWT token is expired", e);
		} catch (UnsupportedJwtException e) {
			LOGGER.warning("JWT token is unsupported: " + e.getMessage());
			throw new RuntimeException("JWT token is unsupported", e);
		} catch (IllegalArgumentException e) {
			LOGGER.warning("JWT claims string is empty: " + e.getMessage());
			throw new RuntimeException("JWT claims string is empty", e);
		}
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username, Map<String, Object> extraClaims) {
		return createToken(extraClaims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public Boolean validateToken(String token, String username) { 
		final String tokenUsername = extractUsername(token);
		return (tokenUsername.equals(username) && !isTokenExpired(token));
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}