package com.fdmgroup.skillify.repository;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.enums.RoleType;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID>{
    Optional<AppUser> findByEmail(@NonNull String email);
    
	/**
	 * 
	 * @param keyword SearchTerm
	 * @return A list of Users contain the SearchTerm in their First Name or Last Name
	 */
	@Query("FROM AppUser u WHERE (u.firstName LIKE CONCAT('%', :keyword, '%'))"
			+ " OR (u.lastName LIKE CONCAT('%', :keyword, '%'))")
	List<AppUser> filterByUserName(@Param("keyword") String keyword);
	
	/**
	 * 
	 * @param keyword SearchTerm
	 * @return A list of Users contain the SearchTerm in their role
	 */
	@Query("FROM AppUser u WHERE u.role = :keyword")
	List<AppUser> filterByRole(@Param("keyword") RoleType role);
	
	@Query("FROM AppUser u WHERE u.role = 1")
	List<AppUser> getAllTrainees();

	@Query("SELECT u.email FROM AppUser u WHERE u.authenticated = true AND u.role = :role")
	List<String> findAutheticatedTrainerOrSalesEmail(@Param("role") RoleType role);
	
}
