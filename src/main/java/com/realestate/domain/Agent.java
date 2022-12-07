package com.realestate.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 30,nullable = false)
	private String firstName;
	
	@Column(length = 30,nullable = false)
	private String lastName;
	
	@Column(length = 14,nullable = false)
	private String phone;
	
	@Email
	@Column(length = 80,unique = true,nullable = false)
	private String email;
	
	@OneToOne
	@JoinColumn(name = "agentImage_id")
	private AgentImage image;
	
	@OneToMany(mappedBy = "agent",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Property> properties=new HashSet<>();
}
