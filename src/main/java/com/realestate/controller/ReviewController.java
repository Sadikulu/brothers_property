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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.domain.Property;
import com.realestate.domain.User;
import com.realestate.dto.ReviewDTO;
import com.realestate.dto.request.ReviewRequest;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.PropertyService;
import com.realestate.service.ReviewService;
import com.realestate.service.UserService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private UserService userService;
	@Autowired
	private PropertyService propertyService;
	
	@PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<REResponse> addReview(@RequestParam("propertyId") Long propertyId,
			                                    @Valid @RequestBody ReviewRequest reviewRequest){
		User user=userService.getCurrentUser();
		Property property=propertyService.getPropertyById(propertyId);
		reviewService.createReview(property,user,reviewRequest);
		REResponse response=new REResponse(ResponseMessage.REVIEW_SAVED_RESPONSE_MESSAGE,true);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	@GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<ReviewDTO>> getReviews(@RequestParam("propertyId") Long propertyId){
		Property property=propertyService.findPropertyById(propertyId);
		List<ReviewDTO> reviewDTOList=reviewService.getAllByPropertyId(property.getId());
		return ResponseEntity.ok(reviewDTOList);
	}
	
	@GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id){
		ReviewDTO reviewDTO=reviewService.findReviewById(id);
		return ResponseEntity.ok(reviewDTO);
	}
	
	@PutMapping("/update/auth")
	@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<REResponse> updateReview(@RequestParam("reviewId") Long reviewId,
			                                       @RequestParam("propertyId") Long propertyId,
			                                       @Valid @RequestBody ReviewRequest reviewRequest){
		User user=userService.getCurrentUser();
		Property property=propertyService.getPropertyById(propertyId);
		reviewService.updateReview(reviewId,user,property,reviewRequest);
		REResponse response=new REResponse(ResponseMessage.REVIEW_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<ReviewDTO> getUserReviewById(@PathVariable Long id){
		User user=userService.getCurrentUser();
		ReviewDTO reviewDTOs= reviewService.findByIdAndUserId(id,user);
		return ResponseEntity.ok(reviewDTOs);
	}
	
	@PatchMapping("/{id}/updateStatus")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> updateStatus(@PathVariable Long id,@RequestParam("status") String status){
		reviewService.updateReviewStatus(id,status);
		REResponse response=new REResponse(ResponseMessage.REVIEW_STATUS_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<REResponse> deleteUserReviewById(@PathVariable Long id){
		User user=userService.getCurrentUser();
		reviewService.removeUserReviewById(id,user);
		REResponse response=new REResponse(ResponseMessage.REVIEW_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteReview(@PathVariable Long id){
		reviewService.removeById(id);
		REResponse response=new REResponse(ResponseMessage.REVIEW_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/all/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
	public ResponseEntity<List<ReviewDTO>> getUserAllReviews(){
		User user=userService.getCurrentUser();
		List<ReviewDTO>  reviewDTOs= reviewService.findAllUserReviews(user);
		return ResponseEntity.ok(reviewDTOs);
	}
}


