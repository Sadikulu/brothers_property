package com.realestate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.domain.Property;
import com.realestate.domain.Review;
import com.realestate.domain.User;
import com.realestate.domain.enums.ReviewStatus;
import com.realestate.dto.ReviewDTO;
import com.realestate.dto.request.ReviewRequest;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.mapper.ReviewMapper;
import com.realestate.repository.ReviewRepository;

@Service
public class ReviewService {

	private ReviewRepository reviewRepository;
	private ReviewMapper reviewMapper;
	
	@Autowired
	public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
		this.reviewRepository = reviewRepository;
		this.reviewMapper = reviewMapper;
	}

	public void createReview(Property property, User user, ReviewRequest reviewRequest) {
		
		Review review=reviewMapper.reviewRequestToReview(reviewRequest);
		review.setProperty(property);
		review.setUser(user);
		
		reviewRepository.save(review);
		
	}

	public List<ReviewDTO> getAllByPropertyId(Long propertyId) {
		List<Review> reviewList= reviewRepository.findAllByPropertyId(propertyId);
		List<ReviewDTO> reviewDTOList=reviewMapper.map(reviewList);
		return reviewDTOList;
	}

	public ReviewDTO findReviewById(Long id) {
		Review review=reviewRepository.findById(id).orElseThrow(()->
		     new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
		ReviewDTO reviewDTO=reviewMapper.reviewToReviewDTO(review);
		return reviewDTO;
	}

	public void updateReview(Long reviewId, User user, Property property,ReviewRequest reviewRequest) {
		Review review=getReviewById(reviewId);
		review.setProperty(property);
		review.setScore(reviewRequest.getScore());
		review.setUser(user);
		review.setReview(reviewRequest.getReview());
		reviewRepository.save(review);
	}
	
	public Review getReviewById(Long id) {
		return reviewRepository.findById(id).orElseThrow(()->
	     new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
	}

	public ReviewDTO findByIdAndUserId(Long id, User user) {
		Review review= reviewRepository.findByUserId(id,user.getId()).orElseThrow(()->
	     new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));;
	     ReviewDTO reviewDTO=reviewMapper.reviewToReviewDTO(review);
		return reviewDTO;
	}

	public void updateReviewStatus(Long id, String status) {
		Review review=getReviewById(id);
		if (status.equalsIgnoreCase("PUBLISHED")) {
			review.setStatus(ReviewStatus.PUBLISHED);
		}else if (status.equalsIgnoreCase("REJECTED")) {
			review.setStatus(ReviewStatus.REJECTED);
		}
		reviewRepository.save(review);
	}

	public void removeUserReviewById(Long id, User user) {
		Review review=reviewRepository.findByUserId(id,user.getId()).orElseThrow(()->
		      new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
		reviewRepository.delete(review);
	}

	public void removeById(Long id) {
		Review review=getReviewById(id);
		reviewRepository.delete(review);
		
	}

	public List<ReviewDTO> findAllUserReviews(User user) {
		List<Review> reviewList=reviewRepository.findAllByUserId(user.getId());
		List<ReviewDTO> reviewDTOList=reviewMapper.map(reviewList);
		return reviewDTOList;
	}
	
}
