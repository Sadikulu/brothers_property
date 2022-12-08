package com.realestate.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50,nullable = false)
	private String firstName;
	
	@Column(length = 50,nullable = false)
	private String lastName;
	
	@Column(length = 80,nullable = false,unique = true)
	private String email;
	
	@Column(length = 120,nullable = false)
	private String password;
	
	@Column(length = 14,nullable = false)
	private String phoneNumber;
	
	@Column(length = 100,nullable = false)
	private String address;
	
	@Column(length = 15,nullable = false)
	private String zipCode;
	
	@Column(nullable = false)
	private Boolean builtIn=false;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> review;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<TourRequest> tourRequest;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "t_user_role",
	           joinColumns = @JoinColumn(name="user_id"),
	           inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles=new HashSet<>();
}
