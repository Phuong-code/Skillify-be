package com.fdmgroup.skillify.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.dto.placement.SearchPlacementDto;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.repository.SkillRepository;



@Service
public class SkillService {

	@Autowired
	SkillRepository skillRepository;	
	
	/**
	 * save a Skill entity
	 * @param skill 
	 */
	public Skill saveSkill(Skill skill) {
		if (skillRepository.findByNameAndProficiency(skill.getName(), skill.getProficiency()) != null) {
			return null;
		}
		return skillRepository.save(skill);
	}
	
	/**
	 * Update a Skill entity
	 * @param skill
	 */
	public void updateSkill(Skill skill) {
		
		skillRepository.save(skill);
	}
	
	/**
	 * Delete a Skill object by given id
	 * @param id
	 */
	public void deleteSkillById(UUID id) {
		
		skillRepository.deleteById(id);
	}
	
	/**
	 * 
	 * @return A list of all Skill objects
	 */
	public List<Skill> getAllSkills(){
		
		return skillRepository.findAll(); 
	}
	
	/**
	 * 
	 * @param id Skill Id
	 * @return A Skill Object by given id
	 */
	public Skill getSkillById(UUID id) {
		
		Optional<Skill> skill = skillRepository.findById(id);
		
		if(skill.isPresent())			
			return skill.get();
		else 
			return null;
	}
		
	/*
	 * 
	 * Delete All Skill Objects
	 * @return number of objects deleted
	 */
	public int deleteAllSkill() {
		
		int count = 0;
		List<Skill> skills = skillRepository.findAll();
		
		for(Skill skill : skills) {
			skillRepository.delete(skill);
			count++;
		}
		
		return count;
		
	}
	
	/**
	 * 
	 * @param id Trainee id
	 * @return All skills a trainee has
	 */
	public List<String> getSkillNamesByTrainee(UUID id) {
		
		return skillRepository.findSkillNamesBYTrainee(id);
	}
	
	
	/**
	 * 
	 * @param id placement id
	 * @return All skills a placement requires
	 */
	public List<String> getSkillNamesByPlacement(UUID id) {
		
		return skillRepository.findSkillNamesBYPlacement(id);
	}

	/**
	 * Get all skills that are not in any quiz
	 * @return
	 */
    public List<Skill> getSkillsNotInAnyQuiz() {
		return skillRepository.findSkillsNotInAnyQuiz();
    }
    
	/**
	 * 
	 * @param keyword SearchTerm
	 * @return A list of skills contain the SearchTerm in skill name
	 */
	public List<Skill> searchSkill(String keyword){
		return skillRepository.filterBySkillName(keyword);
	}
}
