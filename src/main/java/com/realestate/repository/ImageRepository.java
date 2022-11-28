package com.realestate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>{

	@EntityGraph(attributePaths = "id")
	List<Image> findAll();

	@EntityGraph(attributePaths = "id")
	Optional<Image> findById(String id);
}
