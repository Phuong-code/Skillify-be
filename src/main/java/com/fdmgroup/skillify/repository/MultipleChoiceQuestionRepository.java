package com.fdmgroup.skillify.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.MultipleChoiceQuestion;

@Repository
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, UUID>{

	//@Query("SELECT JSON_EXTRACT(mcq.options,'$.title') FROM MultipleChoiceQuestion mcq WHERE mcq.id = :id AND JSON_EXTRACT(mcq.options, '$.isCorrect') = true")
	@Query(value = "SELECT"
			+ "  jt.title"
			+ "  FROM"
			+ "  multiple_choice_question mcq,"
			+ "  JSON_TABLE(mcq.options, '$[*]' COLUMNS ("
			+ "  title VARCHAR(255) PATH '$.title',"
			+ "  correct BOOLEAN PATH '$.correct')) AS jt"
			+ "  WHERE jt.correct = true AND mcq.id = :id", nativeQuery = true)
	List<String> findCorrectAnswerById(@Param("id") UUID id);
	
}
