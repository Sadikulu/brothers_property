package com.realestate.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.realestate.domain.enums.PropertyCategory;
import com.realestate.domain.enums.PropertyType;
import com.realestate.domain.enums.PropertyStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequest {
	
	@NotBlank(message = "Please provide estate's title")
	@Size(min = 5,max = 100)
	private String title;
	
	@NotBlank(message = "Please provide estate's description")
	@Size(min = 5,max = 200)
	private String description;
	
	@NotNull(message = "Please provide estate's category")
	private PropertyCategory category;
	
	@NotNull(message = "Please provide estate's type")
	private PropertyType type;
	
	@NotNull(message = "Please provide estate's bedrooms")
	private Integer bedrooms;
	
	@NotNull(message = "Please provide estate's bathrooms")
	private Integer bathrooms;
	
	@NotNull(message = "Please provide estate's garages")
	private Integer garages;
	
	@NotNull(message = "Please provide estate's area")
	private Integer area;
	
	@NotNull(message = "Please provide estate's price")
	private Integer price;
	
	@NotBlank(message = "Please provide estate's location")
	private String location;
	
	@NotBlank(message = "Please provide estate's address")
	@Size(min = 5,max = 100)
	private String adress;
	
	@NotBlank(message = "Please provide estate's country")
	@Size(min = 2,max = 50)
	private String country;
	
	@NotBlank(message = "Please provide estate's city")
	@Size(min = 2,max = 30)
	private String city;
	
	@NotBlank(message = "Please provide estate's district")
	@Size(min = 2,max = 30)
	private String district;

	@NotNull(message = "Please provide estate's status")
	private PropertyStatus status;
	
//	@NotNull(message = "Please provide estate's images")
//	private Set<String> image;
}
