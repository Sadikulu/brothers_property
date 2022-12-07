package com.realestate.dto;

import java.util.Set;

import com.realestate.domain.Property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AgentDTO {

    private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String phone;
	
	private String email;
	
	private String agentImage;
	
	private Set<Property> properties;
}
