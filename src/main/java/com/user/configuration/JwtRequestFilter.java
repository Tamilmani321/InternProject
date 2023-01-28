package com.user.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.dto.TokenValues;
import com.user.exception.CustomException;
import com.user.service.JwtService;
import com.user.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private JwtService jwtService;
	
	private TokenValues user;

	public TokenValues getToken() {
		return this.user;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	//IT WILL TAKE HEADER FROM THE REQUESTS NAME AUTHOIRZATION 
		
				final String header =request.getHeader("Authorization");
			String jwtToken = null;
			String userName= null;
		if(header!= null && header.startsWith("Bearer ")) {	//header not equal to null and starts with bearer
			 jwtToken =header.substring(7);
			 
			 try {
				userName = jwtUtil.getUserNameFromToken(jwtToken);
				String tokenString = decodeJWT(header);
				Map<Object, String> dec = new ObjectMapper().readValue(tokenString, Map.class);
				request.setAttribute("subject ", dec.get("sub"));
				request.setAttribute("name", dec.get("name"));
				request.setAttribute("id", dec.get("id"));
				user = new TokenValues();
				user.setId((int) request.getAttribute("id"));
				user.setName((String) request.getAttribute("name"));		
			 }catch (BadCredentialsException ec) {
					System.out.println("Bad Credientials");
					request.setAttribute("BadCrediential", ec);
				}
			 catch (SignatureException ec) {
					System.out.println("Signature Invalid");
					request.setAttribute("SignatureException", ec);
				}
			
				catch (ExpiredJwtException ex) {
					System.out.println("Token expired");
					request.setAttribute("Expired", ex);
				 String isRefreshToken = request.getHeader("isRefreshToken");
					String requestURL = request.getRequestURL().toString();
					
					if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
						allowForRefreshToken(ex, request);
					} else
						System.out.println("Expired token but not start with bearer");
			 }catch(JwtException ec) {
				 request.setAttribute("JwtException", ec);
				}
			 
		}
		else {
			System.out.println("Jwt token does not start with bearer");
			
		}
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()== null) {
			UserDetails userDetails = jwtService.loadUserByUsername(userName);
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken
						(userDetails, 
						null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	
	}
	
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

	
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		
		request.setAttribute("claims", ex.getClaims());

	}
	
	public String decodeJWT(String jwt) {
		String[] parts = jwt.split("\\.", 0);
		byte[] bytes = java.util.Base64.getUrlDecoder().decode(parts[1]);
		return new String(bytes, StandardCharsets.UTF_8);

	}

}
