package com.fdmgroup.skillify.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fdmgroup.skillify.repository.*;
import javax.naming.spi.DirStateFactory.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.skillify.dto.placement.PlacementResponseDto;
import com.fdmgroup.skillify.dto.quiz.ClientQuizCreationDto;
import com.fdmgroup.skillify.dto.quiz.ClientQuizResponseDto;
import com.fdmgroup.skillify.dto.quiz.QuizDto;
import com.fdmgroup.skillify.dto.quiz.QuizUpdateDto;
import com.fdmgroup.skillify.dto.quiz.TechQuizCreationDto;
import com.fdmgroup.skillify.dto.quiz.TechQuizPublicDto;
import com.fdmgroup.skillify.dto.quiz.TechQuizResponseDto;
import com.fdmgroup.skillify.dto.skill.SkillResponseDto;
import com.fdmgroup.skillify.dto.user.UserPublicDto;
import com.fdmgroup.skillify.dto.userAnswer.UserAnswerDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.ClientQuiz;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.entity.Question;
import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.ShortAnswerQuestion;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.entity.TechQuiz;
import com.fdmgroup.skillify.entity.TraineeQuizResult;
import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.entity.UserAnswer;
import com.fdmgroup.skillify.enums.QuestionType;
import com.fdmgroup.skillify.enums.QuizType;

