package com.fdmgroup.skillify.repository;

import java.util.List;
import java.util.UUID;

import com.fdmgroup.skillify.enums.ProficiencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID>{

	/**
	 * 
	 * @param name String
	 * @return skill count
	 */
	@Query("SELECT COUNT(s.name) FROM Skill s WHERE s.name = :name")
	int countSkillByName(@Param("name") String name);
	
	/**
	 * 
	 * @param id Trainee id
	 * @return All skills a trainee has
	 */
	@Query("SELECT skill.name FROM TraineeUser u JOIN u.skills skill WHERE u.id = :id")
	List<String> findSkillNamesBYTrainee(@Param("id") UUID id);
	
	/**
	 * 
	 * @param id Trainee id
	 * @return All skills a trainee has
	 */
	@Query("SELECT skill FROM TraineeUser u JOIN u.skills skill WHERE u.id = :id")
	List<Skill> findSkillFullInfoByTrainee(@Param("id") UUID id);
	
	/**
	 * 
	 * @param id placement id
	 * @return All skills a placement requires
	 */
	@Query("SELECT skill.name FROM Placement p JOIN p.skills skill WHERE p.id = :id")
	List<String> findSkillNamesBYPlacement(@Param("id") UUID id);

	@Query("SELECT s FROM Skill s WHERE s.id NOT IN (SELECT skill.id FROM TechQuiz q JOIN q.skill skill)")
    List<Skill> findSkillsNotInAnyQuiz();

	Skill findByNameAndProficiency(@NonNull String name, @NonNull ProficiencyLevel proficiency);
	
	/**
	 * Search skills
	 * @param keyword SearchTerm
	 * @return A list of Placements contain the SearchTerm in their title, company name
	 */
	@Query("FROM Skill s WHERE s.name LIKE CONCAT('%', :keyword, '%')")
	List<Skill> filterBySkillName(@Param("keyword") String keyword);

}
