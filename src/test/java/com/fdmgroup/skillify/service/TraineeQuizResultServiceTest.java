package com.fdmgroup.skillify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.skillify.repository.TraineeQuizResultRepository;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.ClientQuiz;
import com.fdmgroup.skillify.entity.TraineeQuizResult;
import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.dto.quiz.*;
import com.fdmgroup.skillify.dto.traineeQuizResult.TraineeQuizResultDto;
import com.fdmgroup.skillify.dto.user.*;

@SpringBootTest
class TraineeQuizResultServiceTest {

	@MockBean
	TraineeQuizResultRepository traineeQuizResultRepository;
	
	@MockBean
	QuizService quizService;
	
	@MockBean
	AppUserService appUserService;
	
	@Autowired
	TraineeQuizResultService traineeQuizResultService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test_get_all_trainee_quiz_submission() throws ParseException {
	
    String email = "trainer@example.com";
    Quiz quiz = new ClientQuiz();
    QuizDto quizDto = new ClientQuizResponseDto();
    UserPublicDto userPublicDto = new UserPublicDto();
    TraineeUser trainee = new TraineeUser();
    
    List<TraineeQuizResult> unmarkedResults = new ArrayList<>();
    TraineeQuizResult result = new TraineeQuizResult();
    result.setId(UUID.randomUUID());
    result.setSubmissionDate(new Timestamp(100));
    result.setQuiz(quiz);
    result.setTrainee(trainee);
    unmarkedResults.add(result);

    List<TraineeQuizResultDto> unmarkedResultsDto = new ArrayList<>();
    TraineeQuizResultDto resultDto = new TraineeQuizResultDto();
    resultDto.setId(result.getId());
    resultDto.setSubmissionDate(result.getSubmissionDate().toString());
    resultDto.setQuiz(quizDto);
    resultDto.setTrainee(userPublicDto);
    unmarkedResultsDto.add(resultDto);
    
    when(traineeQuizResultRepository.findAllQuizByCurrentUser(email)).thenReturn(unmarkedResults);
    when(quizService.convertQuizDto(result.getQuiz())).thenReturn(quizDto);
    when(appUserService.convertUserPublicDto(result.getTrainee())).thenReturn(userPublicDto);
    		
    List<TraineeQuizResultDto> responseDto = traineeQuizResultService.getAllMyTraineeQuizResults(email);

    verify(traineeQuizResultRepository, times(1)).findAllQuizByCurrentUser(email);   
    verify(quizService, times(1)).convertQuizDto(result.getQuiz());
    verify(appUserService, times(1)).convertUserPublicDto(result.getTrainee());

    assertEquals(unmarkedResultsDto.toString(), responseDto.toString());
	}
	
	@Test
	void test_get_unmarked_trainee_quiz_submission() throws ParseException {
	
    String email = "trainer@example.com";
    Quiz quiz = new ClientQuiz();
    QuizDto quizDto = new ClientQuizResponseDto();
    UserPublicDto userPublicDto = new UserPublicDto();
    TraineeUser trainee = new TraineeUser();
    
    List<TraineeQuizResult> unmarkedResults = new ArrayList<>();
    TraineeQuizResult result = new TraineeQuizResult();
    result.setId(UUID.randomUUID());
    result.setSubmissionDate(new Timestamp(100));
    result.setQuiz(quiz);
    result.setTrainee(trainee);
    unmarkedResults.add(result);

    List<TraineeQuizResultDto> unmarkedResultsDto = new ArrayList<>();
    TraineeQuizResultDto resultDto = new TraineeQuizResultDto();
    resultDto.setId(result.getId());
    resultDto.setSubmissionDate(result.getSubmissionDate().toString());
    resultDto.setQuiz(quizDto);
    resultDto.setTrainee(userPublicDto);
    unmarkedResultsDto.add(resultDto);
    
    when(traineeQuizResultRepository.findUnmarkedQuizByCurrentUser(email)).thenReturn(unmarkedResults);
    when(quizService.convertQuizDto(result.getQuiz())).thenReturn(quizDto);
    when(appUserService.convertUserPublicDto(result.getTrainee())).thenReturn(userPublicDto);
    		
    List<TraineeQuizResultDto> responseDto = traineeQuizResultService.getUnmarkedTraineeQuizResult(email);

    verify(traineeQuizResultRepository, times(1)).findUnmarkedQuizByCurrentUser(email);   
    verify(quizService, times(1)).convertQuizDto(result.getQuiz());
    verify(appUserService, times(1)).convertUserPublicDto(result.getTrainee());

    assertEquals(unmarkedResultsDto.toString(), responseDto.toString());
	}
	
}
