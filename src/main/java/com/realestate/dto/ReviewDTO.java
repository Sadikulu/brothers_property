package com.realestate.dto;

import java.time.LocalDate;

import javax.persistence.Lob;

import com.realestate.domain.Property;
import com.realestate.domain.User;
import com.realestate.domain.enums.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

	private Long id;

	@Lob
	private String review;

	private LocalDate createDate;

	private Integer score;

	private Property property;

	private User user;

	private ReviewStatus status;
}
