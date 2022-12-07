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
public class AgentRequest {

	@Size(max = 30)
	@NotBlank(message = "Please provide your first name")
    private String firstName;
	
	@Size(max = 30)
    @NotBlank(message = "Please provide your last name")
	private String lastName;
	
	@Size(min = 14, max = 14)
    @NotBlank(message = "Please provide your phone number")
	private String phone;
	
	@Email(message = "Please provide your email")
	private String email;
}
