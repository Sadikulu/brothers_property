package com.realestate.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "t_agentImage")
public class AgentImage {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	private String name;
	
	private String type;
	
	private long length;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private AgentImageData agentImageData;

	public AgentImage(String name, String type, AgentImageData agentImageData) {
		super();
		this.name = name;
		this.type = type;
		this.agentImageData = agentImageData;
		this.length=agentImageData.getData().length;
	}
	
	
}
