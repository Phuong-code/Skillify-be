package com.fdmgroup.skillify.controller;

import com.fdmgroup.skillify.dto.placement.SearchPlacementDto;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skill")
public class SkillController {
	
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill skillResponse = skillService.saveSkill(skill);
        return new ResponseEntity<>(skillResponse, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSkill(@PathVariable UUID id, @RequestBody Skill skill) {
        Skill existingSkill = skillService.getSkillById(id);
        if (existingSkill != null) {
            skill.setId(id);
            skillService.updateSkill(skill);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable UUID id) {
        Skill existingSkill = skillService.getSkillById(id);
        if (existingSkill != null) {
            skillService.deleteSkillById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteAllSkills() {
        int deletedCount = skillService.deleteAllSkill();
        if (deletedCount > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all skills that are not in any quiz
     * @return
     */
    @GetMapping("/not-in-any-quiz")
    public ResponseEntity<List<Skill>> getSkillsNotInQuiz() {
        List<Skill> skills = skillService.getSkillsNotInAnyQuiz();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Skill>>searchPlacement(@RequestParam("keyword") String keyword){
    	
    	return ResponseEntity.ok(skillService.searchSkill(keyword));
    }

}
