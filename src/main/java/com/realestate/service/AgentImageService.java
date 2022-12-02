package com.realestate.service;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.domain.AgentImage;
import com.realestate.domain.AgentImageData;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.repository.AgentImageRepository;

@Service
public class AgentImageService {

	@Autowired
	private AgentImageRepository agentImageRepository;

	public String uploadImage(MultipartFile file) {
		AgentImage image=null;
		String fileName=StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		try {
			AgentImageData data=new AgentImageData(file.getBytes());
			image=new AgentImage(fileName,file.getContentType(),data);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		agentImageRepository.save(image);
		return image.getId();
		
	}
	
	public AgentImage getImageById(String id) {
		AgentImage image=agentImageRepository.findById(id).orElseThrow(()->
		        new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return image;
	}
	
	
}
