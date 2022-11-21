package com.realestate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_cmessage")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2,max = 50,message = "Your name '${validatedValue}' must be between {min} and {max} chars long")
	@NotNull(message = "Please provide your name")
	@Column(length = 50,nullable = false)
	private String name;
	
	@Size(min = 5,max = 50,message = "Your message subject '${validatedValue}' must be between {min} and {max} chars long")
	@NotNull(message = "Please provide your message subject")
	@Column(length = 50,nullable = false)
	private String subject;
	
	@Size(min = 5,max = 200,message = "Your message body '${validatedValue}' must be between {min} and {max} chars long")
	@NotNull(message = "Please provide your message body")
	@Column(length = 200,nullable = false)
	private String body;
	
	@Email(message = "Provide valid email")
	@Column(length = 50,nullable = false)
	private String email;

}
