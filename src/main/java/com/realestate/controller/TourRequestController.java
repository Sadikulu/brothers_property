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

import com.realestate.domain.Property;
import com.realestate.domain.User;
import com.realestate.domain.enums.TourRequestStatus;
import com.realestate.dto.TourRequestDTO;
import com.realestate.dto.request.TourRequestRequest;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.PropertyService;
import com.realestate.service.TourRequestService;
import com.realestate.service.UserService;

@RestController
@RequestMapping("/tour")
public class TourRequestController {

	@Autowired
	private TourRequestService tourRequestService;
	@Autowired
	private UserService userService;
	@Autowired
	private PropertyService propertyService;
	
	@GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TourRequestDTO> getTourRequestById(@PathVariable Long id){
		TourRequestDTO reservation=tourRequestService.getById(id);
		return ResponseEntity.ok(reservation);
	}
	
	@GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<TourRequestDTO> getUserTourRequestById(@PathVariable Long id){
		User user=userService.getCurrentUser();
		TourRequestDTO reservation=tourRequestService.findByIdAndUser(id,user);
		return ResponseEntity.ok(reservation);
	}
	
	@PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<REResponse> createTourRequest(@RequestParam("propertyId") Long propertyId,
			                                            @Valid @RequestBody TourRequestRequest tourRequest){
		User user=userService.getCurrentUser();
		Property property=propertyService.getPropertyById(propertyId);
		tourRequestService.addTourRequest(tourRequest,property,user);
		REResponse response=new REResponse(ResponseMessage.TOUR_REQUEST_CREATED_RESPONSE_MESSAGE,true);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PatchMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> updateTourRequest(@RequestParam("propertyId") Long propertyId,
			                                            @RequestParam("id") Long id,
			                                            @Valid @RequestBody TourRequestDTO tourRequestDTO){
		Property property=propertyService.getPropertyById(id);
		tourRequestService.updateTourRequest(id,property,tourRequestDTO);
		REResponse response=new REResponse(ResponseMessage.TOUR_REQUEST_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/admin/{id}/check_status")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> checkStatusByAdmin(@PathVariable("id") Long id,
			                                             @RequestParam("status") TourRequestStatus status){
		tourRequestService.checkStatusByAdmin(id,status);
		REResponse response=new REResponse(ResponseMessage.TOUR_REQUEST_STATUS_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/check_status")
    @PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<REResponse> checkStatusByUser(@PathVariable("id") Long id,
                                                        @RequestParam("status") TourRequestStatus status){
		User user=userService.getCurrentUser();
		tourRequestService.checkStatusByUser(id,status,user);
		REResponse response=new REResponse(ResponseMessage.TOUR_REQUEST_STATUS_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteTourRequest(@PathVariable Long id){
		tourRequestService.removeById(id);
		REResponse response=new REResponse(ResponseMessage.TOUR_REQUEST_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<TourRequestDTO>> getAllTourRequest(){
		List<TourRequestDTO> tourRequestDTOs= tourRequestService.getAllTourRequest();
		return ResponseEntity.ok(tourRequestDTOs);
	}
	
	@GetMapping("/all")
    @PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<List<TourRequestDTO>> getAllUserTourRequest(){
		User user=userService.getCurrentUser();
		List<TourRequestDTO> tourRequestDTOs= tourRequestService.getAllTourRequestByUser(user);
		return ResponseEntity.ok(tourRequestDTOs);
	}
}
