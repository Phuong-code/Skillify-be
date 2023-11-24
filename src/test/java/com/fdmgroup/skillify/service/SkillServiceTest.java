package com.fdmgroup.skillify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.enums.ProficiencyLevel;
import com.fdmgroup.skillify.repository.SkillRepository;

@SpringBootTest
class SkillServiceTest {

	@MockBean
	SkillRepository skillRepository;
	@Autowired
	SkillService skillService;

	@BeforeEach
	void setUp() throws Exception {
	}
	
	
	

	@Test
    public void testSaveSkill() {
        // Test data
        Skill skillToSave = new Skill();
        skillToSave.setName("Java");
       
        Skill savedSkill = new Skill();
     
        
        when(skillRepository.save(skillToSave)).thenReturn(savedSkill);


        Skill result = skillService.saveSkill(skillToSave);

        
        verify(skillRepository, times(1)).save(skillToSave);

   
        assertNotNull(result);
    } 
	
	  @Test
	    public void testSaveSkill_DuplicateSkill() {
	        Skill existingSkill = new Skill();
	        existingSkill.setName("Programming");
	        existingSkill.setProficiency(ProficiencyLevel.BEGINNER);

	        // Mocking the behavior of the repository to return an existing skill
	        when(skillRepository.findByNameAndProficiency("Programming", ProficiencyLevel.BEGINNER)).thenReturn(existingSkill);

	        Skill savedSkill = skillService.saveSkill(existingSkill);

	        assertNull(savedSkill); // Assert that null is returned for duplicate skill

	        verify(skillRepository, times(1)).findByNameAndProficiency("Programming", ProficiencyLevel.BEGINNER);
	    }
	

	

	@Test
	public void testGetAllSkills() {
		List<Skill> mockSkills = new ArrayList<>();
		
		mockSkills.add(new Skill());
		mockSkills.add(new Skill());
		
		when(skillRepository.findAll()).thenReturn(mockSkills);
		
		List<Skill> result = skillService.getAllSkills();
		
		verify(skillRepository, times(1)).findAll();
		assertNotNull(result);
		assertEquals(mockSkills.size(), result.size());
	}
	
	
	@Test
	public void testGetSkillById() {
		UUID skillId = UUID.randomUUID();
		Skill mockSkill = new Skill();
		mockSkill.setId(skillId);
		
		when(skillRepository.findById(skillId)).thenReturn(Optional.of(mockSkill));
		
		Skill result = skillService.getSkillById(skillId);
		
		verify(skillRepository, times(1)).findById(skillId);
		
		assertNotNull(result);
		assertEquals(skillId, result.getId());
	} 

	@Test
	public void testGetSkillById_notExist() {
		UUID skillId = UUID.randomUUID();
	
		
		when(skillRepository.findById(skillId)).thenReturn(Optional.empty());
		
		Skill result = skillService.getSkillById(skillId);
		
		verify(skillRepository, times(1)).findById(skillId);
		
		assertNull(result);
	}
	
	
	@Test
	public void testDeleteSkill() {
		UUID skillId = UUID.randomUUID();
		
		skillService.deleteSkillById(skillId);
		verify(skillRepository, times(1)).deleteById(skillId);
	}
	
	  @Test
	    public void testDeleteAllSkill() {
		  
		  
	        List<Skill> mockSkills = new ArrayList<>();
	      
	        mockSkills.add(new Skill());
	        mockSkills.add(new Skill());
	        
	        when(skillRepository.findAll()).thenReturn(mockSkills);

	        int count = skillService.deleteAllSkill();

	        verify(skillRepository, times(1)).findAll();

	        assertEquals(2, count);
	    }
	@Test
    public void testUpdateSkill() {
		Skill skill = new Skill();
		
		skillService.updateSkill(skill);
		
		verify(skillRepository).save(skill);
    
    }
    @Test
    public void testGetSkillNamesByTrainee() {
       
        UUID traineeId = UUID.randomUUID();

   
        List<String> mockSkillNames = new ArrayList<>();
        when(skillRepository.findSkillNamesBYTrainee(traineeId)).thenReturn(mockSkillNames);

   
        List<String> result = skillService.getSkillNamesByTrainee(traineeId);


        verify(skillRepository, times(1)).findSkillNamesBYTrainee(traineeId);

        assertNotNull(result);
        assertEquals(mockSkillNames, result);
    }
    
    @Test
    public void testGetSkillNamesByPlacement() {
    
        UUID placementId = UUID.randomUUID();

      
        List<String> mockSkillNames = new ArrayList<>();
        when(skillRepository.findSkillNamesBYPlacement(placementId)).thenReturn(mockSkillNames);

        List<String> result = skillService.getSkillNamesByPlacement(placementId);

 
        verify(skillRepository, times(1)).findSkillNamesBYPlacement(placementId);

        assertNotNull(result);
        assertEquals(mockSkillNames, result);
    }
    
    @Test
    public void testGetSkillsNotInAnyQuiz() {
        Skill skill1 = new Skill();
        skill1.setId(UUID.randomUUID());
        skill1.setName("Programming");

        Skill skill2 = new Skill();
        skill2.setId(UUID.randomUUID());
        skill2.setName("Communication");

        List<Skill> mockSkills = Arrays.asList(skill1, skill2);

        when(skillRepository.findSkillsNotInAnyQuiz()).thenReturn(mockSkills);

        List<Skill> skills = skillService.getSkillsNotInAnyQuiz();

        assertEquals(2, skills.size());
        assertEquals("Programming", skills.get(0).getName());
        assertEquals("Communication", skills.get(1).getName());

        verify(skillRepository, times(1)).findSkillsNotInAnyQuiz();
    }
    
    @Test
    public void testSearchSkill() {
        Skill skill1 = new Skill();
        skill1.setId(UUID.randomUUID());
        skill1.setName("Programming");

        Skill skill2 = new Skill();
        skill1.setId(UUID.randomUUID());
        skill2.setName("Problem Solving");

        List<Skill> mockSkills = Arrays.asList(skill1, skill2);

        when(skillRepository.filterBySkillName("Pro")).thenReturn(mockSkills);

        List<Skill> skills = skillService.searchSkill("Pro");

        assertEquals(2, skills.size()); // Both skills match the search term "Pro"
        assertEquals("Programming", skills.get(0).getName());
        assertEquals("Problem Solving", skills.get(1).getName());

        verify(skillRepository, times(1)).filterBySkillName("Pro");
    }

}