/**
 * Service class responsible for managing quizzes and quiz-related operations.
 */
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final AppUserService appUserService;
    private final TraineeUserRepository traineeUserRepository;
    private final SkillService skillService;
    private final PlacementService placementService;
    private final QuestionService questionService;
    private final UserAnswerRepository userAnswerRepository;
    private final TraineeQuizResultRepository traineeQuizResultRepository;
    private final TechQuizRepository techQuizRepository;
    private final ClientQuizRepository clientQuizRepository;
    


    @Autowired
    public QuizService(QuizRepository quizRepository, AppUserService appUserService, SkillService skillService,
			PlacementService placementService, QuestionService questionService,
			UserAnswerRepository userAnserRepository, TraineeQuizResultRepository traineeQuizResultRepository,
			TraineeUserRepository traineeUserRepository, TechQuizRepository techQuizRepository,
                       ClientQuizRepository clientQuizRepository) {
		super();
		this.quizRepository = quizRepository;
		this.appUserService = appUserService;
		this.skillService = skillService;
		this.placementService = placementService;
		this.questionService = questionService;
		this.userAnswerRepository = userAnserRepository;
		this.traineeQuizResultRepository = traineeQuizResultRepository;
		this.traineeUserRepository = traineeUserRepository;
		this.techQuizRepository = techQuizRepository;
        this.clientQuizRepository = clientQuizRepository;
    }

   
    /**
     * Creates a new TechQuiz based on the provided TechQuizCreationDto.
     *
     * @param techQuizCreationDto The DTO containing information for creating a TechQuiz.
     * @return The created TechQuiz.
     * @throws IllegalArgumentException If the author ID or skill ID is invalid.
     */
    public TechQuiz createTechQuiz(TechQuizCreationDto techQuizCreationDto) {
        AppUser author = appUserService.getUserById(techQuizCreationDto.getAuthorId());
        if (author == null) {
            throw new IllegalArgumentException("Invalid author ID.");
        }
        Skill skill = skillService.getSkillById(techQuizCreationDto.getSkillId());

        if (skill == null) {
            throw new IllegalArgumentException("Invalid skill ID.");
        }
        TechQuiz techQuiz = new TechQuiz();
        techQuiz.setType(techQuizCreationDto.getType());
        techQuiz.setTimeLimit(techQuizCreationDto.getTimeLimit());
        techQuiz.setTotalMark(techQuizCreationDto.getTotalMark());
        techQuiz.setAuthor(author);
        techQuiz.setSkill(skill);
        return quizRepository.save(techQuiz);
    }
    

    /**
     * Creates a new ClientQuiz based on the provided ClientQuizCreationDto.
     *
     * @param clientQuizCreationDto The DTO containing information for creating a ClientQuiz.
     * @return The created ClientQuiz.
     * @throws IllegalArgumentException If the author ID or placement ID is invalid.
     */
	public ClientQuiz createClientQuiz(ClientQuizCreationDto clientQuizCreationDto) {
        AppUser author = appUserService.getUserById(clientQuizCreationDto.getAuthorId());
        if (author == null) {
            throw new IllegalArgumentException("Invalid author ID.");
        }
        Placement placement = placementService.getPlacementById(clientQuizCreationDto.getPlacementId());
        
        if (placement == null) {
            throw new IllegalArgumentException("Invalid placement ID.");
        }
        ClientQuiz clientQuiz = new ClientQuiz();
        clientQuiz.setType(clientQuizCreationDto.getType());
        clientQuiz.setTimeLimit(clientQuizCreationDto.getTimeLimit());
        clientQuiz.setTotalMark(clientQuizCreationDto.getTotalMark());
        clientQuiz.setAuthor(author);
        clientQuiz.setPlacement(placement);
        return quizRepository.save(clientQuiz);
    }

    /**
     * Retrieves a list of all quizzes.
     *
     * @return A list of all quizzes.
     */
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    /**
     * Retrieves a quiz by its ID.
     *
     * @param quizId The ID of the quiz to retrieve.
     * @return An Optional containing the retrieved quiz, or an empty Optional if not found.
     */
    public Optional<Quiz> getQuizById(UUID quizId) {
        return quizRepository.findById(quizId);
    }

    /**
     * This method convert a clientQuiz to clientQuizDto
     * @param clientQuiz
     * @return ClientQuizCreationDto
     */
	public ClientQuizCreationDto convertClientQuizDto(ClientQuiz clientQuiz) {
		ClientQuizCreationDto clientQuizCreationDto = new ClientQuizCreationDto();
		clientQuizCreationDto.setType(QuizType.INTERVIEW);
		clientQuizCreationDto.setTimeLimit(clientQuiz.getTimeLimit());
		clientQuizCreationDto.setTotalMark(clientQuiz.getTotalMark());
		clientQuizCreationDto.setPlacementId(clientQuiz.getPlacement().getId());
		clientQuizCreationDto.setAuthorId(clientQuiz.getAuthor().getId());
		return clientQuizCreationDto;
	}
	
	/**
	 * This method convert a techQuiz to techQuizDto
	 * @param techQuiz
	 * @return TechQuizCreationDto
	 */
	public TechQuizPublicDto convertTechQuizDto(TechQuiz techQuiz) {
		TechQuizPublicDto  techQuizPublicDto  = new TechQuizPublicDto ();
		techQuizPublicDto.setType(QuizType.SKILL);
		techQuizPublicDto.setId(techQuiz.getId());
		techQuizPublicDto.setTimeLimit(techQuiz.getTimeLimit());
		techQuizPublicDto.setTotalMark(techQuiz.getTotalMark());
		techQuizPublicDto.setSkillId(techQuiz.getSkill().getId());
		techQuizPublicDto.setAuthorId(techQuiz.getAuthor().getId());
        techQuizPublicDto.setSkillName(techQuiz.getSkill().getName());
        techQuizPublicDto.setProficiency(techQuiz.getSkill().getProficiency());
	    return techQuizPublicDto;
	}
	
    /**
     * Submits user answers for a quiz, calculates the score, and updates relevant records.
     *
     * @param id              The ID of the quiz.
     * @param answers         List of UserAnswerDto containing user answers.
     * @param authentication  The current login authentication.
     */
    public void submitQuizUserAnswers(UUID id, List<UserAnswerDto> answers, Authentication authentication) {

    	int totalScore = 0;
        TraineeQuizResult result = new TraineeQuizResult();
        TraineeUser trainee = traineeUserRepository.findTraineeUserByEmail(authentication.getName());     
        Quiz quiz = quizRepository.findById(id).get();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        result.setQuiz(quiz);
        result.setTrainee(trainee);
        result.setSubmissionDate(timestamp);
        traineeQuizResultRepository.save(result);
        boolean manualMarking = false;
        
        
        for (UserAnswerDto answer : answers) {
            Question question = questionService.getQuestion(answer.getQuestionId());
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setAnswer(answer.getAnswer());
            userAnswer.setQuestion(question);
            userAnswer.setTrainee((trainee));
            userAnswer.setResult(result);
            
            if (question.getType().equals(QuestionType.MULTIPLE_CHOICE)) {
            	
            	if(markMCQ(answer.getAnswer(),question)) {
                    userAnswer.setTraineeMark(question.getMark());
                    totalScore += question.getMark();   
            	}else {
                    userAnswer.setTraineeMark(0);
            	}


            } else if (question.getType().equals(QuestionType.SHORT_ANSWER)) {

            	if(markSAQ(answer.getAnswer(),question)) {
                    userAnswer.setTraineeMark(question.getMark());
                    totalScore += question.getMark();  
            	}else {
            		//If a short answer question is marked as incorrect by the auto-marking method
            		//Then manual marking is required
                	userAnswer.setTraineeMark(0);
                	manualMarking = true;
            	}
            }
           userAnswerRepository.save(userAnswer);

        }
        if (manualMarking == false) 
            result.setFinishedMarking(true);
        else
            result.setFinishedMarking(false);
        
        result.setScore(totalScore);
        traineeQuizResultRepository.save(result);   
        
        //if trainee scores over 75%, add the skill to trainee profile
        if (quiz.getClass() == TechQuiz.class  && result.getScore() / quiz.getTotalMark() >= 0.75) {
        	TechQuiz techQuiz = techQuizRepository.findById(id).get();
        	trainee.addSkill(techQuiz.getSkill());
        	        	
        	traineeUserRepository.save(trainee);
        }

    }
    
    /**
     * Marks an MCQ question.
     *
     * @param userOption The user's selected option(s).
     * @param question   The MCQ question to be marked.
     * @return True if the user's options match the correct options, false otherwise.
     */
    public boolean markMCQ(String userOption, Question question) {
    	
    	List<String> correctOptions = questionService.getCorrectMcqAnswer(question.getId());
    	List<String> userOptions = Arrays.asList(userOption.split(" "));
    	
    	if((userOptions.containsAll(correctOptions)) && (correctOptions.containsAll(userOptions)))
    		return true;
    	else
    		return false;
    }

    /**
     * Marks a short answer question.
     *
     * @param userAnswer The user's answer.
     * @param question   The short answer question to be marked.
     * @return True if the user's answer matches the correct answer, false otherwise.
     */
    public boolean markSAQ(String userAnswer, Question question) {
    	
    	ShortAnswerQuestion saqQuestion = (ShortAnswerQuestion) question;
    	
    	if (userAnswer.strip().equals(saqQuestion.getCorrectAnswer())){
    		return true;
    	}else
    		return false;
    }
    
    /**
     * Manually marks a quiz and updates the trainee's results.
     *
     * @param traineeQuizResultId The ID of the trainee's quiz result.
     * @param userAnswerDtos      List of UserAnswerDto containing user answers.
     */
    public void manualMarkQuiz(UUID traineeQuizResultId, List<UserAnswerDto> userAnswerDtos) {
    	

    	TraineeQuizResult result = traineeQuizResultRepository.findById(traineeQuizResultId).get();
    	Quiz quiz = result.getQuiz();
    	TraineeUser trainee = result.getTrainee();
    	int traineeMark = 0;
    	int totalMark = 0;
    	
    	for (UserAnswerDto userAnswerDto : userAnswerDtos) {
    		userAnswerDto.getTraineeMark();
    		traineeMark = traineeMark + userAnswerDto.getTraineeMark();
    		totalMark = totalMark + questionService.getQuestion(userAnswerDto.getQuestionId()).getMark();
    	}
    	
    	result.setScore(traineeMark);
    	result.setFinishedMarking(true);
    	
    	traineeQuizResultRepository.save(result);     
    	
        //if trainee scores over 75%, add the skill to trainee profile
        if (quiz.getClass() == TechQuiz.class  && traineeMark / totalMark >= 0.75) {
        	TechQuiz techQuiz = techQuizRepository.findById(quiz.getId()).get();
        	trainee.addSkill(techQuiz.getSkill());
        
        	
        	traineeUserRepository.save(trainee);
        }
    	
    }
    
    /**
     * Retrieves a list of QuizDto objects associated with a specific author ID.
     *
     * @param id The ID of the author for whom to retrieve quizzes.
     * @return A list of QuizDto objects representing the quizzes created by the author.
     */
    public List<QuizDto> getQuizzesByAuthorId(UUID id) {
        List<Quiz> quizzes = quizRepository.findByAuthor_Id(id);
        return quizzes.stream().map(this::convertQuizDto).toList();
    }

    /**
     * Converts a ClientQuiz entity to a ClientQuizCreationDto.
     *
     * @param clientQuiz The ClientQuiz entity to be converted.
     * @return The converted ClientQuizCreationDto.
     */
    public QuizDto convertQuizDto(Quiz quiz) {
        if (quiz instanceof TechQuiz) {
            return convertToTechQuizResponseDto((TechQuiz) quiz);
        } else if (quiz instanceof ClientQuiz) {
            return convertToClientQuizDto((ClientQuiz) quiz);
        }
        return null;
    }

    /**
     * Converts a ClientQuiz entity to a ClientQuizResponseDto.
     *
     * @param clientQuiz The ClientQuiz entity to be converted.
     * @return The converted ClientQuizResponseDto.
     */
    public ClientQuizResponseDto convertToClientQuizDto(ClientQuiz clientQuiz) {
        ClientQuizResponseDto clientQuizResponseDto = new ClientQuizResponseDto();
        clientQuizResponseDto.setId(clientQuiz.getId());
        clientQuizResponseDto.setTimeLimit(clientQuiz.getTimeLimit());
        clientQuizResponseDto.setTotalMark(clientQuiz.getTotalMark());
        clientQuizResponseDto.setType(clientQuiz.getType());

        UserPublicDto userPublicDto = appUserService.convertUserPublicDto(clientQuiz.getAuthor());
        clientQuizResponseDto.setAuthor(userPublicDto);

        PlacementResponseDto placementResponseDto = new PlacementResponseDto();
        placementResponseDto.setId(clientQuiz.getPlacement().getId());
        placementResponseDto.setTitle(clientQuiz.getPlacement().getTitle());
        placementResponseDto.setDescription(clientQuiz.getPlacement().getDescription());
        placementResponseDto.setCompanyName(clientQuiz.getPlacement().getCompanyName());
        placementResponseDto.setAuthorEmail(clientQuiz.getPlacement().getAuthor().getEmail());
        placementResponseDto.setExpiredDate(clientQuiz.getPlacement().getExpiredDate().toString());
        placementResponseDto.setSkillNames(clientQuiz.getPlacement().getSkills().stream()
                .map(skill -> skill.getName() + " - " + skill.getProficiency())
                .toList());
        clientQuizResponseDto.setPlacement(placementResponseDto);

        return clientQuizResponseDto;
    }

    /**
     * Converts a TechQuiz entity to a TechQuizResponseDto.
     *
     * @param techQuiz The TechQuiz entity to be converted.
     * @return The converted TechQuizResponseDto.
     */
    public TechQuizResponseDto convertToTechQuizResponseDto(TechQuiz techQuiz) {
        TechQuizResponseDto techQuizResponseDto = new TechQuizResponseDto();
        techQuizResponseDto.setId(techQuiz.getId());
        techQuizResponseDto.setTimeLimit(techQuiz.getTimeLimit());
        techQuizResponseDto.setTotalMark(techQuiz.getTotalMark());
        techQuizResponseDto.setType(techQuiz.getType());

        UserPublicDto userPublicDto = appUserService.convertUserPublicDto(techQuiz.getAuthor());
        techQuizResponseDto.setAuthor(userPublicDto);

        SkillResponseDto skillResponseDto = new SkillResponseDto();
        skillResponseDto.setId(techQuiz.getSkill().getId());
        skillResponseDto.setName(techQuiz.getSkill().getName());
        skillResponseDto.setProficiency(techQuiz.getSkill().getProficiency());
        skillResponseDto.setDescription(techQuiz.getSkill().getDescription());
        techQuizResponseDto.setSkill(skillResponseDto);

        return techQuizResponseDto;
    }

    /**
     * Deletes a quiz and its related records by ID.
     *
     * @param id The ID of the quiz to be deleted.
     */
    @Transactional
    public void deleteQuizById(UUID id) {
        quizRepository.deleteById(id);
    }

    /**
     * Updates the details of a quiz identified by the provided quiz ID.
     *
     * @param quizId The ID of the quiz to be updated.
     * @param quizUpdateDto The data containing the updated quiz information.
     * @return The updated QuizDto object representing the modified quiz.
     */
    @Transactional
    public QuizDto updateQuizById(UUID quizId, QuizUpdateDto quizUpdateDto) {
        Quiz quiz = quizRepository.findById(quizId).get();
        quiz.setTimeLimit(quizUpdateDto.getTimeLimit());
        quiz.setTotalMark(quizUpdateDto.getTotalMark()); 
        quizRepository.save(quiz);
        return convertQuizDto(quiz);
    }

    /**
     * Retrieves a list of ClientQuizResponseDto objects associated with a specific placement ID.
     *
     * @param placementId The ID of the placement for which to retrieve quizzes.
     * @return A list of ClientQuizResponseDto objects representing the quizzes associated with the placement.
     */
    public List<ClientQuizResponseDto> getQuizzesByPlacementId(UUID placementId) {
        List<ClientQuiz> quizzes = clientQuizRepository.findByPlacement_Id(placementId);
        return quizzes.stream().map(this::convertToClientQuizDto).toList();
    }
}
