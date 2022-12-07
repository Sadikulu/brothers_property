package com.realestate.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.realestate.domain.Agent;
import com.realestate.domain.AgentImage;
import com.realestate.dto.AgentDTO;
import com.realestate.dto.request.AgentRequest;

@Mapper(componentModel = "spring")
public interface AgentMapper {

	@Mapping(target = "id",ignore = true)
	@Mapping(target = "image",ignore = true)
	@Mapping(target = "properties",ignore = true)
	Agent agentRequestToAgent(AgentRequest agentRequest);
	
	List<AgentDTO> map(List<Agent> agentList);
	
	@Mapping(source = "image",target = "agentImage",qualifiedByName = "getImageAsString")
	AgentDTO agentToAgentDTO(Agent agent);
	
	@Named("getImageAsString")
	public static String getImageAsString(AgentImage image) {
		String img=image.getId().toString();
		return img;
	}
}
