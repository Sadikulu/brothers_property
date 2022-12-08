package com.realestate.dto;

import java.util.Date;
import java.util.Set;

import com.realestate.domain.Agent;
import com.realestate.domain.enums.PropertyCategory;
import com.realestate.domain.enums.PropertyStatus;
import com.realestate.domain.enums.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

	private Long id;
	
	private String title;
	
	private String description;
	
	private PropertyCategory category;
	
	private PropertyType type;
	
	private Integer bedrooms;
	
	private Integer bathrooms;
	
	private Integer garages;
	
	private Integer area;
	
	private Integer price;
	
	private String location;
	
	private String adress;
	
	private String country;
	
	private String city;
	
	private String district;
	
	private Date createDate;
	
	private Long likes;
	
	private Long visitCount;

	private PropertyStatus status;
	
	private Set<String> images;
	
	private Set<Long> review;

    private Set<Long> propertyDetail;
    
    private Agent agent;
}


