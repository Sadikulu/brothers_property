package com.realestate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.domain.Property;
import com.realestate.domain.TourRequest;
import com.realestate.domain.User;
import com.realestate.domain.enums.TourRequestStatus;
import com.realestate.dto.TourRequestDTO;
import com.realestate.dto.request.TourRequestRequest;
import com.realestate.exception.BadRequestException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.mapper.TourRequestMapper;
import com.realestate.repository.TourRequestRepository;

@Service
public class TourRequestService {

	private TourRequestRepository tourRequestRepository;
	private TourRequestMapper tourRequestMapper;

	@Autowired
	public TourRequestService(TourRequestRepository tourRequestRepository, TourRequestMapper tourRequestMapper) {
		this.tourRequestRepository = tourRequestRepository;
		this.tourRequestMapper = tourRequestMapper;
	}

	public TourRequestDTO getById(Long id) {
		TourRequest request = tourRequestRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		TourRequestDTO requestDTO = tourRequestMapper.tourRequestToTourRequestDTO(request);
		return requestDTO;
	}

	public TourRequestDTO findByIdAndUser(Long id, User user) {
		TourRequest request = tourRequestRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		TourRequestDTO requestDTO = tourRequestMapper.tourRequestToTourRequestDTO(request);
		return requestDTO;
	}

	public void addTourRequest(TourRequestRequest tourRequestRequest, Property property, User user) {
		checkTourRequestTimeIsCorrect(tourRequestRequest.getDateTime());
		boolean propertyStatus = checkPropertyAvailability(property, tourRequestRequest.getDateTime());
		TourRequest tourRequest = tourRequestMapper.tourRequestRequestToTourRequest(tourRequestRequest);
		if (!propertyStatus) {
			tourRequest.setStatus(TourRequestStatus.PENDİNG);
		} else {
			throw new BadRequestException(ErrorMessage.PROPERTY_NOT_AVAILABLE_MESSAGE);
		}
		tourRequest.setProperty(property);
		tourRequest.setUser(user);

		tourRequestRepository.save(tourRequest);
	}

	public void checkTourRequestTimeIsCorrect(LocalDateTime dateTime) {
		LocalDateTime now = LocalDateTime.now();
		if (dateTime.isBefore(now)) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}
	}

	public boolean checkPropertyAvailability(Property property, LocalDateTime dateTime) {
		List<TourRequest> existTourRequests = getConflictTourRequests(property, dateTime);
		return existTourRequests.size() > 0;
	}

	public List<TourRequest> getConflictTourRequests(Property property, LocalDateTime dateTime) {
		TourRequestStatus[] status = { TourRequestStatus.CANCELED, TourRequestStatus.DONE, TourRequestStatus.REJECTED };
		List<TourRequest> existTourRequests = tourRequestRepository.checkPropertyStatus(property.getId(), dateTime,
				status);
		return existTourRequests;
	}

	public TourRequest getByTourId(Long id) {
		TourRequest tourRequest = tourRequestRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return tourRequest;
	}

	public void updateTourRequest(Long id, Property property, TourRequestDTO tourRequestDTO) {
		boolean propertyStatus = checkPropertyAvailability(property, tourRequestDTO.getDateTime());
		TourRequest tourRequest = getByTourId(id);
		if (propertyStatus) {
			throw new BadRequestException(ErrorMessage.PROPERTY_NOT_AVAILABLE_MESSAGE);
		}
		LocalDateTime now = LocalDateTime.now();
		if (now.compareTo(tourRequestDTO.getDateTime()) > 0) {
			throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
		}
		tourRequest.setProperty(property);
		tourRequest.setDateTime(tourRequestDTO.getDateTime());
		tourRequest.setChild(tourRequestDTO.getChild());
		tourRequest.setAdult(tourRequestDTO.getAdult());
		tourRequest.setStatus(tourRequestDTO.getStatus());

		tourRequestRepository.save(tourRequest);
	}

	public void checkStatusByAdmin(Long id, TourRequestStatus status) {
		TourRequest tourRequest=getByTourId(id);
		LocalDateTime now=LocalDateTime.now();
		if (tourRequest.getStatus().equals(TourRequestStatus.PENDİNG) &&
			now.plusHours(1).isBefore(tourRequest.getDateTime()) &&
			(status.equals(TourRequestStatus.APPROVED) ||
					status.equals(TourRequestStatus.REJECTED))){
			tourRequest.setStatus(status);
		}else if (tourRequest.getStatus().equals(TourRequestStatus.PENDİNG) &&
				  now.plusHours(1).isAfter(tourRequest.getDateTime())) {
			tourRequest.setStatus(TourRequestStatus.REJECTED);
		}else {
			throw new BadRequestException(ErrorMessage.RESERVATION_STATUS_OR_TIME_INCORRECT_MESSAGE);
		}
		tourRequestRepository.save(tourRequest);
		
	}

	public void checkStatusByUser(Long id, TourRequestStatus status, User user) {
		TourRequest tourRequest=getByTourId(id);
		TourRequest userRequest=user.getTourRequest().stream().filter(t->t.getId()==tourRequest.getId())
				                .findFirst().orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		LocalDateTime today=LocalDateTime.now();
		if ((userRequest.getStatus().equals(TourRequestStatus.PENDİNG) || 
			 userRequest.getStatus().equals(TourRequestStatus.APPROVED)) && 
			 today.plusHours(1).isBefore(tourRequest.getDateTime()) && 
			 status.equals(TourRequestStatus.CANCELED)) {
			tourRequest.setStatus(status);
			tourRequestRepository.save(tourRequest);
		}else {
			throw new BadRequestException(ErrorMessage.TOUR_REQUEST_NOT_CANCELED_MESSAGE);
		}
	}

	public void removeById(Long id) {
		boolean exist=tourRequestRepository.existsById(id);
		if (!exist) {
			throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id));
		}
		tourRequestRepository.deleteById(id);
	}

	public List<TourRequestDTO> getAllTourRequest() {
		List<TourRequest> tourRequestList=tourRequestRepository.findAllBy();
		List<TourRequestDTO> tourRequestDTOList=tourRequestMapper.map(tourRequestList);
		return tourRequestDTOList;
	}

	public List<TourRequestDTO> getAllTourRequestByUser(User user) {
		List<TourRequest> tourRequestList=tourRequestRepository.findAllByUserId(user);
		List<TourRequestDTO> tourRequestDTOList=tourRequestMapper.map(tourRequestList);
		return tourRequestDTOList;
	}
	
	public boolean existsByProperty(Property property) {
		return tourRequestRepository.existsByProperty(property);
	}
	
	public boolean existsByUser(User user) {
		return tourRequestRepository.existsByUser(user);
	}
	
	
}
