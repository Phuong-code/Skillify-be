package com.fdmgroup.skillify.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.skillify.dto.userAnswer.UserAnswerDto;

import com.fdmgroup.skillify.entity.UserAnswer;
import com.fdmgroup.skillify.service.QuizService;
import com.fdmgroup.skillify.service.UserAnswerService;

@RestController
@RequestMapping("/api/userAnswer")
public class UserAnswerController {
	
	@Autowired
	UserAnswerService userAnswerService;
	
	@Autowired
	QuizService quizService;
	
	@GetMapping
	public ResponseEntity<List<UserAnswer>> getAllUserAnswer() {
		List<UserAnswer> userAnswers = userAnswerService.getAllUserAnswer();
		return ResponseEntity.ok(userAnswers);
	}
	
	@GetMapping("/{userAnswerId}")
	public ResponseEntity<UserAnswer> getUserAnswerById(@PathVariable UUID userAnswerId) {
		Optional<UserAnswer> userAnswer = userAnswerService.getUserAnswerById(userAnswerId);
		if (userAnswer.isPresent()) {
			return ResponseEntity.ok(userAnswer.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<UserAnswerDto> createUserAnswers(@RequestBody UserAnswerDto userAnswerDto , Authentication authentication) {
		String email = authentication.getName();
		userAnswerDto.setTraineeEmail(email);
		UserAnswerDto userAnswerDtoRepsonse = userAnswerService.saveUserAnswer(userAnswerDto);
		return ResponseEntity.ok(userAnswerDtoRepsonse);
		
	}
	
	@DeleteMapping("/{userAnswerId}")
	  public ResponseEntity<Void> deleteUserAnswer(@PathVariable UUID userAnswerId) {
		userAnswerService.deleteUserAnswer(userAnswerId);
	        return ResponseEntity.noContent().build();
	    }
	
	@GetMapping("/quizResult/{traineeQuizResultId}")
	public ResponseEntity<List<UserAnswerDto>> getUserAnswersByTraineeQuizResult(@PathVariable UUID traineeQuizResultId) {
		List<UserAnswerDto> userAnswers = userAnswerService.getUserAnswerByTraineeQuizResultId(traineeQuizResultId);
			return ResponseEntity.ok(userAnswers);
	}
	
    @PutMapping("/quizResult/{traineeQuizResultId}")
    public ResponseEntity<Void> manualMarkQuiz(@PathVariable UUID traineeQuizResultId, @RequestBody List<UserAnswerDto> userAnswerDtos){
    	quizService.manualMarkQuiz(traineeQuizResultId, userAnswerDtos);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
