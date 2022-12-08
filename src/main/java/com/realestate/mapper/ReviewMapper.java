package com.realestate.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.realestate.domain.Review;
import com.realestate.dto.ReviewDTO;
import com.realestate.dto.request.ReviewRequest;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

	@Mapping(target = "id",ignore = true)
	@Mapping(target = "createDate",ignore = true)
	@Mapping(target = "user",ignore = true)
	@Mapping(target = "property",ignore = true)
	@Mapping(target = "status",ignore = true)
	Review reviewRequestToReview(ReviewRequest reviewRequest);
	
	List<ReviewDTO> map(List<Review> reviews);
	
	ReviewDTO reviewToReviewDTO(Review review);
}
