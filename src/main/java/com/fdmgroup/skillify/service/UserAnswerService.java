package com.fdmgroup.skillify.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.dto.question.McqDto;
import com.fdmgroup.skillify.dto.question.SaqDto;
import com.fdmgroup.skillify.dto.userAnswer.UserAnswerDto;
import com.fdmgroup.skillify.entity.MultipleChoiceQuestion;
import com.fdmgroup.skillify.entity.Question;
import com.fdmgroup.skillify.entity.ShortAnswerQuestion;
import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.entity.UserAnswer;
import com.fdmgroup.skillify.enums.QuestionType;
import com.fdmgroup.skillify.repository.MultipleChoiceQuestionRepository;
import com.fdmgroup.skillify.repository.QuestionRepository;
import com.fdmgroup.skillify.repository.ShortAnswerQuestionRepository;
import com.fdmgroup.skillify.repository.TraineeUserRepository;
import com.fdmgroup.skillify.repository.UserAnswerRepository;

@Service
public class UserAnswerService {
	
	@Autowired
	UserAnswerRepository userAnswerRepository;
	@Autowired 
	TraineeUserRepository traineeUserRepository;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
	@Autowired
	ShortAnswerQuestionRepository shortAnswerQuestionRepository;

	/**
	 * This method returns all the user answers
	 * @return
	 */
	public List<UserAnswer> getAllUserAnswer() {
		return userAnswerRepository.findAll();
		
	}

	/**
	 * This method returns a user answer by its id
	 * @param userAnswerId
	 * @return
	 */
	public Optional<UserAnswer> getUserAnswerById(UUID userAnswerId){
		return userAnswerRepository.findById(userAnswerId);
		
	}

	/**
	 * This method saves a user answer
	 * @param userAnswerDto
	 * @return
	 */
	public UserAnswerDto saveUserAnswer(UserAnswerDto userAnswerDto) {
	    UserAnswer userAnswer = new UserAnswer();

	    String traineeEmail = userAnswerDto.getTraineeEmail();

	    TraineeUser trainee = traineeUserRepository.findTraineeUserByEmail(traineeEmail);
	    if (trainee == null) {
	        throw new RuntimeException("Trainee with email not found: " + traineeEmail);
	    }

	    userAnswer.setTrainee(trainee);

	    UUID questionId = userAnswerDto.getQuestionId();
	    Optional<Question> question = questionRepository.findById(questionId);
	    if (question.isEmpty()) {
	        throw new RuntimeException("question with id not found: " + questionId);
	    }

	    userAnswer.setQuestion(question.get()); 
	    userAnswer.setAnswer(userAnswerDto.getAnswer());
	    userAnswerRepository.save(userAnswer);

	    UserAnswerDto savedUserAnswerDto = new UserAnswerDto(traineeEmail, questionId, userAnswerDto.getAnswer());

	    return savedUserAnswerDto;
	}

	/**
	 * This method deletes a user answer by its id
 	 * @param userAnswerId
	 */
	public void deleteUserAnswer(UUID userAnswerId) {
		userAnswerRepository.deleteById(userAnswerId);
	}

	/**
	 * This method returns all the user answers by a trainee quiz result id
	 * @param TraineeQuizResultId
	 * @return
	 */
	public List<UserAnswerDto> getUserAnswerByTraineeQuizResultId(UUID TraineeQuizResultId){
		
		List<UserAnswer> answers = userAnswerRepository.findUserAnswerByTraineeQuizResultId(TraineeQuizResultId);
		List<UserAnswerDto> answerDtos = new ArrayList<UserAnswerDto>();
		
		for (UserAnswer answer : answers) {
			UserAnswerDto answerDto = new UserAnswerDto();
			Question question = answer.getQuestion();
			answerDto.setAnswer(answer.getAnswer());
			answerDto.setQuestionId(question.getId());
			answerDto.setTraineeEmail(answer.getTrainee().getEmail());
			answerDto.setTraineeMark(answer.getTraineeMark());
			
			if(question.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
				McqDto mcqDto = new McqDto();
				mcqDto.setMark(question.getMark());
				mcqDto.setQuestion(question.getQuestion());
				mcqDto.setType(QuestionType.MULTIPLE_CHOICE);
				mcqDto.setQuizId(question.getQuiz().getId());
				mcqDto.setQuestion_id(question.getId());
				mcqDto.setOptions(multipleChoiceQuestionRepository.findById(question.getId()).get().getOptions());
				answerDto.setQuestionDto(mcqDto);
			}else {
				SaqDto saqDto = new SaqDto();
				saqDto.setMark(question.getMark());
				saqDto.setQuestion(question.getQuestion());
				saqDto.setType(QuestionType.SHORT_ANSWER);
				saqDto.setQuizId(question.getQuiz().getId());
				saqDto.setQuestion_id(question.getId());
				saqDto.setCorrectAnswer(shortAnswerQuestionRepository.findById(question.getId()).get().getCorrectAnswer());
				answerDto.setQuestionDto(saqDto);
			}
			
			answerDtos.add(answerDto);
		}
		return answerDtos;
	}
}
