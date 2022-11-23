package com.realestate.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestate.domain.Property;
import com.realestate.domain.TourRequest;
import com.realestate.domain.User;
import com.realestate.domain.enums.TourRequestStatus;

@Repository
public interface TourRequestRepository extends JpaRepository<TourRequest, Long>{

	@EntityGraph(attributePaths = {"property","property.image","user"})
	Optional<TourRequest> findById(Long id);

	@EntityGraph(attributePaths = {"property","property.image","user"})
	Optional<TourRequest> findByIdAndUserId(Long id, Long userId);

	@Query("select tr from TourRequest tr "
			+ "join fetch Property p on tr.property=p.id where "
			+ "p.id=:propertyId and (tr.status not in :status) and tr.dateTime=:dateTime")
	List<TourRequest> checkPropertyStatus(@Param("propertyId") Long propertyId, 
			                              @Param("dateTime") LocalDateTime dateTime,
			                              @Param("status") TourRequestStatus[] status);

	@EntityGraph(attributePaths = {"property","property.image","user"})
	List<TourRequest> findAllBy();

	@EntityGraph(attributePaths = {"property","property.image","user"})
	List<TourRequest> findAllByUserId(User user);

	boolean existsByProperty(Property property);

	boolean existsByUser(User user);
	
}
