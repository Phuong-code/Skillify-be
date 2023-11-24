package com.fdmgroup.skillify.controller;

import java.util.List;
import java.util.UUID;

import com.fdmgroup.skillify.dto.question.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.skillify.dto.question.McqDto;
import com.fdmgroup.skillify.dto.question.SaqDto;
import com.fdmgroup.skillify.entity.Question;
import com.fdmgroup.skillify.service.QuestionService;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@GetMapping
	public ResponseEntity<List<Question>> getAllQuestions() {
		List<Question> questions = questionService.getAllQuestion(); 
		return ResponseEntity.ok(questions);
	}

	@PostMapping
	public ResponseEntity<List<QuestionDto>> createQuestions(@RequestBody List<QuestionDto> questionDtos) {
		List<QuestionDto> questions = questionService.createQuestions(questionDtos);
		return ResponseEntity.ok(questions);
	}

	//Create MCQ Question
	@PostMapping("/mcq-question")
	public ResponseEntity<Void> createMCQ(@RequestBody McqDto mcqDto) {
		questionService.createMCQ(mcqDto);
			    
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	//Create Short answer Question
	@PostMapping("/shortanswer-question")
	public ResponseEntity<Void> createSAQ(@RequestBody SaqDto saqDto) {
		questionService.createSaq(saqDto);
			    
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/shortanswer-questions")
	public ResponseEntity<Void> createSAQs(@RequestBody List<SaqDto> saqDtos) {
		questionService.createMultipleSaqs(saqDtos);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * Delete a question
	 * @param id question id
	 * @return
	 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
    	Question existingQuestion = questionService.getQuestion(id);
        if (existingQuestion != null) {
        	questionService.deleteQuestion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}



