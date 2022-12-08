package com.realestate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("select r from Review r join r.property p where p.id=:id")
	List<Review> findAllByPropertyId(@Param("id") Long id);
	
	@EntityGraph(attributePaths = {"user","property","property.image"})
	Optional<Review> findById(Long id);
	
	@Query("select r from Review r join r.user u where u.id=:id")
	List<Review> findAllByUserId(@Param("id") Long id);
	
	@Query("select r from Review r join r.user u where r.id=:id and u.id=:userId")
	Optional<Review> findByUserId(@Param("id") Long id,
			                      @Param("userId") Long userId);
	
	@EntityGraph(attributePaths = {"user","property","property.image"})
	List<Review> findAll();
}
