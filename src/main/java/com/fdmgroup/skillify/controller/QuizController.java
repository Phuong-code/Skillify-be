package com.fdmgroup.skillify.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fdmgroup.skillify.dto.question.QuestionDto;
import com.fdmgroup.skillify.dto.quiz.*;
import com.fdmgroup.skillify.dto.traineeQuizResult.TraineeQuizResultDto;
import com.fdmgroup.skillify.dto.userAnswer.UserAnswerDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.TechQuiz;
import com.fdmgroup.skillify.entity.TraineeQuizResult;
import com.fdmgroup.skillify.enums.QuizType;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;
    private TechQuizService techQuizService;
    private ClientQuizService clientQuizService;
    private QuestionService questionService;
    private AppUserService appUserService;
    private TraineeQuizResultService traineeQuizResultService;

    
    @Autowired
    public QuizController(
            QuizService quizService,
            TechQuizService techQuizService,
            ClientQuizService clientQuizService,
            QuestionService questionService,
            AppUserService appUserService,
            TraineeQuizResultService traineeQuizResultService) {
        this.quizService = quizService;
        this.techQuizService = techQuizService;
        this.clientQuizService = clientQuizService;
        this.questionService = questionService;
        this.appUserService = appUserService;
        this.traineeQuizResultService = traineeQuizResultService;
    }
    
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        System.out.println(quizzes);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
    
    //Create Tech Quiz
    @PostMapping("/tech-quiz")
    public ResponseEntity<TechQuizPublicDto> createTechQuiz(
            @RequestBody TechQuizCreationDto techQuizCreationDto,
            Authentication authentication) {
        String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        techQuizCreationDto.setAuthorId(appUser.getId());
        TechQuizPublicDto techQuizPublicDto = techQuizService.createTechQuiz(techQuizCreationDto);
        return new ResponseEntity<>(techQuizPublicDto, HttpStatus.CREATED);
    }
    
    //Create Client Quiz
    @PostMapping("/client-quiz")
    public ResponseEntity<ClientQuizResponseDto> createClientQuiz(
            @RequestBody ClientQuizCreationDto clientQuizCreationDto,
            Authentication authentication) {
        String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        clientQuizCreationDto.setAuthorId(appUser.getId());
        ClientQuizResponseDto clientQuizResponse = clientQuizService.createClientQuiz(clientQuizCreationDto);
        return new ResponseEntity<>(clientQuizResponse, HttpStatus.CREATED);
    }

    //Get quiz
    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuizById(@PathVariable UUID id) {
        Quiz quiz = quizService.getQuizById(id).get();
        if (quiz.getType().equals(QuizType.SKILL)) {
        	 return new ResponseEntity<>(quizService.convertTechQuizDto(techQuizService.getTechQuizById(quiz.getId()).get()), HttpStatus.OK);
        }else {
        	 return new ResponseEntity<>(quizService.convertClientQuizDto(clientQuizService.getClientQuizById(quiz.getId()).get()), HttpStatus.OK);
        }
    }
    
    @GetMapping("/tech-quiz/search")
    public ResponseEntity<List<TechQuizCreationDto>>searchTechQuiz(@RequestParam("keyword") String keyword){
    	
    	return ResponseEntity.ok(techQuizService.searchTechQuizBySkill(keyword));
    }

    /**
     * Get all quizzes created by the current user
     * @param authentication
     * @return
     */
    @GetMapping("/my")
    public ResponseEntity<List<QuizDto>> getMyQuizzes(Authentication authentication){
    	String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        return ResponseEntity.ok(quizService.getQuizzesByAuthorId(appUser.getId()));
    }
    
    /**
     * 
     * @param id quiz id
     * @return list of dto questions
     */
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(@PathVariable UUID id){
    	
    	return ResponseEntity.ok(questionService.getByQuizId(id));
    }
    
    // Endpoint to submit answers for a quiz
    @PostMapping("/{id}/submit")
    public ResponseEntity<Void> submitQuizAnswers(@PathVariable UUID id, @RequestBody List<UserAnswerDto> answersDto, Authentication authentication) { 	
		quizService.submitQuizUserAnswers(id, answersDto, authentication);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	
    }
    
	/**
	 * Delete a Quiz
	 * @param id Quiz id
	 * @return
	 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable UUID id, Authentication authentication) {
        String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
    	Quiz existingQuiz = quizService.getQuizById(id).get();
        if (existingQuiz != null) {
            if (!existingQuiz.getAuthor().getId().equals(appUser.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            quizService.deleteQuizById(id);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update a quiz general information
     * @param id Quiz id
     * @param quizUpdateDto Quiz update dto
     * @param authentication
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable UUID id, @RequestBody QuizUpdateDto quizUpdateDto, Authentication authentication) {
        String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        Quiz existingQuiz = quizService.getQuizById(id).get();
        if (existingQuiz != null) {
            if (!existingQuiz.getAuthor().getId().equals(appUser.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            QuizDto updatedQuiz = quizService.updateQuizById(id, quizUpdateDto);
            return ResponseEntity.ok(updatedQuiz);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update a quiz questions
     * @param id
     * @param questionsDto
     * @param authentication
     * @return
     */
    @PatchMapping("/{id}/questions")
    public ResponseEntity<Void> updateQuizQuestions(@PathVariable UUID id, @RequestBody List<QuestionDto> questionsDto, Authentication authentication) {
        String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        Quiz existingQuiz = quizService.getQuizById(id).get();
        if (existingQuiz != null) {
            if (!existingQuiz.getAuthor().getId().equals(appUser.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            questionService.updateQuestions(questionsDto);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/questions")
    public ResponseEntity<Void> deleteQuizQuestions(@PathVariable UUID id, @RequestBody List<UUID> questionIds, Authentication authentication) {
        String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        Quiz existingQuiz = quizService.getQuizById(id).get();
        if (existingQuiz != null) {
            if (!existingQuiz.getAuthor().getId().equals(appUser.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            questionService.deleteQuestions(questionIds);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * 
     * @param authentication
     * @return
     */
    @GetMapping("/unmarked")
    public ResponseEntity<List<TraineeQuizResultDto>> getUnmarkedQuiz(Authentication authentication){
    	String email = authentication.getName();
    	return ResponseEntity.ok(traineeQuizResultService.getUnmarkedTraineeQuizResult(email));
    }

    /**
     *
     * @param authentication
     * @return
     */
    @GetMapping("/all-results")
    public ResponseEntity<List<TraineeQuizResultDto>> getAllMyQuizzes(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(traineeQuizResultService.getAllMyTraineeQuizResults(email));
    }
}
