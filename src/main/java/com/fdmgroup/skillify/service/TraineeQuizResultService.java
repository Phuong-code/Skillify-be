package com.fdmgroup.skillify.service;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.skillify.dto.user.UserPublicDto;
import com.fdmgroup.skillify.entity.TraineeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.dto.quiz.QuizDto;
import com.fdmgroup.skillify.dto.traineeQuizResult.TraineeQuizResultDto;
import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.TraineeQuizResult;
import com.fdmgroup.skillify.repository.TraineeQuizResultRepository;
/**
 * This class defines service methods related to TraineeQuizResult, the submission of a quiz.
 */
@Service
public class TraineeQuizResultService {
	
	@Autowired
	private TraineeQuizResultRepository traineeQuizResultRepository;

	@Autowired
	private QuizService quizService;

	@Autowired
	private AppUserService appUserService;
	
    /**
     * This method get all unmarked quiz submissions for current logged in sales/trainer and convert into Dto
     * @param email String, Current user's email
     * @return List of TraineeQuizResultDto
     */
    public List<TraineeQuizResultDto> getUnmarkedTraineeQuizResult(String email) {
        
    	List<TraineeQuizResult> unmarkedResults = traineeQuizResultRepository.findUnmarkedQuizByCurrentUser(email);
    	List<TraineeQuizResultDto> unmarkedResultDtos = new ArrayList<TraineeQuizResultDto>();
    	
    	for (TraineeQuizResult traineeQuizResult : unmarkedResults) {
    		TraineeQuizResultDto unmarkedResultDto = new TraineeQuizResultDto();
    		unmarkedResultDto.setId(traineeQuizResult.getId());

			Quiz quiz = traineeQuizResult.getQuiz();
			QuizDto quizDto = quizService.convertQuizDto(quiz);
    		unmarkedResultDto.setQuiz(quizDto);

			TraineeUser trainee = traineeQuizResult.getTrainee();
			UserPublicDto userPublicDto = appUserService.convertUserPublicDto(trainee);
			unmarkedResultDto.setTrainee(userPublicDto);
			
    		unmarkedResultDto.setSubmissionDate(traineeQuizResult.getSubmissionDate().toString());
    		unmarkedResultDto.setScore(traineeQuizResult.getScore());
    		
    		unmarkedResultDtos.add(unmarkedResultDto);
    	}
    	return unmarkedResultDtos;
    }

	/**
	 * This method get all quiz submissions for current logged in sales/trainer
	 * @param email String, Current user's email
	 * @return List of TraineeQuizResultDto
	 */
	public List<TraineeQuizResultDto> getAllMyTraineeQuizResults(String email) {
		List<TraineeQuizResult> results = traineeQuizResultRepository.findAllQuizByCurrentUser(email);
		List<TraineeQuizResultDto> resultDtos = new ArrayList<TraineeQuizResultDto>();

		for (TraineeQuizResult traineeQuizResult : results) {
			TraineeQuizResultDto resultDto = new TraineeQuizResultDto();
			resultDto.setId(traineeQuizResult.getId());

			Quiz quiz = traineeQuizResult.getQuiz();
			QuizDto quizDto = quizService.convertQuizDto(quiz);
			resultDto.setQuiz(quizDto);

			TraineeUser trainee = traineeQuizResult.getTrainee();
			UserPublicDto userPublicDto = appUserService.convertUserPublicDto(trainee);
			resultDto.setTrainee(userPublicDto);

			resultDto.setSubmissionDate(traineeQuizResult.getSubmissionDate().toString());
			resultDto.setScore(traineeQuizResult.getScore());

			resultDtos.add(resultDto);
		}
		return resultDtos;
	}
}
