package com.fdmgroup.skillify.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.enums.StatusType;


@Repository
public interface TraineeUserRepository extends AppUserRepository{
	
	 @Query("SELECT t FROM TraineeUser t JOIN AppUser u ON t.id = u.id WHERE u.email = :email")
	    TraineeUser findTraineeUserByEmail(@Param("email") String email);

	/**
	 * 
	 * @param status StatusType
	 * @return A list of TraineeUser objects by given status
	 */
	@Query("FROM TraineeUser u WHERE u.status = :status")
	List<TraineeUser> findTraineeUserByStatus(@Param("status") StatusType status);

	/**
	 * 
	 * @param skill_id UUID
	 * @return A list of TraineeUser objects by given skill id
	 */
	@Query("SELECT u FROM TraineeUser u JOIN u.skills skill WHERE skill.id = :skill_id")
	List<TraineeUser> findTraineeUsersBySkills(@Param("skill_id") UUID skill_id);
}
