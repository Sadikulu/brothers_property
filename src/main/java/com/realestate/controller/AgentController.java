package com.realestate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.dto.AgentDTO;
import com.realestate.dto.request.AgentRequest;
import com.realestate.dto.response.REResponse;
import com.realestate.dto.response.ResponseMessage;
import com.realestate.service.AgentService;

@RestController
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	private AgentService agentService;
	
	@PostMapping("admin/{imageId}/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> create(@PathVariable String imageId,@RequestBody AgentRequest agentRequest){
		agentService.createAgent(agentRequest,imageId);
		REResponse response=new REResponse(ResponseMessage.AGENT_SAVED_RESPONSE_MESSAGE,true);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AgentDTO>> getAllAgent(){
		List<AgentDTO> agentDTOList= agentService.getAllAgent();
		return ResponseEntity.ok(agentDTOList);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AgentDTO> getAgent(@PathVariable Long id){
		AgentDTO agentDTO=agentService.getById(id);
		return ResponseEntity.ok(agentDTO);
	}
	
	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> updateAgent(@RequestParam("id") Long id,
			                                      @RequestParam("propertyId") Long propertyId,
			                                      @RequestParam("imageId") String imageId,
			                                      @Valid @RequestParam AgentRequest agentRequest){
		agentService.updateAgent(id,imageId,propertyId,agentRequest);
		REResponse response=new REResponse(ResponseMessage.AGENT_UPDATED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/admin/{id}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<REResponse> deleteAgent(@PathVariable Long id){
		agentService.deleteAgent(id);
		REResponse response=new REResponse(ResponseMessage.AGENT_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}
}
