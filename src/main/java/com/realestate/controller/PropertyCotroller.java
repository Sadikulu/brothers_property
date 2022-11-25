package com.realestate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.domain.Agent;
import com.realestate.domain.Image;
import com.realestate.domain.Property;
import com.realestate.domain.PropertyDetail;
import com.realestate.domain.User;
import com.realestate.domain.enums.PropertyCategory;
import com.realestate.dto.PropertyDTO;
import com.realestate.dto.request.PropertyRequest;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.AgentService;
import com.realestate.service.ImageService;
import com.realestate.service.PropertyDetailService;
import com.realestate.service.PropertyService;
import com.realestate.service.UserService;

import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping("/property")
public class PropertyCotroller {

	private PropertyService propertyService;
	private ImageService imageService;
	private AgentService agentService;
	private PropertyDetailService propertyDetailService;
    private UserService userService;
	
	@Autowired
	public PropertyCotroller(PropertyService propertyService, ImageService imageService, AgentService agentService,
			PropertyDetailService propertyDetailService,UserService userService) {
		this.propertyService = propertyService;
		this.imageService = imageService;
		this.agentService = agentService;
		this.propertyDetailService = propertyDetailService;
		this.userService=userService;
	}

	@PostMapping("/admin/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> addProperty(@RequestParam("agentId") Long agentId,
			@RequestParam("detailId") Long detailId, @RequestParam("imageId") String imageId,
			@Valid @RequestBody PropertyRequest propertyRequest) {
		Agent agent = agentService.getOnlyById(agentId);
		PropertyDetail detail = propertyDetailService.getOnlyById(detailId);
		Image image = imageService.getOnlyById(imageId);
		propertyService.createProperty(agent, detail, image, propertyRequest);
		REResponse response = new REResponse(ResponseMessage.PROPERTY_CREATED_RESPONSE_MESSAGE, true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> updateProperty(@RequestParam("id") Long id,@RequestParam("agentId") Long agentId,
			@RequestParam("detailId") Long detailId, @RequestParam("imageId") String imageId,
			@Valid @RequestBody PropertyRequest propertyRequest) {
		Agent agent = agentService.getOnlyById(agentId);
		PropertyDetail detail = propertyDetailService.getOnlyById(detailId);
		Image image = imageService.getOnlyById(imageId);
		propertyService.updateProperty(id, agent, detail, image, propertyRequest);
		REResponse response = new REResponse(ResponseMessage.PROPERTY_UPDATED_RESPONSE_MESSAGE, true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/visitors/all")
	public ResponseEntity<List<PropertyDTO>> getAllProperty(){
		List<PropertyDTO> propertyDTOs=propertyService.getAllProperty();
		return ResponseEntity.ok(propertyDTOs);
	}
	
	@GetMapping("/visitors/{id}")
	public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id){
		PropertyDTO propertyDTO=propertyService.findPropertyDTOById(id);
		return ResponseEntity.ok(propertyDTO);
	}
	
	@DeleteMapping("/admin/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteProperty(@PathVariable Long id){
		propertyService.deleteProperty(id);
		REResponse response=new REResponse(ResponseMessage.PROPERTY_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<PropertyDTO>> searchProperties(
            @Or({
                    @Spec(path = "title", params = "title", spec = LikeIgnoreCase.class),
                    @Spec(path = "type", params = "type", spec = LikeIgnoreCase.class),
                    @Spec(path = "status", params = "status", spec = LikeIgnoreCase.class),
                    @Spec(path = "bedrooms", params = "bedrooms", spec = LikeIgnoreCase.class),
                    @Spec(path = "bathrooms", params = "bathrooms", spec = LikeIgnoreCase.class),
                    @Spec(path = "country", params = "country", spec = LikeIgnoreCase.class),
                    @Spec(path = "city", params = "city", spec = LikeIgnoreCase.class),
                    @Spec(path = "district", params = "district", spec = LikeIgnoreCase.class),

            }) @And({
                    @Spec(path = "price", params = "lowPrice", spec = GreaterThanOrEqual.class),
                    @Spec(path = "price", params = "highPrice", spec = LessThanOrEqual.class)
            }) Specification<Property> customerNameSpec) {

		List<PropertyDTO> propertyDTOs= propertyService.searchProperty(customerNameSpec);
        return ResponseEntity.ok(propertyDTOs);
    }
	
	@GetMapping("/{id}/like")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Long> setLikes(@PathVariable Long id){
		User user=userService.getCurrentUser();
		Long setLikes=propertyService.setLike(id,user);
		return ResponseEntity.ok(setLikes);
	}
	
	@GetMapping("/visitors/category")
	public ResponseEntity<List<PropertyDTO>> getAllPropertyWithCategory(@RequestParam("category") PropertyCategory category){
		List<PropertyDTO> propertyDTOs=propertyService.findAllPropertyWithCategory(category);
		return ResponseEntity.ok(propertyDTOs);
	}
	
	
}
