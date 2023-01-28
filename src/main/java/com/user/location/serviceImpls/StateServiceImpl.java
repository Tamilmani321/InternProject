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
import com.user.location.dtos.StateDto;
import com.user.location.entities.Countries;
import com.user.location.entities.States;
import com.user.location.interfaces.StateService;
import com.user.location.repository.CountryRepository;
import com.user.location.repository.StateRepository;
import com.user.response.DataResponse;
import com.user.response.MessageResponse;
import com.user.common.CountryDatas;
import com.user.common.PaginationMeta;
import com.user.common.StateDatas;
import com.user.configuration.JwtRequestFilter;
import com.user.exception.CustomException;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ConverterServiceImpl converterService;

	@Autowired
	private JwtRequestFilter jWtRequestfilter;
	
	@Autowired
	private PaginationMeta paginationMeta;


	
	public ResponseEntity<?> SaveState(StateDto st) {
		Optional<Countries> c = countryRepository.findById(st.getCountryId());
		States sta = stateRepository.findBystateName(st.getStateName());
		MessageResponse response;
		if (sta == null) {
			if (c.isPresent()) {
				if (c.get().isDeleteStatus() == false) {
					States s = new States();
					s.setCountry(c.get());
					s.setShortCode(st.getShortCode());
					s.setStateName(st.getStateName());
					s.setDeleteStatus(false);
					s.setStatus(st.getStatus());
					s.setCreatedBy(jWtRequestfilter.getToken().getId());
					s.setLastModifiedBy(jWtRequestfilter.getToken().getId());
					try {
						stateRepository.save(s);
						response = new MessageResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED, true,
								"State Created Successfully");
						return ResponseEntity.status(HttpStatus.CREATED).body(response);
					}catch (DataIntegrityViolationException e) {
						throw new CustomException("Statename and Shorcode already Exist");
					}
					
					
				} else {

					throw new CustomException("Country id was deleted try another Country id");
				}

			} else {

				throw new CustomException("Country id does not exist try another Country id");
			}
		} else {
			throw new CustomException("Statename already Exist");
			
		}

	}

	public ResponseEntity<?> getAllStates() {
		List<States> state = stateRepository.findBydeleteStatus(false);
		DataResponse response = new DataResponse();
		List<StateDto> stateDto =state
				.stream()
				.map(converterService::convertToStateDto)
				.collect(Collectors.toList());
		response.setData(stateDto);
		response.setMessage("Success");
		response.setStatus(true);
		response.setStatusCode(HttpStatus.OK.value());
		response.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public ResponseEntity<?> getStatesByStateId(long id) {
		Optional<States> states = stateRepository.findById(id);
		if (states.isPresent()) {
			States di = states.get();
			if (di.isDeleteStatus() == false) {
				
				List<StateDto> stateDto =states
						.stream()
						.map(converterService::convertToStateDto)
						.collect(Collectors.toList());
				DataResponse response = new DataResponse();
				response.setData(stateDto);
				response.setMessage("Success");
				response.setStatus(true);
				response.setStatusCode(HttpStatus.OK.value());
				response.setHttpStatus(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("State id was deleted try another state id");

			}
		}

		else {
			throw new CustomException("State id does not exist");

		}
	}

	public ResponseEntity<?> getStatesByCountryid(long id) {
		Optional<Countries> sis = countryRepository.findById(id);
		if (sis.isPresent()) {
			Countries coun = sis.get();
			if (coun.isDeleteStatus() == false) {
				List<States> states = stateRepository.findByCountry(coun);
				List<StateDto> stateDto =states
						.stream()
						.map(converterService::convertToStateDto)
						.collect(Collectors.toList());
				DataResponse response = new DataResponse();
				response.setData(stateDto);
				response.setMessage("Success");
				response.setStatus(true);
				response.setStatusCode(HttpStatus.OK.value());
				response.setHttpStatus(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {

				throw new CustomException("Country id was deleted try another Country id");
			}
		} else {

			throw new CustomException("Country id does not exist try another Country id");
		}

	}

	public ResponseEntity<?> updateState(long id, StateDto d) {
		Optional<Countries> stat = countryRepository.findById(d.getCountryId());
		States states = stateRepository.findBystateName(d.getStateName());
		MessageResponse response;
		if (stat.isPresent()) {
			Countries country = stat.get();
			if (country.isDeleteStatus() == false) {
				Optional<States> sta = stateRepository.findById(id);
				if (sta.isPresent()) {
					States state = sta.get();
					if (state.isDeleteStatus() == false) {
						if (states == null) {
							state.setStatus(d.getStatus());
							state.setStateName(d.getStateName());
							state.setShortCode(d.getShortCode());
							state.setCreatedBy(state.getCreatedBy());
							state.setLastModifiedBy(jWtRequestfilter.getToken().getId());
							try {
								stateRepository.save(state);	
								response = new MessageResponse(HttpStatus.OK.value(), HttpStatus.OK, true,
										"State Updated Successfully");
								return ResponseEntity.status(HttpStatus.OK).body(response);
							}
							catch (DataIntegrityViolationException e) {
								throw new CustomException("Statename and Shortcode already Exist");
							}
							
						} else {

							
							throw new CustomException("Statename already Exist");
						}

					} else {
						throw new CustomException("State id was deleted try another State id");
					}
				} else {
					throw new CustomException("State id does not exist try another State id");
				}
			} else {
				throw new CustomException("Country id was deleted try another Country id");
			}
		} else {
			throw new CustomException("Country id does not exist try another Country id");
		}

	}

	public ResponseEntity<?> updateStatus(long id, StateDto d) {
		Optional<States> state = stateRepository.findById(id);
		if (state.isPresent()) {
			States stat = state.get();
			if (stat.isDeleteStatus() == false) {
				stat.setStatus(d.getStatus());
				stateRepository.save(stat);
				HttpHeaders header = new HttpHeaders();
				header.add("message", "State status updated");
				return ResponseEntity.status(HttpStatus.OK).headers(header).body(stat);
			}

			else {
				throw new CustomException("State id was deleted try another State id");
			}
		} else {
			throw new CustomException("State id does not exist try another State id");
		}
	}

	public ResponseEntity<?> deleteState(long id) {
		Optional<States> dis = stateRepository.findById(id);
		MessageResponse response;
		if (dis.isPresent()) {
			States coun = dis.get();
			if (coun.isDeleteStatus() == false) {
				coun.setDeleteStatus(true);
				stateRepository.save(coun);
				response = new MessageResponse(HttpStatus.OK.value(), HttpStatus.OK, true,
						"State Deleted Successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("State id was deleted try another State id");

			}
		} else {
			throw new CustomException("State id does not exist try another State id");

		}

	}

	public ResponseEntity<?> getStatesByPage(Pageable pageable) {
		Page<States> states = stateRepository.findAll(pageable);
		DataResponse response = new DataResponse();
		List <States> stateContent=states.getContent();
		List<StateDto> stateDto =stateContent
				.stream()
				.map(converterService::convertToStateDto)
				.collect(Collectors.toList());
		PaginationMeta pagination = paginationMeta.createPagination(states);
		StateDatas userDatas = new StateDatas();
		userDatas.setState(stateDto);
		userDatas.setPagination(pagination);
		response.setMessage("Success");
		response.setStatus(true);
		response.setHttpStatus(HttpStatus.OK);		
		response.setData(userDatas);
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	
	}

}
