package com.realestate.dto;

import java.time.LocalDateTime;

import com.realestate.domain.Property;
import com.realestate.domain.User;
import com.realestate.domain.enums.TourRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourRequestDTO {

	
	private Long id;
	

	private LocalDateTime dateTime;
	
	
	private Integer adult;
	
	
	private Integer child;
	
	
	private User user;
	
	
	private TourRequestStatus status;
	

	private Property property;
	
	
}
