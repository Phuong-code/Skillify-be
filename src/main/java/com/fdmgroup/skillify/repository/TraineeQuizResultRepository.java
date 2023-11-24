package com.fdmgroup.skillify.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.TraineeQuizResult;
import com.fdmgroup.skillify.entity.UserAnswer;

@Repository
public interface TraineeQuizResultRepository extends JpaRepository<TraineeQuizResult, UUID> {
	
	@Query("SELECT t FROM TraineeQuizResult t WHERE (t.finishedMarking = false) AND (t.quiz.author.email = :email) Order BY t.quiz.id, t.submissionDate")
	List<TraineeQuizResult>findUnmarkedQuizByCurrentUser(@Param("email") String email);

	@Query("SELECT t FROM TraineeQuizResult t WHERE (t.quiz.author.email = :email) Order BY t.quiz.id, t.finishedMarking, t.submissionDate")
	List<TraineeQuizResult>findAllQuizByCurrentUser(@Param("email") String email);
}
