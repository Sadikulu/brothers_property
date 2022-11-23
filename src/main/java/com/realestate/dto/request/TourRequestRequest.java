package com.realestate.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourRequestRequest {
	
	@NotNull(message = "Please provide Date and time")
	private LocalDateTime dateTime;
	
	@NotNull(message = "Please provide Adult")
	private Integer adult;
	
	@NotNull(message = "Please provide Child")
	private Integer child;
	
	
}
