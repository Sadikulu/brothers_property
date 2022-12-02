package com.realestate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.domain.AgentImage;

@Repository
public interface AgentImageRepository extends JpaRepository<AgentImage,String>{

	@EntityGraph(attributePaths = "id")
	Optional<AgentImage> findById(String id);
	
}
