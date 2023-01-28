package com.user.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.common.PaginationMeta;
import com.user.common.UserDatas;
import com.user.configuration.JwtRequestFilter;
import com.user.controller.RoleController;
import com.user.controller.UserController;
import com.user.dao.UserDao;
import com.user.dto.RoleDto;
import com.user.dto.UserDto;
import com.user.entity.Roles;
import com.user.entity.User;
import com.user.exception.CustomException;
import com.user.exception.RoleNotFoundException;
import com.user.exception.UserNotFoundException;
import com.user.repository.RoleRepository;
import com.user.repository.UserRepository;
import com.user.response.DataResponse;
import com.user.response.MessageResponse;
import com.user.util.ImageUtil;

@Service
public class UserService implements UserDao {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConverterService converterService;

	@Autowired
	private JwtRequestFilter filter;
	@Autowired
	private PaginationMeta paginationMeta;


	public ResponseEntity<?> getUser(Long us) {
		Optional<User> user = userRepository.findById(us);
		if (user.isPresent()) {
			User use = user.get();
			if (use.isDeleteStatus() == false) {
				UserDto userDto = new UserDto();
				userDto=converterService.convertToUserDto(use);
				DataResponse response = new DataResponse(HttpStatus.OK.value(),HttpStatus.OK,
						true,"Success",userDto);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("User id deleted try another user id");
			}

		} else {
			throw new CustomException("User id does not exist try another user id");
		}

	}
	public ResponseEntity<?> getUserByPage(Pageable pageable) {
		Page<User> user = userRepository.findAll(pageable);
		DataResponse response = new DataResponse();
		List <User> userContent=user.getContent();
		List<UserDto> userDto =userContent
				.stream()
				.map(converterService::convertToUserDto)
				.collect(Collectors.toList());;
		PaginationMeta pagination = paginationMeta.createPagination(user);
		UserDatas userDatas = new UserDatas();
		userDatas.setUser(userDto);
		userDatas.setPagination(pagination);
		response.setMessage("Success");
		response.setStatus(true);
		response.setHttpStatus(HttpStatus.OK);		
		response.setData(userDatas);
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public ResponseEntity<?> updateUser(UserDto use, Long us) {
		Optional<User> user = userRepository.findById(us);
		
		if (user.isPresent()) {
			User user1 = user.get();
			if (user1.isDeleteStatus() == false) {
					List<Roles> role = roleRepository.findAllById(use.getRoleId());
					user1.setEmail(use.getEmail());
					user1.setName(use.getName());
					user1.setRoles(role);
					user1.setCreatedBy((int) user1.getId());
					user1.setLastModifiedBy(filter.getToken().getId());
					use=converterService.convertToUserDto(user1);
					MessageResponse respone;
					if(use!=null) {
						try {
							user1=userRepository.save(user1);
							use=converterService.convertToUserDto(user1);
							respone= new MessageResponse(HttpStatus.OK.value(),
									HttpStatus.OK, true, "User updated Successfully");
						}catch (DataIntegrityViolationException e) {
							throw new CustomException("Username Already Exist");
						}
					}
					else {
						throw new CustomException("An error occured try again later");
					}
					
					return ResponseEntity.status(HttpStatus.OK).body(respone);
				} else {
					throw new CustomException("User name is already exist try  another user name");
				}

			} else {
				throw new CustomException("User id deleted try another user id");
			}

		} 

	public ResponseEntity<?> deleteUser(Long us) {
		Optional<User> user = userRepository.findById(us);
		if (user.isPresent()) {
			User use = user.get();
			if (use.isDeleteStatus() == false) {
				use.setDeleteStatus(true);
				use.setCreatedBy((int) use.getId());
				use.setLastModifiedBy(filter.getToken().getId());
				use=userRepository.save(use);
				MessageResponse respone;
				if(use!=null) {
					respone= new MessageResponse(HttpStatus.OK.value(),
							HttpStatus.OK, true, "User deleted successfully");
				}
				else {
					throw new CustomException("An error occured try again later");
				}
				return ResponseEntity.status(HttpStatus.OK).body(respone);
			} else {
				throw new CustomException("User id already deleted try another user id");
			}

		} else {
			throw new CustomException("User id does not exist try another user id");
		}

	}
	public ResponseEntity<?> links(Long id) {
		UserDto userDto = new UserDto();
		Optional<User> userRepo = userRepository.findById(id);		
		User user = userRepo.get();
		Pageable pageable =null;
		userDto=converterService.convertToUserDto(user);
		DataResponse response= new DataResponse();
		userDto.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
		userDto.add(linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"));
		userDto.add(linkTo(methodOn(UserController.class).updateUser(id,userDto)).withRel("updateUser"));
		userDto.add(linkTo(methodOn(UserController.class).getUserByPage(pageable)).withRel("getUserByPage"));
		response.setData(userDto);
		response.setMessage("Success");
		response.setStatus(true);
		response.setStatusCode(HttpStatus.OK.value());
		response.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	

	

}