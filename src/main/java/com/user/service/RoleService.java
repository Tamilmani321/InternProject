package com.user.service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.user.common.PaginationMeta;
import com.user.common.RoleDatas;
import com.user.configuration.JwtRequestFilter;
import com.user.controller.RoleController;
import com.user.dao.RoleDao;
import com.user.dto.RoleDto;
import com.user.entity.Roles;
import com.user.exception.CustomException;
import com.user.repository.RoleRepository;
import com.user.response.DataResponse;
import com.user.response.MessageResponse;

@Service
public class RoleService implements RoleDao{

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ConverterService converterService;
	
	@Autowired
	private JwtRequestFilter jWtRequestfilter;
	
	@Autowired
	private PaginationMeta paginationMeta;

	public ResponseEntity<?> saveRole(RoleDto roleDto) {
			Roles role1 = new Roles();
			role1=converterService.convertToRole(roleDto);
			role1.setCreatedBy(jWtRequestfilter.getToken().getId());
			role1.setLastModifiedBy(jWtRequestfilter.getToken().getId());
			MessageResponse response;
			try {
				role1=roleRepository.save(role1);
				if(role1!=null) {
					response=new MessageResponse(HttpStatus.OK.value(),HttpStatus.OK,true,"Role Created Successfully");
				}
				else {
					throw new CustomException("Try again Later");
				}
				
			}catch (DataIntegrityViolationException e) {
				throw new CustomException("Rolename Already Exist");
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		

		
	}
	public ResponseEntity<?> getRoleByPage(Pageable pageable) {
		Page<Roles> role = roleRepository.findAll(pageable);
		DataResponse response = new DataResponse();
		List <Roles> roleContent=role.getContent();
		List<RoleDto> roleDto =roleContent
				.stream()
				.map(converterService::convertToRoleDto)
				.collect(Collectors.toList());;
		PaginationMeta pagination = paginationMeta.createPagination(role);
		RoleDatas userDatas = new RoleDatas();
		userDatas.setRoles(roleDto);
		userDatas.setPagination(pagination);
		response.setMessage("Success");
		response.setStatus(true);
		response.setHttpStatus(HttpStatus.OK);		
		response.setData(userDatas);
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public ResponseEntity<?> getRole(Long a) {
		Optional<Roles> rol = roleRepository.findById(a);
		DataResponse response= new DataResponse();
		if (rol.isPresent()) {
			Roles role = rol.get();
			if (role.isDeleteStatus() == false) {
				RoleDto roleDto = new RoleDto();
				roleDto=converterService.convertToRoleDto(role);
				response.setData(roleDto);
				response.setMessage("Success");
				response.setStatus(true);
				response.setStatusCode(HttpStatus.OK.value());
				response.setHttpStatus(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("Role id was deleted try another role id");
			}
		} else {
			throw new CustomException("Role id does not exist try another role id");
		}

	}

	public ResponseEntity<?> updateRole(RoleDto roleDto, Long us) {
		Optional<Roles> role = roleRepository.findById(us);
		if (role.isPresent()) {
			Roles roles = role.get();
			if (roles.isDeleteStatus() == false) {
				roles.setName(roleDto.getName());
				roles.setCreatedBy(roles.getCreatedBy());
				roles.setLastModifiedBy(jWtRequestfilter.getToken().getId());
				MessageResponse response;
				try {
					roles=roleRepository.save(roles);
					if(roles!=null) {
						response = new MessageResponse(HttpStatus.OK.value(), HttpStatus.OK, true,
								"Role Updated Successfully");
					}
					else {
						throw new CustomException("Try again Later");
					}
					return ResponseEntity.status(HttpStatus.OK).body(response);
				} catch (DataIntegrityViolationException e) {
					throw new CustomException("RoleName Already Exist Try Another RoleName");
				}

			}

			else {
				throw new CustomException("Role id was deleted try another role id");
			}

		} else {
			throw new CustomException("Role id does not exist try another role id");
		}

	}
	@PostConstruct
	public void init() {
		Roles role= new Roles();	
		role.setCreatedBy(1);
		role.setLastModifiedBy(1);
		role.setName("Admin");
		roleRepository.save(role);
	}

	public ResponseEntity<?> deleteRole(Long us) {
		
		Optional<Roles> rol = roleRepository.findById(us);
		if (rol.isPresent()) {
			Roles role = rol.get();
			if (role.isDeleteStatus() == false) {
				role.setCreatedBy(role.getCreatedBy());
				role.setLastModifiedBy(jWtRequestfilter.getToken().getId());
				MessageResponse response = new MessageResponse(HttpStatus.OK.value(), HttpStatus.OK, true,
						"RoleDeleted Successfully");
				role.setDeleteStatus(true);
				role=roleRepository.save(role);
				if(role!=null) {
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
				else {
					throw new CustomException("Try Again Later");
				}
				
			} else {
				throw new CustomException("Role id was already deleted try another role id");
			}

		} else {
			throw new CustomException("Role id does not exist try another role id");
		}

	}
	public ResponseEntity<?> links(Long id) {
		RoleDto roleDto = new RoleDto();
		Optional<Roles> rol = roleRepository.findById(id);		
		Roles role = rol.get();
		roleDto=converterService.convertToRoleDto(role);
		DataResponse response= new DataResponse();
		roleDto.add(linkTo(methodOn(RoleController.class).getRole(id)).withSelfRel());
		roleDto.add(linkTo(methodOn(RoleController.class).deleteRole(id)).withRel("deleteRole"));
		roleDto.add(linkTo(methodOn(RoleController.class).updateRole(id, roleDto)).withRel("updateRole"));
		response.setData(roleDto);
		response.setMessage("Success");
		response.setStatus(true);
		response.setStatusCode(HttpStatus.OK.value());
		response.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
