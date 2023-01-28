package com.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.user.response.JwtResponse;
import com.user.response.LoginRequest;
import com.user.response.LoginResponse;
import com.user.response.RefreshTokenResponse;
import com.user.service.JwtService;
import com.user.util.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/createUser")
	public ResponseEntity<?> save(@RequestParam @Valid String name, @RequestParam String email,
			@RequestParam("image") MultipartFile file, @RequestParam List<Long> roles) throws IOException {
		return jwtService.save(name, email, file, roles);

	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		RefreshTokenResponse response= new RefreshTokenResponse(HttpStatus.OK.value(), HttpStatus.OK,
				true, "Success", token);
		return ResponseEntity.ok((response));
	}

	
	  public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims
	  claims) { Map<String, Object> expectedMap = new HashMap<String, Object>();
	  for (Entry<String, Object> entry : claims.entrySet()) {
	  expectedMap.put(entry.getKey(), entry.getValue()); } return expectedMap; }
	  
	 

	@PostMapping("/signIn")
	public ResponseEntity<?> createJwtToken(@RequestBody @Valid LoginRequest login) throws Exception {

		return jwtService.login(login);

	}

}
