package com.realestate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.domain.AgentImage;
import com.realestate.dto.response.ImageSaveResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.AgentImageService;

@RestController
@RequestMapping("/agentImg")
public class AgentImageController {

	@Autowired
	private AgentImageService agentImageService;
	
	@PostMapping("/upload")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ImageSaveResponse> uploadFile(@RequestParam("file") MultipartFile file){
		 String imageId= agentImageService.uploadImage(file);
		 ImageSaveResponse response=new ImageSaveResponse(imageId,ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE,true);
		 return ResponseEntity.ok(response);
	}
	
	@GetMapping("/display/{id}")
	public ResponseEntity<byte[]> displayImage(@PathVariable String id){
		AgentImage image= agentImageService.getImageById(id);
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(image.getAgentImageData().getData(),headers,HttpStatus.CREATED);
	}
}
