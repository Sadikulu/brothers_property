package com.realestate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.domain.Image;
import com.realestate.dto.ImageDTO;
import com.realestate.dto.response.ImageSaveResponse;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.ImageService;

@RestController
@RequestMapping("/files")
public class PropertyImageController {

	@Autowired
	private ImageService imageService;

	@PostMapping("/upload")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ImageSaveResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		String imageId = imageService.saveImage(file);
		ImageSaveResponse response = new ImageSaveResponse(imageId, ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
		Image image = imageService.getImageById(id);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + image.getName())
				.body(image.getImageData().getData());
	}

	@GetMapping("/display/{id}")
	public ResponseEntity<byte[]> displayFile(@PathVariable String id) {
		Image image = imageService.getImageById(id);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(image.getImageData().getData(), header, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ImageDTO>> getAllImages(){
		List<ImageDTO> allImegeDTO= imageService.getAllImages();
		return ResponseEntity.ok(allImegeDTO);
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteFile(@PathVariable String id){
		imageService.deleteById(id);
		REResponse response=new REResponse(ResponseMessage.IMAGE_DELETE_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
}
