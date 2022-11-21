package com.realestate.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 75, nullable = false)
	private String firstName;
	
	@Column(length = 50, nullable = false)
	private String lastName;
	
	@Column(length = 75, nullable = false,unique = true)
	@Email(message = "Provide valid email")
	private String email;
	
	@Column(length = 75, nullable = false)
	private String phoneNumber;
	
	@Column(length = 75, nullable = false)
	private String password;
	
	@Column(length = 75, nullable = false)
	private String address;
	
	@Column(length = 75, nullable = false)
	private String zipCode;
	
	@Column(length = 75, nullable = false)
	private Boolean builtIn=false;
		
		
	@ManyToMany
	@JoinTable(name = "tbl_user_role",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns= @JoinColumn(name="role_id"))
	private  Set<Role> roles=new HashSet<>();
	

}
