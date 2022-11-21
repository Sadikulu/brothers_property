package com.realestate.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.realestate.domain.enums.PropertyStatus;
import com.realestate.domain.enums.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_property")
public class Property {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 5,max = 200,message = "Title '${validatedValue}' must be between {min} and {max} chars long")
    @NotNull(message = "Please provide estate's title")
    @Column(length = 200,nullable = false)
	private String title;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Enumerated(EnumType.STRING)
	private PropertyType type;
	
	private Integer bedrooms;
	
	private Integer bathrooms;
	
	private Integer garages;
	
	private Integer area;
	
	private BigDecimal price; 
	
	private String location;
	
	private String adress;
	
	private String country;
	
	private String city;
	
	private String district;
	
	private LocalDateTime localDateTime=LocalDateTime.now();
	
	private Integer likes;
	
	private Integer visitCount;
	
	@Enumerated(EnumType.STRING)
	private PropertyStatus status;
	
	
	
	
	
	
	
	
	
	

}
