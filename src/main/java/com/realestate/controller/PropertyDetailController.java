package com.realestate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.dto.PropertyDetailDTO;
import com.realestate.dto.request.PropertyDetailRequest;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.PropertyDetailService;

@RestController
@RequestMapping("/detail")
public class PropertyDetailController {

	@Autowired
	private PropertyDetailService propertyDetailService;
	
	@PostMapping("/auth/add")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> createDeatail(@Valid @RequestBody PropertyDetailRequest propertyDetailRequest){
		propertyDetailService.createDetail(propertyDetailRequest);
		REResponse response=new REResponse(ResponseMessage.PROPERTY_DETAIL_CREATED_RESPONSE_MESSAGE,true);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PatchMapping("/auth/update")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> updateDetail(@RequestParam("id") Long id,
			                                       @Valid @RequestBody PropertyDetailRequest propertyDetailRequest){
		propertyDetailService.updatePropertyDetail(id,propertyDetailRequest);
		REResponse response=new REResponse(ResponseMessage.PROPERTY_DETAIL_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PropertyDetailDTO>> getAllDetails(){
		List<PropertyDetailDTO> propertyDetailDTOs=propertyDetailService.getAllDetails();
		return ResponseEntity.ok(propertyDetailDTOs);
	}
	
	@DeleteMapping("/auth/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteDetail(@PathVariable Long id){
		propertyDetailService.removeDetail(id);
		REResponse response=new REResponse(ResponseMessage.PROPERTY_DETAIL_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
}
