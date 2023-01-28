package com.user.service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.user.configuration.JwtRequestFilter;
import com.user.dto.UserDto;
import com.user.entity.Roles;
import com.user.entity.User;
import com.user.exception.CustomException;
import com.user.repository.RoleRepository;
import com.user.repository.UserRepository;
import com.user.response.JwtResponse;
import com.user.response.LoginRequest;
import com.user.response.LoginResponse;
import com.user.response.MessageResponse;
import com.user.util.ImageUtil;
import com.user.util.JwtUtil;
@Service
public class JwtService implements UserDetailsService {
	

	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private ConverterService converterService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	
	
	
	public ResponseEntity<?> login(LoginRequest signIn) {
		String password =Base64.getEncoder().encodeToString(signIn.getPassword().getBytes());
		String name=signIn.getName();
			User user =userRepository.findByNameAndPassword(name,password);
			if(user!=null) {
				UserDto userDto= new UserDto();
				userDto=converterService.convertToUserDto(user);
				LoginResponse response = new LoginResponse();
				response.setData(userDto);
				response.setHttpStatus(HttpStatus.OK);
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus(true);
				response.setJwtToken(getToken(user));
				response.setMessage("Success");
				return ResponseEntity.status(HttpStatus.OK).body(response);
				}
			throw new CustomException("Invalid username or password");
		}
	
	
		
	private String getToken(User signUp) {
		return jwtUtil.generateToken(signUp);
	}
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	public ResponseEntity<?> save(String name, String email, MultipartFile file, List<Long> roles)
			throws IOException {
		List<Roles> role = roleRepository.findAllById(roles);
				User user = new User();
				MessageResponse res= new MessageResponse();
				String passwords=name+"_2023";
				String Encrypassword =Base64.getEncoder().encodeToString(passwords.getBytes());
				user.setAttachments(imageUtil.imageCompressor(file));
				user.setName(name);
				user.setEmail(email);
				user.setPassword(Encrypassword);
				user.setRoles(role);
				
				
				try {
					user=userRepository.save(user);
					user.setCreatedBy((int) user.getId());
					user.setLastModifiedBy((int) user.getId());
					res.setHttpStatus(HttpStatus.CREATED);
					res.setMessage("User created successfully");
					res.setStatus(true);
					res.setStatusCode(HttpStatus.CREATED.value());
					user=userRepository.save(user);
					if(user!=null) {
						return ResponseEntity.status(HttpStatus.CREATED).body(res);
					}
					else {
						throw new CustomException("An error occured try again later");
					}
				}catch (DataIntegrityViolationException e) {
					
					throw new CustomException("Username And Email Already Exist");
				}
				
				
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user= userRepository.findByname(username);
		if(user != null) {
			return new org.springframework.security.core.userdetails.User
					(user.getName(), 
						user.getPassword(), getAuthorities(user)
						);
		}
		else {
			throw new UsernameNotFoundException("Username is not valid");
		}
		
	}
	
	
	
	
	private Set getAuthorities(User user) {
		Set authorities = new HashSet();
		return authorities;

	}
	 
	private void authenticate(String userName, String UserPassword) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, UserPassword));
		}
		catch (DisabledException e) {
			throw new Exception ("User is disabled");
		}
		catch(BadCredentialsException e) {
			throw new Exception ("Bad Credentials from user");
		}
		
	}

}
