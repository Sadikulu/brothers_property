package com.realestate.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.realestate.domain.ContactMessage;
import com.realestate.dto.ContactMessageDTO;
import com.realestate.dto.request.ContactMessageRequest;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {
	
	ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);
	
	@Mapping(target = "id",ignore = true)
	ContactMessage contactMessageRequestToMessage(ContactMessageRequest contactMessageRequest);
	
	List<ContactMessageDTO> map(List<ContactMessage> list);
	
	//TODO
	//ContactMessage contactMessageToContactMessage(Long id,ContactMessage contactMessage);
}
