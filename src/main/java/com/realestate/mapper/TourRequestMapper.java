package com.realestate.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.realestate.domain.TourRequest;
import com.realestate.dto.TourRequestDTO;
import com.realestate.dto.request.TourRequestRequest;

@Mapper(componentModel = "spring")
public interface TourRequestMapper {

	TourRequestDTO tourRequestToTourRequestDTO(TourRequest tourRequest);
	
	@Mapping(target = "id",ignore = true)
	@Mapping(target = "property",ignore = true)
	@Mapping(target = "status",ignore = true)
	@Mapping(target = "user",ignore = true)
	TourRequest tourRequestRequestToTourRequest(TourRequestRequest tourRequestRequest);
	
	List<TourRequestDTO> map(List<TourRequest> tourRequests);
}
