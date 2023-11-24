package com.fdmgroup.skillify.repository;

import com.fdmgroup.skillify.entity.TechQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TechQuizRepository extends JpaRepository<TechQuiz, UUID> {

	/**
	 * 
	 * @param keyword SearchTerm
	 * @return A list of Tech Quiz contain the SearchTerm in their skill name
	 */
	@Query("FROM TechQuiz q WHERE q.skill.name LIKE CONCAT('%', :keyword, '%')")
	List<TechQuiz> filterBySkill(@Param("keyword") String keyword);

	List<TechQuiz> findByAuthor_Id(@NonNull UUID id);
}
