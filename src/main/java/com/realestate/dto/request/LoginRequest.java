package com.realestate.dto.request;

import javax.validation.constraints.Email;
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
public class LoginRequest {
	@Email(message = "Please provide your email")
	@NotBlank
	@NotNull
	private String email;
	@NotBlank
	@NotNull
	private String password; 
}
