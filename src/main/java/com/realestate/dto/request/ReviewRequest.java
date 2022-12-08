package com.realestate.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

	@NotBlank(message ="Please Provide Your Review" )
	private String review;

	@NotNull(message ="Please Provide Your Score" )
	private Integer score;
	
}
