package com.fdmgroup.skillify.service;

import com.fdmgroup.skillify.dto.quiz.TechQuizCreationDto;

import com.fdmgroup.skillify.dto.quiz.TechQuizPublicDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.entity.TechQuiz;
import com.fdmgroup.skillify.repository.QuizRepository;
import com.fdmgroup.skillify.repository.TechQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing Tech Quiz related operations.
 */
@Service
public class TechQuizService {
    private final TechQuizRepository techQuizRepository;
    private final QuizRepository quizRepository;
    private final AppUserService appUserService;
    private final SkillService skillService;


    @Autowired
    public TechQuizService(TechQuizRepository techQuizRepository, AppUserService appUserService, 
			SkillService skillService, QuizRepository quizRepository) {
        this.techQuizRepository = techQuizRepository;
		this.quizRepository = quizRepository;
        this.appUserService = appUserService;
        this.skillService = skillService;
    }
    
    /**
     * Creates a new TechQuiz based on the provided TechQuizCreationDto.
     *
     * @param techQuizCreationDto The DTO containing the details for creating a new TechQuiz.
     * @return The created TechQuizPublicDto containing essential details of the created quiz.
     * @throws IllegalArgumentException If the author ID or skill ID is invalid.
     */
    public TechQuizPublicDto createTechQuiz(TechQuizCreationDto techQuizCreationDto) {
        AppUser author = appUserService.getUserById(techQuizCreationDto.getAuthorId());
        if (author == null) {
            throw new IllegalArgumentException("Invalid author ID.");
        }
                
        Skill skill = skillService.getSkillById(techQuizCreationDto.getSkillId());
        if (skill == null) {
            throw new IllegalArgumentException("Invalid author ID.");
        }
        
        TechQuiz techQuiz = new TechQuiz();
        techQuiz.setType(techQuizCreationDto.getType());
        techQuiz.setTimeLimit(techQuizCreationDto.getTimeLimit());
        techQuiz.setTotalMark(techQuizCreationDto.getTotalMark());
        techQuiz.setAuthor(author);
        techQuiz.setSkill(skill);
        quizRepository.save(techQuiz);

        TechQuizPublicDto techQuizPublicDto = new TechQuizPublicDto();
        techQuizPublicDto.setAuthorId(techQuiz.getAuthor().getId());
        techQuizPublicDto.setSkillId(techQuiz.getSkill().getId());
        techQuizPublicDto.setTimeLimit(techQuiz.getTimeLimit());
        techQuizPublicDto.setTotalMark(techQuiz.getTotalMark());
        techQuizPublicDto.setType(techQuiz.getType());
        techQuizPublicDto.setId(techQuiz.getId());
        techQuizPublicDto.setSkillName(techQuiz.getSkill().getName());
        techQuizPublicDto.setProficiency(techQuiz.getSkill().getProficiency());
        return techQuizPublicDto;
    }

    /**
     * Retrieves a list of all Tech Quizzes.
     *
     * @return A list of TechQuiz objects representing all available tech quizzes.
     */
    public List<TechQuiz> getAllTechQuizzes() {
        return techQuizRepository.findAll();
    }

    /**
     * Retrieves a TechQuiz by its ID.
     *
     * @param id The ID of the TechQuiz to retrieve.
     * @return An Optional containing the retrieved TechQuiz, if found.
     */
    public Optional<TechQuiz> getTechQuizById(UUID id) {
        return techQuizRepository.findById(id);
    }

    /**
     * Saves a TechQuiz in the repository.
     *
     * @param techQuiz The TechQuiz object to be saved.
     * @return The saved TechQuiz object.
     */
    public TechQuiz saveTechQuiz(TechQuiz techQuiz) {
        return techQuizRepository.save(techQuiz);
    }

    /**
     * Deletes a TechQuiz by its ID.
     *
     * @param id The ID of the TechQuiz to be deleted.
     */
    public void deleteTechQuiz(UUID id) {
        techQuizRepository.deleteById(id);
    }
    
    /**
     * Searches for Tech Quizzes based on a keyword within their associated skills.
     *
     * @param keyword The search term to filter Tech Quizzes by skill.
     * @return A list of TechQuizCreationDto objects containing filtered Tech Quiz details.
     */
    public List<TechQuizCreationDto> searchTechQuizBySkill(String keyword){
    	
    	List<TechQuiz> techQuizList = techQuizRepository.filterBySkill(keyword);
    	List<TechQuizCreationDto> techQuizDtoList = new ArrayList<TechQuizCreationDto>();
    	
    	for (TechQuiz techQuiz : techQuizList) {
    		TechQuizCreationDto techQuizDto = new TechQuizCreationDto();
    		techQuizDto.setId(techQuiz.getId());
    		techQuizDto.setAuthorId(techQuiz.getAuthor().getId());
    		techQuizDto.setSkillId(techQuiz.getSkill().getId());
    		techQuizDto.setTimeLimit(techQuiz.getTimeLimit());
    		techQuizDto.setTotalMark(techQuiz.getTotalMark());
    		techQuizDto.setType(techQuiz.getType());
    		techQuizDto.setSkillName(techQuiz.getSkill().getName());
    		techQuizDto.setSkillProficiency(techQuiz.getSkill().getProficiency());
    		techQuizDtoList.add(techQuizDto);
    	}
    	return techQuizDtoList;
    }
}
