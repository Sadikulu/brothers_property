package com.realestate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.domain.PropertyDetail;
import com.realestate.dto.PropertyDetailDTO;
import com.realestate.dto.request.PropertyDetailRequest;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.mapper.PropertyDetailMapper;
import com.realestate.repository.PropertyDetailRepository;

@Service
public class PropertyDetailService {

	private PropertyDetailRepository propertyDetailRepository;
	private PropertyDetailMapper propertyDetailMapper;

	@Autowired
	public PropertyDetailService(PropertyDetailRepository propertyDetailRepository,
			PropertyDetailMapper propertyDetailMapper) {
		this.propertyDetailRepository = propertyDetailRepository;
		this.propertyDetailMapper = propertyDetailMapper;
	}

	public void createDetail(PropertyDetailRequest propertyDetailRequest) {
		PropertyDetail detail=propertyDetailMapper.propertyDetailRequestToPropertyDetail(propertyDetailRequest);
		propertyDetailRepository.save(detail);
		
	}

	public void updatePropertyDetail(Long id, PropertyDetailRequest propertyDetailRequest) {
		PropertyDetail detail=getOnlyById(id);
		detail.setTitle(propertyDetailRequest.getTitle());
		propertyDetailRepository.save(detail);
	}
	
	public PropertyDetail getDetailById(Long id) {
		PropertyDetail detail=propertyDetailRepository.findById(id).orElseThrow(()->
		   new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
		return detail;
	}

	public void removeDetail(Long id) {
		PropertyDetail detail=getDetailById(id);
		propertyDetailRepository.delete(detail);
	}
	
	public PropertyDetail getOnlyById(Long id) {
		PropertyDetail detail=propertyDetailRepository.findOnlyById(id).orElseThrow(()->
		   new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
		return detail;
	}

	public List<PropertyDetailDTO> getAllDetails() {
		List<PropertyDetail> propertyDetails= propertyDetailRepository.findAll();
		List<PropertyDetailDTO> propertyDetailDTOs=propertyDetailMapper.map(propertyDetails);
		return propertyDetailDTOs;
	}
}
