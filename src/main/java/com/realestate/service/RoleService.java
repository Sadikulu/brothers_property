package com.realestate.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.domain.Role;
import com.realestate.domain.enums.RoleType;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.repository.RoleRepository;

@Service
public class RoleService {
	
	private RoleRepository roleRepository;
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}
	
	public Role findByType(RoleType roleType) {
	Role role=roleRepository.findByType(roleType).orElseThrow(()->
		new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,roleType.name())));
	return role;
	}

}
