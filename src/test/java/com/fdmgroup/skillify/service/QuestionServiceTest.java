package com.fdmgroup.skillify.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fdmgroup.skillify.entity.Option;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.entity.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.skillify.dto.placement.PlacementResponseDto;
import com.fdmgroup.skillify.dto.question.McqDto;
import com.fdmgroup.skillify.dto.question.QuestionDto;
import com.fdmgroup.skillify.dto.question.SaqDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.ClientQuiz;
import com.fdmgroup.skillify.entity.MultipleChoiceQuestion;
import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.ShortAnswerQuestion;
import com.fdmgroup.skillify.entity.TechQuiz;
import com.fdmgroup.skillify.enums.QuestionType;
import com.fdmgroup.skillify.enums.QuizType;
import com.fdmgroup.skillify.repository.MultipleChoiceQuestionRepository;
import com.fdmgroup.skillify.repository.QuestionRepository;
import com.fdmgroup.skillify.repository.QuizRepository;
import com.fdmgroup.skillify.repository.ShortAnswerQuestionRepository;

@SpringBootTest
class QuestionServiceTest {
	
	@Autowired
	QuestionService questionService;
	@MockBean
	QuestionRepository questionRepository;
	@MockBean
	Quiz mockTechQuiz;
	
	@MockBean
	QuizRepository quizRepository;
	
	@MockBean
	ShortAnswerQuestionRepository shortAnswerQuestionRepository;
	
	@MockBean
	MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
	
	
	
    MultipleChoiceQuestion mcq;

	
    
    
    
    
	@BeforeEach
	void setup() {
		questionRepository.deleteAll();
		
		mcq = new MultipleChoiceQuestion();
        mcq.setQuestion("What is Java");
        mcq.setQuiz(mockTechQuiz);
        mcq.setMark(70);
        mcq.setType(QuestionType.MULTIPLE_CHOICE);
		Option option1 = new Option("A", "Person", false);
		Option option2 = new Option("B", "Food", false);
		Option option3 = new Option("C", "Programming language", true);
		Option option4 = new Option("D", "Dog", false);
		List<Option> options = new ArrayList<>();
		options.add(option1);
		options.add(option2);
		options.add(option3);
		options.add(option4);
        mcq.setOptions(options); 
	}
	
	@Test
	void delete_mcq() {		
		UUID testId = UUID.randomUUID();
		
		questionService.deleteQuestion(testId);
		
		verify(questionRepository, times(1)).deleteById(testId);
	}
	
	 @Test
	 public void testGetQuestion() {
		 UUID questionId = UUID.randomUUID();
	     Question mockQuestion = new Question() {
	     };
	     when(questionRepository.findById(questionId)).thenReturn(Optional.of(mockQuestion));

	     Question result = questionService.getQuestion(questionId);

	     assertEquals(mockQuestion, result);
	 }
	 
	  @Test
	  public void testDeleteQuestion() {
		  UUID questionId = UUID.randomUUID();

	      questionService.deleteQuestion(questionId);

	      verify(questionRepository, times(1)).deleteById(questionId);
	   }
	  

	  
	  @Test
	    public void testGetAllQuestion() {
	      Question question1 = new Question() {
		};
	      Question question2 = new Question() {
		};
	      List<Question> mockQuestions = Arrays.asList(question1, question2);
	      when(questionRepository.findAll()).thenReturn(mockQuestions);

	      List<Question> result = questionService.getAllQuestion();

	      assertEquals(mockQuestions, result);
	   }
	  
	  @Test
	    public void testGetCorrectMcqAnswer() {
	       UUID mcqId = UUID.randomUUID();
	       List<String> mockAnswers = Arrays.asList("A", "B", "C");
	       when(multipleChoiceQuestionRepository.findCorrectAnswerById(mcqId)).thenReturn(mockAnswers);

	       List<String> result = questionService.getCorrectMcqAnswer(mcqId);

	       assertEquals(mockAnswers, result);
	   }
	  
