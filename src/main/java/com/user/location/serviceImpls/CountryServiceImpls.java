package com.user.location.serviceImpls;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.user.location.dtos.CountryDto;
import com.user.location.entities.Countries;
import com.user.exception.CustomException;
import com.user.location.interfaces.CountryService;
import com.user.location.repository.CountryRepository;
import com.user.response.DataResponse;
import com.user.response.MessageResponse;
import com.user.common.CountryDatas;
import com.user.common.PaginationMeta;
import com.user.common.UserDatas;
import com.user.configuration.JwtRequestFilter;
import com.user.dto.UserDto;
import com.user.entity.User;

@Service
public class CountryServiceImpls implements CountryService {

	@Autowired
	private CountryRepository countryRepository;	
	@Autowired
	private ConverterServiceImpl converterService;
	
	@Autowired
	private JwtRequestFilter jWtRequestfilter;
	@Autowired
	private PaginationMeta paginationMeta;

	
	public ResponseEntity<?> SaveCountry(CountryDto s) {
		
			Countries country = new Countries();
			country = converterService.convertToCountries(s);
			country.setCreatedBy(jWtRequestfilter.getToken().getId());
			country.setLastModifiedBy(jWtRequestfilter.getToken().getId());
			MessageResponse response;
			try {
				country=countryRepository.save(country);
				if(country!=null) {
					response=new MessageResponse(HttpStatus.CREATED.value(),HttpStatus.CREATED,true,"Country Created Successfully");
				}
				else {
					throw new CustomException("Try again Later");
				}
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);		
		}
			catch (DataIntegrityViolationException e) {
				throw new CustomException("Countryname and shortcode already exist");
			}
		
		
	}

	public ResponseEntity<?> viewWithoutDeletedCountries() {
		List<Countries> country = countryRepository.findBydeleteStatus(false);
		DataResponse response= new DataResponse();
		List<CountryDto> countryDto =country
				.stream()
				.map(converterService::convertToCountryDto)
				.collect(Collectors.toList());;
		response.setData(countryDto);
		response.setMessage("Success");
		response.setStatus(true);
		response.setStatusCode(HttpStatus.OK.value());
		response.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	public ResponseEntity<?> viewUsingCountryId(long id) {
		Optional<Countries> c = countryRepository.findById(id);
		if (c.isPresent()) {
			Countries co = c.get();
			CountryDto countryDto = new CountryDto();
			countryDto=converterService.convertToCountryDto(co);
			if (co.isDeleteStatus() == false) {
				DataResponse response= new DataResponse();
				response.setData(countryDto);
				response.setMessage("Success");
				response.setStatus(true);
				response.setStatusCode(HttpStatus.OK.value());
				response.setHttpStatus(HttpStatus.OK);
				return ResponseEntity.status(HttpStatus.OK).body(response);
				

			} else {
				throw new CustomException("Country id was deleted try another country id");
			}

		} else {
			throw new CustomException("Country id does not exist");
		}

	}

	public ResponseEntity<?> updateCountry(long id, Countries con) {
		Optional<Countries> c = countryRepository.findById(id);
		Countries countr = countryRepository.findBycountryName(con.getCountryName());
		if (c.isPresent()) {
			Countries coun = c.get();
			if (coun.isDeleteStatus() == false) {
				if(countr==null) {					
					MessageResponse response=new MessageResponse(HttpStatus.OK.value(),HttpStatus.OK,true,"Country Updated Successfully");
					coun.setCountryName(con.getCountryName());
					coun.setShortCode(con.getShortCode());
					coun.setCreatedBy(coun.getCreatedBy());;
					coun.setLastModifiedBy(jWtRequestfilter.getToken().getId());
					try {
						countryRepository.save(coun);
					}catch (DataIntegrityViolationException e) {
						throw new CustomException("Country name and shortcode already exist try another country name for update");
					}
					countryRepository.save(coun);
					return ResponseEntity.status(HttpStatus.OK).body(response);	
				}
				else {
					throw new CustomException("Country name already exist try another country name for update");
				}
				
				
			} else {
				throw new CustomException("Country deleted try another country id for update");

			}
		} else {
			throw new CustomException("Country Id does not exist try another country id");

		}

	}
	public ResponseEntity<?> viewAllCountries() {
		List<Countries> country = countryRepository.findAll();
		
		List<CountryDto> countryDto =country
				.stream()
				.map(converterService::convertToCountryDto)
				.collect(Collectors.toList());;
		DataResponse response= new DataResponse();
		response.setData(countryDto);
		response.setMessage("Success");
		response.setStatus(true);
		response.setStatusCode(HttpStatus.OK.value());
		response.setHttpStatus(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}



	public ResponseEntity<?> deleteCountry(long id) {
		Optional<Countries> con = countryRepository.findById(id);
		if (con.isPresent()) {
			Countries coun = con.get();
			if (coun.isDeleteStatus() == false) {
				coun.setDeleteStatus(true);
				MessageResponse response=new MessageResponse(HttpStatus.OK.value(),HttpStatus.OK,true,"Country Deleted Successfully");
				countryRepository.save(coun);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				throw new CustomException("Country Already Deleted");
			}

		} else {
			throw new CustomException("Country id does not exist enter a valid country id for delete");
		}

	}

	public ResponseEntity<?> updateStatus(long id, CountryDto s) {
		Optional<Countries> coun = countryRepository.findById(id);
		if (coun.isPresent()) {
			Countries con = coun.get();
			if (con.isDeleteStatus() == false) {
				con.setStatus(s.getStatus());
				countryRepository.save(con);
				return ResponseEntity.status(HttpStatus.OK).body(con);
			} else {
				throw new CustomException("Country deleted try another country id");
			}
		} else {
			throw new CustomException("Country id does not exist");
		}

	}

	public ResponseEntity<?> getCountriesByPage(Pageable pageable) {
		Page<Countries> country = countryRepository.findAll(pageable);
		DataResponse response = new DataResponse();
		List <Countries> countryContent=country.getContent();
		List<CountryDto> countryDto =countryContent
				.stream()
				.map(converterService::convertToCountryDto)
				.collect(Collectors.toList());;
		PaginationMeta pagination = paginationMeta.createPagination(country);
		CountryDatas userDatas = new CountryDatas();
		userDatas.setCountry(countryDto);
		userDatas.setPagination(pagination);
		response.setMessage("Success");
		response.setStatus(true);
		response.setHttpStatus(HttpStatus.OK);		
		response.setData(userDatas);
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	
	}

}
