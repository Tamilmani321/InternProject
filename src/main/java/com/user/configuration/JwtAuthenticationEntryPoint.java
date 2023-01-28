package com.user.configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.exception.CustomException;
import com.user.response.UnauthorizedErrorResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		Exception expiredException =(Exception) request.getAttribute("Expired");
		Exception badCredientialException =(Exception) request.getAttribute("BadCrediential");
		Exception signatureException =(Exception) request.getAttribute("SignatureException");
		Exception jwtException =(Exception) request.getAttribute("JwtException");
		
		UnauthorizedErrorResponse error=null;
		
		if(expiredException!=null) {
			error=new UnauthorizedErrorResponse();
			error.setHttpStatus(HttpStatus.FORBIDDEN);
			error.setMessage("Session Timeout");
			error.setStatusCode(HttpStatus.FORBIDDEN.value());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			
			
		}
		else if(badCredientialException!=null){
			error=new UnauthorizedErrorResponse();
			error.setHttpStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("You are Not able to access this resource");
			error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		else if(signatureException!=null){
			error=new UnauthorizedErrorResponse();
			error.setHttpStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("Bad Credentials");
			error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		else if(jwtException!=null) {
			error = new UnauthorizedErrorResponse();
			error.setHttpStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("Access Denied");
			error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		else {
			error = new UnauthorizedErrorResponse();
			error.setHttpStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("Access Denied");
			error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		PrintWriter out = response.getWriter();
		ObjectMapper objectMapper= new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(error);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonString);
		out.flush();
	
	
	}



}
