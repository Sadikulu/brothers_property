package com.realestate.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	
	@Size(max = 50)
	@NotBlank(message = "Please provide your first name")
	private String firstName;
	
	@Size(max = 50)
	@NotBlank(message = "Please provide your last name")
	private String lastName;
	
	@Size(max = 80)
	@Email(message = "Please provide your email")
	private String email;
	
	@Size(min = 4, max = 120)
	@NotBlank(message = "Please provide your password")
	private String password;
	
	@Size(min = 14, max = 14)
	@NotBlank(message = "Please provide your phone number")
	private String phoneNumber;
	
	@Size(max = 100)
	@NotBlank(message = "Please provide your address")
	private String address;
	
	@Size(max = 15)
	@NotBlank(message = "Please provide your zip code")
	private String zipCode;
}