	    @Test
	    public void testDeleteQuestions() {
	        List<UUID> questionIds = new ArrayList<>();
	        questionIds.add(UUID.randomUUID());
	        questionIds.add(UUID.randomUUID());

	        questionService.deleteQuestions(questionIds);

	        verify(questionRepository, times(1)).deleteAllById(questionIds);
	    }
	    
	    @Test
	    public void testCreateQuestions() {

	        List<QuestionDto> questionDtos = new ArrayList<>();

	        List<Question> questions = new ArrayList<>();
	   
	        when(questionRepository.saveAll(questions)).thenReturn(questions);

	        List<QuestionDto> expectedDtos = new ArrayList<>();
	 
	
	        List<QuestionDto> savedQuestionDtos = questionService.createQuestions(questionDtos);

	        // Assert the results
	        assertEquals(expectedDtos, savedQuestionDtos);
	    }
	    
	    @Test
	    public void testCreateMCQ() {
	        
	        McqDto mcqDto = new McqDto();
	        Quiz quiz = new Quiz() {
			};
	        when(quizRepository.findById(mcqDto.getQuizId())).thenReturn(Optional.of(quiz));

	  
	         questionService.createMCQ(mcqDto);

	   
	        verify(quizRepository, times(1)).findById(mcqDto.getQuizId());
	        

	    }
	    
	    @Test
	    public void testCreateSAQ() {
	        
	        SaqDto saqDto = new SaqDto();
	
	        Quiz quiz = new Quiz() {
			};
	        when(quizRepository.findById(saqDto.getQuizId())).thenReturn(Optional.of(quiz));

	  
	         questionService.createSaq(saqDto);

	   
	        verify(quizRepository, times(1)).findById(saqDto.getQuizId());
	        

	    }
	    



	    @Test
	    public void testGetByQuizId_SAQ() {
	        UUID quizId = UUID.randomUUID();

	        List<Question> questionList = new ArrayList<>();
	        
	        ShortAnswerQuestion saq = new ShortAnswerQuestion();
	        saq.setType(QuestionType.SHORT_ANSWER);
	        
	        // Mock the Quiz object
	        Quiz mockQuiz = mock(Quiz.class);
	        when(mockQuiz.getId()).thenReturn(quizId);
	        saq.setQuiz(mockQuiz);

	        questionList.add(saq);

	        when(questionRepository.findByQuizId(quizId)).thenReturn(questionList);
	        
	        List<QuestionDto> questionDtos = questionService.getByQuizId(quizId);
	        
	        assertNotNull(questionDtos);

	        
	    }
	    @Test
	    public void testGetByQuizId_MCQ() {
	        UUID quizId = UUID.randomUUID();

	        List<Question> questionList = new ArrayList<>();
	        
	       MultipleChoiceQuestion saq = new MultipleChoiceQuestion();
	        saq.setType(QuestionType.MULTIPLE_CHOICE);
	        
	        // Mock the Quiz object
	        Quiz mockQuiz = mock(Quiz.class);
	        when(mockQuiz.getId()).thenReturn(quizId);
	        saq.setQuiz(mockQuiz);

	        questionList.add(saq);

	        when(questionRepository.findByQuizId(quizId)).thenReturn(questionList);
	        
	        List<QuestionDto> questionDtos = questionService.getByQuizId(quizId);
	        
	        assertNotNull(questionDtos);

	        
	    }
	    

	    @Test
	    public void testUpdateQuestions() {
	        List<QuestionDto> questionDtos = new ArrayList<>();
	        MultipleChoiceQuestion saq = new MultipleChoiceQuestion();
	        saq.setType(QuestionType.MULTIPLE_CHOICE); 

	    
	        questionService.updateQuestions(questionDtos);

	    
	        List<Question> savedQuestions = questionRepository.findAll();

	      
	        assertEquals(questionDtos.size(), savedQuestions.size());
	   
	    }
	    

	}







	    
	
	

	    

	   

	    







