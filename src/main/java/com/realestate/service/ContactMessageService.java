package com.realestate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realestate.domain.ContactMessage;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.repository.ContactMessageRepository;

@Service
public class ContactMessageService {
	
	private ContactMessageRepository contactMessageRepository;
	
	@Autowired
	public ContactMessageService(ContactMessageRepository contactMessageRepository) {
		this.contactMessageRepository = contactMessageRepository;
	}

	public void saveMessage(ContactMessage contactMessage) {
		contactMessageRepository.save(contactMessage);
		
	}

	public List<ContactMessage> getAllMessage() {
		return contactMessageRepository.findAll();
	}

	public Page<ContactMessage> getAllMessage(Pageable pageable) {
		return contactMessageRepository.findAll(pageable);
	}

	public ContactMessage getMessageById(Long id) {
		return contactMessageRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
	}

	public void deleteContactMessage(Long id) {
		
		ContactMessage contactMessage=getMessageById(id);
		
		contactMessageRepository.delete(contactMessage);
		
	}

	public void updateContactmessage(Long id, ContactMessage contactMessage) {
		ContactMessage foundContactMessage=getMessageById(id);
		foundContactMessage.setName(contactMessage.getName());
		foundContactMessage.setBody(contactMessage.getBody());
		foundContactMessage.setEmail(contactMessage.getEmail());
		foundContactMessage.setSubject(contactMessage.getSubject());
		
		contactMessageRepository.save(foundContactMessage);	
	}

}
