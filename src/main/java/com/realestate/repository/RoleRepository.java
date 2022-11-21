package com.realestate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Role;
import com.realestate.domain.enums.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByType(RoleType type);
	
}
