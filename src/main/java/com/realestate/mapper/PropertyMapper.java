package com.realestate.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.realestate.domain.Image;
import com.realestate.domain.Property;
import com.realestate.domain.PropertyDetail;
import com.realestate.domain.Review;
import com.realestate.dto.PropertyDTO;
import com.realestate.dto.request.PropertyRequest;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "agent", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "likes", ignore = true)
	@Mapping(target = "propertyDetails", ignore = true)
	@Mapping(target = "review", ignore = true)
	@Mapping(target = "tourRequest", ignore = true)
	@Mapping(target = "visitCount", ignore = true)
	Property propertyRequestToProperty(PropertyRequest propertyRequest);

	List<PropertyDTO> map(List<Property> properties);

	@Mapping(source = "image", target = "images", qualifiedByName = "getImageAsString")
	@Mapping(source = "propertyDetails", target = "propertyDetail", qualifiedByName = "getDetailAsLong")
	@Mapping(source = "review", target = "review", qualifiedByName = "getReviewAsLong")
	PropertyDTO propertyToPropertyDTO(Property property);

	@Named("getImageAsString")
	public static Set<String> getImageAsString(Set<Image> images) {
		Set<String> imgs = new HashSet<>();
		imgs = images.stream().map(img -> img.getId().toString()).collect(Collectors.toSet());
		return imgs;
	}

	@Named("getDetailAsLong")
	public static Set<Long> getDetailAsLong(Set<PropertyDetail> propertyDetails) {
		Set<Long> details = new HashSet<>();
		propertyDetails.stream().forEach(proDetails -> {
			details.add(proDetails.getId());
		});
		return details;
	}

	@Named("getReviewAsLong")
	public static Set<Long> getReviewAsLong(Set<Review> reviews) {
		Set<Long> reviws = new HashSet<>();
		reviews.forEach(r -> {
			reviws.add(r.getId());
		});
		return reviws;
	}
}
