package com.realestate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.domain.Agent;
import com.realestate.domain.AgentImage;
import com.realestate.domain.Property;
import com.realestate.dto.AgentDTO;
import com.realestate.dto.request.AgentRequest;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.mapper.AgentMapper;
import com.realestate.repository.AgentRepository;

@Service
public class AgentService {

	private AgentRepository agentRepository;
	private AgentImageService agentImageService;
	private PropertyService propertyService;
	private AgentMapper agentMapper;
	
	@Autowired
	public AgentService(AgentRepository agentRepository, AgentImageService agentImageService,
			PropertyService propertyService, AgentMapper agentMapper) {
		this.agentRepository = agentRepository;
		this.agentImageService = agentImageService;
		this.propertyService = propertyService;
		this.agentMapper = agentMapper;
	}

	public void createAgent(AgentRequest agentRequest, String imageId) {
		AgentImage image= agentImageService.getImageById(imageId);
		Integer usedAgentCount=agentRepository.findAgentCountByImageId(imageId);
		if (usedAgentCount>0) {
			throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
		}
		Agent agent= agentMapper.agentRequestToAgent(agentRequest);
		agent.setImage(image);
		agentRepository.save(agent);
	}

	public List<AgentDTO> getAllAgent() {
		List<Agent> agentList= agentRepository.findAll();
		List<AgentDTO> agentDTOList= agentMapper.map(agentList);
		return agentDTOList;
	}

	public AgentDTO getById(Long id) {
		Agent agent=getAgentById(id);
		AgentDTO agentDTO=agentMapper.agentToAgentDTO(agent);
		return agentDTO;
	}
	
	public Agent getAgentById(Long id) {
		Agent agent= agentRepository.findById(id).orElseThrow(()->
		    new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
		return agent;
	}

	public void updateAgent(Long id, String imageId, Long propertyId,AgentRequest agentRequest) {
		Agent agent=getAgentById(id);
		
		AgentImage image=agentImageService.getImageById(imageId);
		List<Agent> agentList=agentRepository.findAgentsByImageId(image.getId());
		for (Agent a : agentList) {
			if (agent.getId().longValue()!=a.getId().longValue()) {
				throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
			}
		}
		
		Property property=propertyService.getPropertyById(propertyId);
		List<Property> propertyList=agentRepository.findAgentsByPropertyId(property.getId());
		for (Property p : propertyList) {
			if (property.getId().longValue()!=p.getId().longValue()) {
				throw new ConflictException(ErrorMessage.PROPERTY_USED_MESSAGE);
			}
		}
		
		agent.setFirstName(agentRequest.getFirstName());
		agent.setLastName(agentRequest.getLastName());
		agent.setEmail(agentRequest.getEmail());
		agent.setPhone(agentRequest.getPhone());
		agent.setId(id);
		agent.setImage(image);
		agent.getProperties().add(property);
		
		agentRepository.save(agent);
	}

	public void deleteAgent(Long id) {
		Agent agent=getAgentById(id);
		agentRepository.delete(agent);
		
	}
	
	public Agent getOnlyById(Long id) {
		Agent agent= agentRepository.findAgentById(id).orElseThrow(()->
		    new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
		return agent;
	}
}
