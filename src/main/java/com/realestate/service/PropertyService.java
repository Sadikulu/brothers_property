package com.realestate.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.realestate.domain.Agent;
import com.realestate.domain.Image;
import com.realestate.domain.Like;
import com.realestate.domain.Property;
import com.realestate.domain.PropertyDetail;
import com.realestate.domain.User;
import com.realestate.domain.enums.PropertyCategory;
import com.realestate.dto.PropertyDTO;
import com.realestate.dto.request.PropertyRequest;
import com.realestate.exception.BadRequestException;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.mapper.PropertyMapper;
import com.realestate.repository.LikeRepository;
import com.realestate.repository.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private PropertyMapper propertyMapper;
	@Autowired
	private TourRequestService tourRequestService;
	@Autowired
	private LikeRepository likeRepository;

	public Property getPropertyById(Long id) {
		Property property = propertyRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return property;
	}

	public Property findPropertyById(Long id) {
		Property property = propertyRepository.findPropertyById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return property;
	}

	public void createProperty(Agent agent, PropertyDetail detail, Image image, PropertyRequest propertyRequest) {
		Property property = propertyMapper.propertyRequestToProperty(propertyRequest);
		property.setVisitCount(0L);
		property.setAgent(agent);
		Set<PropertyDetail> details = new HashSet<>();
		details.add(detail);
		property.setPropertyDetails(details);
		Integer usedPropertyCount = propertyRepository.findPropertyCountByImageId(image.getId());
		if (usedPropertyCount > 0) {
			throw new ConflictException(ErrorMessage.IMAGE_PROPERTY_USED_MESSAGE);
		}
		Set<Image> images = new HashSet<>();
		images.add(image);
		property.setImage(images);

		propertyRepository.save(property);
	}

	public void updateProperty(Long id, Agent agent, PropertyDetail detail, Image image,
			PropertyRequest propertyRequest) {
		Property property = findPropertyById(id);
		List<Property> propertieList = propertyRepository.findPropertiesByImageId(image.getId());
			for (Property p : propertieList) {
				if (property.getId().longValue() != p.getId().longValue()) {
					throw new ConflictException(ErrorMessage.IMAGE_PROPERTY_USED_MESSAGE);
				}
			}
		
		property.setAgent(agent);
		property.setAdress(propertyRequest.getAdress());
		property.setArea(propertyRequest.getArea());
		property.setBathrooms(propertyRequest.getBathrooms());
		property.setBedrooms(propertyRequest.getBedrooms());
		property.setCategory(propertyRequest.getCategory());
		property.setCity(propertyRequest.getCity());
		property.setCountry(propertyRequest.getCountry());
		property.setDescription(propertyRequest.getDescription());
		property.setDistrict(propertyRequest.getDistrict());
		property.setGarages(propertyRequest.getGarages());
		property.getImage().add(image);
		property.setLocation(propertyRequest.getLocation());
		property.setPrice(propertyRequest.getPrice());
		property.setStatus(propertyRequest.getStatus());
		property.getPropertyDetails().add(detail);
		property.setTitle(propertyRequest.getTitle());
		property.setType(propertyRequest.getType());

		propertyRepository.save(property);
	}

	public List<PropertyDTO> getAllProperty() {
		List<Property> properties = propertyRepository.findAll();
		List<PropertyDTO> propertyDTOs = propertyMapper.map(properties);
		return propertyDTOs;

	}

	public PropertyDTO findPropertyDTOById(Long id) {
		Property property = findPropertyById(id);

		Long totalCount = property.getVisitCount();
		property.setVisitCount(totalCount + 1);

		propertyRepository.save(property);
		PropertyDTO propertyDTO = propertyMapper.propertyToPropertyDTO(property);
		return propertyDTO;
	}

	public void deleteProperty(Long id) {
		Property property = findPropertyById(id);
		boolean exist = tourRequestService.existsByProperty(property);
		if (exist) {
			throw new BadRequestException(ErrorMessage.PROPERTY_USED_BY_TOUR_REQUEST_MESSAGE);
		}
		propertyRepository.delete(property);
	}

	public Long setLike(Long id, User user) {
		Property property = findPropertyById(id);
		if (likeRepository.existsByPropertyIdAndUserId(id, user.getId())) {
			Like like = likeRepository.findByPropertyIdAndUserId(id, user.getId());
			if (like.getLiked() == false) {
				like.setLiked(false);
				Long decreaseLike = property.getLikes() - 1;
				property.setLikes(decreaseLike);
			} else {
				like.setLiked(false);
				Long increaseLike = property.getLikes() + 1;
				property.setLikes(increaseLike);
			}
			propertyRepository.save(property);
			likeRepository.save(like);
		} else {
			Like like = new Like();
			like.setPropertyId(id);
			like.setUserId(user.getId());
			like.setLiked(false);
			Long increaseLike = property.getLikes() + 1;
			property.setLikes(increaseLike);
			propertyRepository.save(property);
			likeRepository.save(like);
		}
		return property.getLikes();
	}

	public List<PropertyDTO> findAllPropertyWithCategory(PropertyCategory category) {
		List<Property> properties = propertyRepository.findAllByCategory(category);
		List<PropertyDTO> propertyDTOs = propertyMapper.map(properties);
		return propertyDTOs;
	}

	public List<PropertyDTO> searchProperty(Specification<Property> customerNameSpec) {
		List<Property> properties = propertyRepository.findAll(customerNameSpec);
		List<PropertyDTO> propertyDTOs = propertyMapper.map(properties);
		return propertyDTOs;
	}

}
