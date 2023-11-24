package com.fdmgroup.skillify.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.Question;
import com.fdmgroup.skillify.entity.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

	/**
	 * Get all questions for a quiz
	 * @param id quiz id
	 * @return A list of question belongs to the quiz
	 */
	@Query("FROM Question q WHERE q.quiz.id = :id")
	List<Question> findByQuizId(@Param("id") UUID id);
	

}
	

