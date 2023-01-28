package com.user.location.serviceImpls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.user.location.dtos.DistrictDto;
import com.user.location.entities.Districts;
import com.user.location.entities.States;
import com.user.exception.CustomException;
import com.user.location.interfaces.DistrictService;
import com.user.location.repository.DistrictRepository;
import com.user.location.repository.StateRepository;
import com.user.response.DataResponse;
import com.user.response.MessageResponse;
import com.user.common.DistrictDatas;
import com.user.common.PaginationMeta;
import com.user.common.StateDatas;
import com.user.configuration.JwtRequestFilter;

@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private ConverterServiceImpl converterService;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private PaginationMeta paginationMeta;
	@Autowired
	private JwtRequestFilter jWtRequestfilter;
	
	public ResponseEntity<?> SaveDistrict(DistrictDto st) {
		Optional<States> sn = stateRepository.findById(st.getStateId());
		MessageResponse response;
		if(sn.isPresent()) {
			if (sn.get().isDeleteStatus() == false) {
					Districts s = new Districts();
					s.setStates(sn.get());
					s.setDistrictName(st.getDistrictName());
					s.setShortCode(st.getShortCode());
					s.setStatus(st.getStatus());
					s.setCreatedBy(jWtRequestfilter.getToken().getId());
					s.setLastModifiedBy(jWtRequestfilter.getToken().getId());
					try {
						districtRepository.save(s);
						response = new MessageResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED, true,
								"District Created Successfully");
						return ResponseEntity.status(HttpStatus.CREATED).body(response);
					}catch (DataIntegrityViolationException e) {
						throw new CustomException("Districtname and Shortcode already Exist");
					}
					
				
				

				
			} else {
				throw new CustomException("State id was deleted try another state id");
			}
		}
		else {

			throw new CustomException("State id does not exist try another state id");
		}
		

	}
	 
	public ResponseEntity<?> getAllDistricts() {
		List<Districts> districts = districtRepository.findBydeleteStatus(false);
		List<DistrictDto> districtDto =districts
				.stream()
				.map(converterService::convertToDistrictDto)
				.collect(Collectors.toList());
		DataResponse response = new DataResponse();
		response.setData(districtDto);
		System.out.println("districts in repo "+districts.get(0).getStates());
		System.out.println("districts in dto id "+districtDto.get(0).getStateId());
		response.setMessage("Success");
		response.setStatus(true);
		response.setStatusCode(HttpStatus.OK.value());
		response.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	public ResponseEntity<?> getDistrictsByDistrictId(long id) {
		Optional<Districts> districts = districtRepository.findById(id);
		if (districts.isPresent()) {
			Districts district = districts.get();
			if (district.isDeleteStatus() == false) {
				DistrictDto districtDto = new DistrictDto();
				districtDto=converterService.convertToDistrictDto(district)	;
				DataResponse response = new DataResponse();
				response.setData(districtDto);
				response.setMessage("Success");
				response.setStatus(true);
				response.setStatusCode(HttpStatus.OK.value());
				response.setHttpStatus(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("District id was deleted try another district id");

			}
		}

		else {
			throw new CustomException("District id does not exist");

		}
	}

	public ResponseEntity<?> getDistrictsBySid(long id) {
		Optional<States> sis = stateRepository.findById(id);
		if (sis.isPresent()) {
			States s = sis.get();
			if (s.isDeleteStatus() == false) {
				List<Districts> dis = districtRepository.findByStates(s);
				List<DistrictDto> districtDto =dis
						.stream()
						.map(converterService::convertToDistrictDto)
						.collect(Collectors.toList());
				DataResponse response = new DataResponse();
				response.setData(districtDto);
				response.setMessage("Success");
				response.setStatus(true);
				response.setStatusCode(HttpStatus.OK.value());
				response.setHttpStatus(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {

				throw new CustomException("State id was deleted try another state id");
			}
		} else {

			throw new CustomException("State id does not exist try another state id");
		}

	}

	public ResponseEntity<?> deleteDistrict(long id) {
		Optional<Districts> dis = districtRepository.findById(id);
		MessageResponse response;
		if (dis.isPresent()) {
			Districts coun = dis.get();
			if (coun.isDeleteStatus() == false) {
				coun.setDeleteStatus(true);
				districtRepository.save(coun);
				response = new MessageResponse(HttpStatus.OK.value(), HttpStatus.OK, true,
						"District Deleted Successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("District id was deleted try another District id");

			}
		} else {
			throw new CustomException("District id does not exist try another District id");

		}

	}

	public ResponseEntity<?> updateStatus(long id, DistrictDto d) {
		Optional<Districts> district = districtRepository.findById(id);
		if (district.isPresent()) {
			Districts dis = district.get();
			if (dis.isDeleteStatus() == false) {
				dis.setStatus(d.getStatus());
				districtRepository.save(dis);
				HttpHeaders header = new HttpHeaders();
				header.add("message", "District status updated");
				return ResponseEntity.status(HttpStatus.OK).headers(header).body(dis);
			}

			else {
				throw new CustomException("District id was deleted try another District id");
			}
		} else {
			throw new CustomException("District id does not exist try another District id");
		}
	}

	public ResponseEntity<?> updateDistrict(long id, DistrictDto d) {
		Optional<States> state = stateRepository.findById(d.getStateId());
		MessageResponse response;
		if (state.isPresent()) {
			States sta = state.get();
			if (sta.isDeleteStatus() == false) {
				Optional<Districts> dis = districtRepository.findById(id);
				if (dis.isPresent()) {
					Districts district = dis.get();
					if (district.isDeleteStatus() == false) {
						
							district.setStates(sta);
							district.setDistrictName(d.getDistrictName());
							district.setShortCode(d.getShortCode());
							district.setCreatedBy(district.getCreatedBy());;
							district.setLastModifiedBy(jWtRequestfilter.getToken().getId());
							
							try {
								districtRepository.save(district);	
								response = new MessageResponse(HttpStatus.OK.value(), HttpStatus.OK, true,
										"District Updated Successfully");
								return ResponseEntity.status(HttpStatus.OK).body(response);
							}
							catch (DataIntegrityViolationException e) {
								throw new CustomException("Districtname and Shortcode already Exist");
							}
							
							
						
					} else {
						throw new CustomException("District id was deleted try another District id");
					}
				} else {
					throw new CustomException("District id does not exist try another District id");
				}
			} else {
				throw new CustomException("State id was deleted try another state id");
			}
		} else {
			throw new CustomException("State id does not exist try another state id");
		}

	}

	public ResponseEntity<?> getDistrictsByPage(Pageable pageable) {
		Page<Districts> districts = districtRepository.findAll(pageable);
		DataResponse response = new DataResponse();
		List <Districts> districtsContent=districts.getContent();
		List<DistrictDto> districtDto =districtsContent
				.stream()
				.map(converterService::convertToDistrictDto)
				.collect(Collectors.toList());
		PaginationMeta pagination = paginationMeta.createPagination(districts);
		DistrictDatas userDatas = new DistrictDatas();
		userDatas.setDistrict(districtDto);
		userDatas.setPagination(pagination);
		response.setMessage("Success");
		response.setStatus(true);
		response.setHttpStatus(HttpStatus.OK);		
		response.setData(userDatas);
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	
	}

}
