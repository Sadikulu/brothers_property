package com.realestate.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.domain.enums.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String review;
	
	@Column(nullable = false)
	private LocalDate createDate=LocalDate.now();
	
	private Integer score;
	
	@ManyToOne
	@JoinColumn(name = "propertyId")
	@JsonIgnore
	private Property property;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30,nullable = false)
	private ReviewStatus status=ReviewStatus.PENDING;
}
