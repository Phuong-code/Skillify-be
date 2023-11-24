package com.fdmgroup.skillify.repository;

import java.util.List;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.Placement;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, UUID> {
	

	
	/**
	 * Search none-expired placement
	 * @param keyword SearchTerm
	 * @return A list of Placements contain the SearchTerm in their title, company name
	 */
	@Query("FROM Placement p JOIN p.skills skill WHERE ((skill.name LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (p.companyName LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (p.title LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (p.author.firstName LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (p.author.lastName LIKE CONCAT('%', :keyword, '%')))"
			+ " AND p.expiredDate >= DATE(now())")
	List<Placement> filterByAuthorOrTitleOrCompanyOrSkill(@Param("keyword") String keyword);

	List<Placement> findByAuthor_Email(@NonNull String email);
	
	@Query("FROM Placement p WHERE p.expiredDate >= DATE(now())")
	List<Placement> findAllNonExpiredPlacement();


}

	

	
	


