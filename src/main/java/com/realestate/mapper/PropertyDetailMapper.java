package com.realestate.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.realestate.domain.PropertyDetail;
import com.realestate.dto.PropertyDetailDTO;
import com.realestate.dto.request.PropertyDetailRequest;

@Mapper(componentModel = "spring")
public interface PropertyDetailMapper {

	@Mapping(target = "id",ignore = true)
	PropertyDetail propertyDetailRequestToPropertyDetail(PropertyDetailRequest propertyDetailRequest);
	
	List<PropertyDetailDTO>  map(List<PropertyDetail> propertyDetails);
}
