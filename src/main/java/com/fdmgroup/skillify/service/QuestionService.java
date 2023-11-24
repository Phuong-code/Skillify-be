package com.fdmgroup.skillify.service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fdmgroup.skillify.dto.question.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.dto.question.McqDto;
import com.fdmgroup.skillify.dto.question.SaqDto;
import com.fdmgroup.skillify.entity.MultipleChoiceQuestion;
import com.fdmgroup.skillify.entity.Question;
import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.ShortAnswerQuestion;
import com.fdmgroup.skillify.enums.QuestionType;
import com.fdmgroup.skillify.repository.MultipleChoiceQuestionRepository;
import com.fdmgroup.skillify.repository.QuestionRepository;
import com.fdmgroup.skillify.repository.QuizRepository;
import com.fdmgroup.skillify.repository.ShortAnswerQuestionRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	ShortAnswerQuestionRepository shortAnswerQuestionRepository;
	@Autowired
	MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
	@Autowired
	QuizRepository quizRepository;
	
	
	/**
	 * Creates a Multiple choice Question bases on the mcqDTO
	 * @param mcqDto The DTO containing information to create the MCQ
	 * @return The created MCQ
	 */
	public Question createMCQ(McqDto mcqDto) {
		MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
		mcq.setQuestion(mcqDto.getQuestion());
		mcq.setMark(mcqDto.getMark());
		mcq.setType(mcqDto.getType());
		mcq.setOptions(mcqDto.getOptions());
		Quiz quiz = quizRepository.findById(mcqDto.getQuizId()).get();
		mcq.setQuiz(quiz);
		
		return questionRepository.save(mcq);
	}
	
	
	/**
	 * Creates a short answer question bases on the saqDTO
	 * @param saqDto The DTO containing information to create the SAQ.
	 * @return The created SAQ.
	 */
	public Question createSaq(SaqDto saqDto) {
		ShortAnswerQuestion saq = new ShortAnswerQuestion();
		saq.setQuestion(saqDto.getQuestion());
		saq.setMark(saqDto.getMark());
		saq.setType(saqDto.getType());
		saq.setCorrectAnswer(saqDto.getCorrectAnswer());
		Quiz quiz = quizRepository.findById(saqDto.getQuizId()).get();
		saq.setQuiz(quiz);
		
		return questionRepository.save(saq); 
	}
	
	/**
	 * Creates multiple short answer questions based on the provided DTOs.
	 * @param saqDtos List of DTOs containing information to create Short answer question
	 */
	public void createMultipleSaqs(List<SaqDto> saqDtos) {
		List<ShortAnswerQuestion> saqs = saqDtos.stream().map(this::convertDtoToSaq).collect(Collectors.toList());
		shortAnswerQuestionRepository.saveAll(saqs);
	}

	 /**
     * Creates multiple questions based on the provided DTOs.
     *
     * @param questionDtos List of DTOs containing information to create questions.
     * @return List of created Question DTOs.
     */
	public List<QuestionDto> createQuestions(List<QuestionDto> questionDtos) {
		List<Question> questions = questionDtos.stream().map(this::convertDtoToEntity).collect(Collectors.toList());
		questionRepository.saveAll(questions);
		List<QuestionDto> savedQuestionDtos = questions.stream().map(this::convertEntityToDto).collect(Collectors.toList());
		return savedQuestionDtos;
	}
	
	 /**
     * Converts a Question entity to a QuestionDto.
     *
     * @param question The Question entity to convert.
     * @return The corresponding QuestionDto.
     */
	public QuestionDto convertEntityToDto(Question question) {
		if (question.getType().equals(QuestionType.SHORT_ANSWER)) {
			return convertSaqToDto((ShortAnswerQuestion) question); 
		} else {
			return convertMcqToDto((MultipleChoiceQuestion) question);
		}
	}
	
	 /**
     * Converts a SAQ entity to a SaqDto.
     *
     * @param question The SAQ entity to convert.
     * @return The corresponding SaqDto.
     */
	public SaqDto convertSaqToDto(ShortAnswerQuestion question) {
		SaqDto saqDto = new SaqDto();
		saqDto.setQuestion(question.getQuestion());
		saqDto.setMark(question.getMark());
		saqDto.setType(question.getType());
		saqDto.setCorrectAnswer(question.getCorrectAnswer());
		saqDto.setQuizId(question.getQuiz().getId());
		saqDto.setQuestion_id(question.getId());
		return saqDto;
	}
	
	
	 /**
     * Converts a MSQ entity to an McqDto.
     * @param question The MSQ entity to convert.
     * @return The corresponding McqDto.
     */
	public McqDto convertMcqToDto(MultipleChoiceQuestion question) {
		McqDto mcqDto = new McqDto();
		mcqDto.setQuestion(question.getQuestion());
		mcqDto.setMark(question.getMark());
		mcqDto.setType(question.getType());
		mcqDto.setOptions(question.getOptions());
		mcqDto.setQuizId(question.getQuiz().getId());
		mcqDto.setQuestion_id(question.getId());
		return mcqDto; 
	}
	
	
	 /**
     * Converts a QuestionDto to a Question entity.
     * @param questionDto The QuestionDto to convert.
     * @return The corresponding Question entity.
     */
	public Question convertDtoToEntity(QuestionDto questionDto) {
		if (questionDto.getType().equals(QuestionType.SHORT_ANSWER)) {
			return convertDtoToSaq((SaqDto) questionDto);
		} else {
			return convertDtoToMcq((McqDto) questionDto);
		}
	}
	
	
	/**
	 * Converts an McqDto to a MSQ entity.
     *
     * @param questionDto The McqDto to convert.
     * @return The corresponding MSQ entity.
	 */
	public MultipleChoiceQuestion convertDtoToMcq(McqDto questionDto) {
		MultipleChoiceQuestion mcq = questionDto.getQuestion_id() == null ?
				new MultipleChoiceQuestion() : multipleChoiceQuestionRepository.findById(questionDto.getQuestion_id()).get();
		mcq.setQuestion(questionDto.getQuestion());
		mcq.setMark(questionDto.getMark());
		mcq.setType(questionDto.getType());
		mcq.setOptions(questionDto.getOptions());
		Quiz quiz = quizRepository.findById(questionDto.getQuizId()).get();
		mcq.setQuiz(quiz);
		return mcq;
	}
	
	/**
	 * converts an mcqDto to a SAQ entity
	 * @param saqDto The SaqDto to convert
	 * @return The corresponding SAQ entity
	 */
	public ShortAnswerQuestion convertDtoToSaq(SaqDto saqDto) {
		ShortAnswerQuestion saq = saqDto.getQuestion_id() == null ?
				new ShortAnswerQuestion() : shortAnswerQuestionRepository.findById(saqDto.getQuestion_id()).get();
		saq.setQuestion(saqDto.getQuestion());
		saq.setMark(saqDto.getMark());
		saq.setType(saqDto.getType());
		saq.setCorrectAnswer(saqDto.getCorrectAnswer());
		Quiz quiz = quizRepository.findById(saqDto.getQuizId()).get();
		saq.setQuiz(quiz);
		return saq;
	}
	/**
	 * Retrieves a question by its ID
	 * @param id The ID of the question
	 * @return The retrieved question
	 */
	public Question getQuestion(UUID id) {
		return questionRepository.findById(id).get();
	}
	
	/**
	 * Deletes a question by its ID
	 * @param id The ID of the question to delete
	 */
	public void deleteQuestion(UUID id) {
		questionRepository.deleteById(id); 
	}
	
	/**
	 * Get all questions for a quiz
	 * @param id quiz id
	 * @return A list of question belongs to the quiz
	 */
	public List<QuestionDto> getByQuizId(UUID id){
		List<Question> questionList = questionRepository.findByQuizId(id);
		List<QuestionDto> questionDtos = questionList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
		return questionDtos;
	}
	
	
	/**
	 * Retrieves all the questions
	 * @return List of all the question
	 */
	public List<Question> getAllQuestion() {
		return questionRepository.findAll();
	}
	
	/**
	 * Retrieves the correct answers for a MCQ by its ID
	 * @param id The ID of the MCQ
	 * @return List of corrects answers for the MCQ
	 */
	public List<String> getCorrectMcqAnswer(UUID id) {
		return multipleChoiceQuestionRepository.findCorrectAnswerById(id);
	}

	/**
	 * Updates existing questions based on the provided questionDTO
	 * @param questionsDto List of QuestionDto objects for updating questions.
	 */
	@Transactional
	public void updateQuestions(List<QuestionDto> questionsDto) {
		List<Question> questions = questionsDto.stream().map(this::convertDtoToEntity).collect(Collectors.toList());
		questionRepository.saveAll(questions);
	}
	
	/**
	 * Deletes multiple questions based on their ID
	 * @param questionIds List of IDs of questions to be deleted
	 */
	public void deleteQuestions(List<UUID> questionIds) {
		questionRepository.deleteAllById(questionIds);
	}
}

