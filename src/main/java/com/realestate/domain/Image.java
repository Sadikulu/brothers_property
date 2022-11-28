package com.realestate.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

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
public class Image {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	private String name;
	
	private String type;
	
	private long length;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private ImageData imageData;
	
	private Boolean featured;

	public Image(String name, String type, ImageData imageData) {
		this.name = name;
		this.type = type;
		this.imageData = imageData;
		this.length=imageData.getData().length;
	}
	
	
}
