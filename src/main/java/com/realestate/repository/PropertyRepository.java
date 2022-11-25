package com.realestate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Property;
import com.realestate.domain.enums.PropertyCategory;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long>{

	@EntityGraph(attributePaths = "id")
	Optional<Property> findById(Long id);

	@EntityGraph(attributePaths = {"agent","agent.image"})
	Optional<Property> findPropertyById(Long id);

	@Query( "SELECT count(*) from Property p join p.image img where img.id=:id")
	Integer findPropertyCountByImageId(@Param("id") String id);
	
	@Query("Select p from Property p join p.image img where img.id=:id")
	List<Property> findPropertiesByImageId(@Param("id") String id);
	
//	@Query("Select image from Property p where p.id=:id")
//	List<Image> findImageByPropertyId(@Param("id") Long id);
	
	
	@EntityGraph(attributePaths = {"agent","agent.image"})
	List<Property> findAll();

	@Query("Select p from Property p where p.category=:category")
	List<Property> findAllByCategory(@Param("category") PropertyCategory category);

	@EntityGraph(attributePaths = {"agent","agent.image"})
	List<Property> findAll(Specification<Property> customerNameSpec);
	
}
