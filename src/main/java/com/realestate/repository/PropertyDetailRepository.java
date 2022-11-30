package com.realestate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.domain.PropertyDetail;

@Repository
public interface PropertyDetailRepository extends JpaRepository<PropertyDetail, Long>{
	
	@EntityGraph(attributePaths = "id")
	Optional<PropertyDetail>  findOnlyById(Long id);
	
}
