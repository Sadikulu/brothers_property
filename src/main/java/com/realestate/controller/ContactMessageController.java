package com.realestate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.domain.ContactMessage;
import com.realestate.dto.ContactMessageDTO;
import com.realestate.dto.request.ContactMessageRequest;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.dto.response.VRResponse;
import com.realestate.mapper.ContactMessageMapper;
import com.realestate.service.ContactMessageService;

@RestController
@RequestMapping("/contactmessage")
public class ContactMessageController {

	private ContactMessageService contactMessageService;
	private ContactMessageMapper contactMessageMapper;

	@Autowired
	public ContactMessageController(ContactMessageService contactMessageService,
			ContactMessageMapper contactMessageMapper) {
		this.contactMessageService = contactMessageService;
		this.contactMessageMapper = contactMessageMapper;
	}

	@PostMapping("/visitors")
	public ResponseEntity<VRResponse> createMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest) {
		ContactMessage contactMessage = contactMessageMapper.contactMessageRequestToMessage(contactMessageRequest);
		contactMessageService.saveMessage(contactMessage);
		VRResponse response = new VRResponse(ResponseMessage.CONTACTMESSAGE_CREATED_SUCCESSFULLY, true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ContactMessageDTO>> getAllContactMessage() {
		List<ContactMessage> contactMessageList = contactMessageService.getAllMessage();
		List<ContactMessageDTO> contactMessageDTOList = contactMessageMapper.map(contactMessageList);
		return ResponseEntity.ok(contactMessageDTOList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactMessageDTO> getMessageByIdWithPath(@PathVariable("id") Long id) {
		ContactMessage contactMessage = contactMessageService.getMessageById(id);
		ContactMessageDTO contactMessageDTO = contactMessageMapper.contactMessageToDTO(contactMessage);
		return ResponseEntity.ok(contactMessageDTO);
	}

	@GetMapping("/request")
	public ResponseEntity<ContactMessageDTO> getMessageByIdWithParam(@RequestParam("id") Long id) {
		ContactMessage contactMessage = contactMessageService.getMessageById(id);
		ContactMessageDTO contactMessageDTO = contactMessageMapper.contactMessageToDTO(contactMessage);
		return ResponseEntity.ok(contactMessageDTO);
	}

	@GetMapping("/pages")
	public ResponseEntity<Page<ContactMessageDTO>> getAllContactMessageWithPage(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam("sort") String prop,
			@RequestParam(value = "direction", required = false, defaultValue = "DESC") Direction direction) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
		Page<ContactMessage> contactMessagePage = contactMessageService.getAllMessage(pageable);
		Page<ContactMessageDTO> pageDTO = getPageDTO(contactMessagePage);
		return ResponseEntity.ok(pageDTO);
	}
	@DeleteMapping("/{id}")
	
	public ResponseEntity<VRResponse> deleteContactMessage(@PathVariable ("id") Long id ){
		
		contactMessageService.deleteContactMessage(id);
		
		VRResponse response=new VRResponse(ResponseMessage.CONTACTMESSAGE_DELETED_SUCCESSFULLY,true);
		
		return ResponseEntity.ok(response);
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<VRResponse> updatedContactMessage(@PathVariable  ("id")Long id,
			@Valid @RequestBody ContactMessageRequest contactMessageRequest){
		
	ContactMessage contactMessage=	contactMessageMapper.contactMessageRequestToMessage
			(contactMessageRequest);
	contactMessageService.updateContactmessage(id,contactMessage);
	
	VRResponse response=new VRResponse(ResponseMessage.CONTACTMESSAGE_UPDATED_SUCCESSFULLY,true);
	
	return ResponseEntity.ok(response);
		
		
	}
	

	private Page<ContactMessageDTO> getPageDTO(Page<ContactMessage> contactMessagePage) {
		Page<ContactMessageDTO> dtoPage = contactMessagePage
				.map(new java.util.function.Function<ContactMessage, ContactMessageDTO>() {
					@Override
					public ContactMessageDTO apply(ContactMessage contactMessage) {
						return contactMessageMapper.contactMessageToDTO(contactMessage);
					}
				});
		return dtoPage;
	}

}
