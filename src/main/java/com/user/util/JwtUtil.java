package com.user.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

//	private static final String SECRET_KEY="TamilMani";
	private int jwtExpirationInMs;
	private int refreshExpirationDateInMs;
	private String secret;
	
	//private static final int TOKEN_VALIDITY= 3600 * 5;
	//5 hours
	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Value("${jwt.expirationDateInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	@Value("${jwt.refreshExpirationDateInMs}")
	public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
		this.refreshExpirationDateInMs = refreshExpirationDateInMs;
	}
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}
	
	
	  public String getUserNameFromToken(String token) { return
	  getClaimFromToken(token, Claims::getSubject); }
	 
	
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {//HIGHER ORDER FUNCTION THIS IS FUNCTIONAL PROGARMMING WHAT IS FUNCTIONAL PROGARAMMING
		final Claims claims=getAllClaimsfromToken(token);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsfromToken(String token) {
		return Jwts.parser().setSigningKey(secret)
				.parseClaimsJws(token).getBody();
	}
	
	
	public boolean validateToken (String token, UserDetails userDetails) {//IT RETURNS BOOLEAN IF THE TOKEN IS VALIDATE OR NOT 
		String userName=getUserNameFromToken(token);
		return ( userName.equals(userDetails.getUsername()) && !isTokenExpired(token) );//token is not expired it gives true
	}
	
	private boolean isTokenExpired (String token) {
		final Date expirationDate=getExpirationDatefromToken(token);
		return expirationDate.before(new Date());//CHECK TOKEN IS EXPIRED OR NOT
	}
	private Date getExpirationDatefromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration); //IT IS ALSO HIGHER ORDER FUNCTION
	}
	
	public String generateToken(User userDetails) {
		Map<String, Object> claims= new HashMap<>();
		claims.put("name", userDetails.getName());
		claims.put("id", userDetails.getId());
		claims.put("email", userDetails.getEmail());
		claims.put("roles", userDetails.getRoles());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getName())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	
	
}
