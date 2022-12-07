package com.realestate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Agent;
import com.realestate.domain.Property;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>{

	@Query("select count(*) from Agent a join a.image img where img.id=:id")
	Integer findAgentCountByImageId(@Param("id") String id);
	
	@EntityGraph(attributePaths = {"image","properties"})
	List<Agent> findAll();
	
	@EntityGraph(attributePaths = {"image","properties","properties.image"})
	Optional<Agent> findById(Long id);
	
	@Query("select a from Agent a join a.image img where img.id=:id")
	List<Agent> findAgentsByImageId(@Param("id") String id);
	
	@Query("select a from Agent a join a.properties pro where pro.id=:id")
	List<Property> findAgentsByPropertyId(@Param("id") Long id);
	
	@EntityGraph(attributePaths = "image")
	Optional<Agent> findAgentById(Long id);
}
