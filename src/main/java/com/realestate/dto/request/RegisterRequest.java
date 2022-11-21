package com.realestate.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@Size(max=50)
	@NotBlank(message = "Please provide your first name")
	@NotNull(message = "Please provide your first name")
	private String firstName;
	@Size(max=50)
	@NotBlank(message = "Please provide your last name")
	@NotNull(message = "Please provide your last name")
	private String lastName;
	@Size(max=50)
	@NotBlank(message = "Please provide your address")
	@NotNull(message = "Please provide your address")
	private String address;
	@Size(max=50)
	@NotBlank(message = "Please provide your email")
	@Email(message="Provide valid email")
	@NotNull(message = "Please provide your email")
	private String email;
	@Size(max=50)
	@NotBlank(message = "Please provide your phone number")
	@NotNull(message = "Please provide your phone number")
	private String phoneNumber;
	@Size(max=50)
	@NotBlank(message = "Please provide your password")
	@NotNull(message = "Please provide your password")
	private String password;
	@Size(max=50)
	@NotBlank(message = "Please provide your zipcode")
	@NotNull(message = "Please provide your zipcode")
	private String zipcode;
	


	
}
