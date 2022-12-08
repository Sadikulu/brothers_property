package com.realestate.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.domain.enums.PropertyCategory;
import com.realestate.domain.enums.PropertyStatus;
import com.realestate.domain.enums.PropertyType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Column(nullable = false,length = 200)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,length = 30)
	private PropertyCategory category;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,length = 30)
	private PropertyType type;
	
	@Column(nullable = false,length = 10)
	private Integer bedrooms;
	
	@Column(nullable = false,length = 10)
	private Integer bathrooms;
	
	@Column(length = 10)
	private Integer garages;
	
	@Column(nullable = false)
	private Integer area;
	
	@Column(nullable = false)
	private Integer price;
	
	@Column(nullable = false,length = 100)
	private String location;
	
	@Column(nullable = false,length = 100)
	private String adress;
	
	@Column(nullable = false,length = 50)
	private String country;
	
	@Column(nullable = false,length = 50)
	private String city;
	
	@Column(nullable = false,length = 50)
	private String district;
	
	@ManyToOne
	@JoinColumn(name = "agentId")
	@JsonIgnore
	private Agent agent;
	
	@Setter(value = AccessLevel.NONE)
	private LocalDateTime createDate=LocalDateTime.now();
	
	private Long likes=0L;
	
	private Long visitCount=0L;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,length = 30)
	private PropertyStatus status;
	
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "propertyId")
	private Set<Image> image;
	
	@OneToMany(mappedBy = "property",orphanRemoval = true)
	@JsonIgnore
	private Set<Review> review=new HashSet<>();
	
	@OneToMany(mappedBy = "property",orphanRemoval = true)
	@JsonIgnore
	private List<TourRequest> tourRequest=new ArrayList<>();

	@ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id"))
    private Set<PropertyDetail> propertyDetails;
}
