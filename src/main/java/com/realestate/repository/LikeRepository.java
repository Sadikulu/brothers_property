package com.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{

	Boolean existsByPropertyIdAndUserId(Long propertyId,Long userId);
	Like findByPropertyIdAndUserId(Long propertyId,Long userId);
	
}
