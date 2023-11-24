package com.fdmgroup.skillify.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.UserAnswer;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, UUID> {
	
	@Query("FROM UserAnswer u WHERE result.id = :id")
	List<UserAnswer> findUserAnswerByTraineeQuizResultId(@Param("id") UUID id);

}
