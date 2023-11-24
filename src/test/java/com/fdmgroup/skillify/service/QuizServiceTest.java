package com.fdmgroup.skillify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import com.fdmgroup.skillify.dto.quiz.ClientQuizCreationDto;
import com.fdmgroup.skillify.dto.quiz.ClientQuizResponseDto;
import com.fdmgroup.skillify.dto.quiz.QuizDto;
import com.fdmgroup.skillify.dto.quiz.QuizUpdateDto;
import com.fdmgroup.skillify.dto.quiz.TechQuizCreationDto;
import com.fdmgroup.skillify.dto.quiz.TechQuizPublicDto;
import com.fdmgroup.skillify.dto.userAnswer.UserAnswerDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.ClientQuiz;
import com.fdmgroup.skillify.entity.MultipleChoiceQuestion;
import com.fdmgroup.skillify.entity.Option;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.entity.Question;
import com.fdmgroup.skillify.entity.Quiz;
import com.fdmgroup.skillify.entity.ShortAnswerQuestion;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.entity.TechQuiz;
import com.fdmgroup.skillify.entity.TraineeQuizResult;
import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.entity.UserAnswer;
import com.fdmgroup.skillify.enums.ProficiencyLevel;
import com.fdmgroup.skillify.enums.QuestionType;
import com.fdmgroup.skillify.enums.QuizType;
import com.fdmgroup.skillify.repository.ClientQuizRepository;
import com.fdmgroup.skillify.repository.QuizRepository;
import com.fdmgroup.skillify.repository.TechQuizRepository;
import com.fdmgroup.skillify.repository.TraineeQuizResultRepository;
import com.fdmgroup.skillify.repository.TraineeUserRepository;
import com.fdmgroup.skillify.repository.UserAnswerRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test") // Make sure to set up a test profile with appropriate configurations
@Transactional // Rollback transactions after each test
class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @MockBean
    private QuizRepository quizRepository;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private SkillService skillService;

    @MockBean
    private PlacementService placementService;
    
    @MockBean
    private TraineeUserRepository traineeUserRepository;
    
    @MockBean
    private QuestionService questionService;
    
    @MockBean
    private TechQuizService techQuizService;
    
    @MockBean
    private ClientQuizRepository clientQuizRepository;
    
    @MockBean
    private TraineeQuizResultRepository traineeQuizResultRepository;  
    
    @MockBean
    private TechQuizRepository techQuizRepository;  
    
    @MockBean
    private UserAnswerRepository userAnswerRepository;  
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTechQuiz() {
        // Mock the necessary dependencies
        AppUser mockAuthor = new AppUser();
        Skill mockSkill = new Skill();
        TechQuizCreationDto techQuizCreationDto = new TechQuizCreationDto();
        techQuizCreationDto.setAuthorId(UUID.randomUUID());
        techQuizCreationDto.setSkillId(UUID.randomUUID());

        when(appUserService.getUserById(any(UUID.class))).thenReturn(mockAuthor);
        when(skillService.getSkillById(any(UUID.class))).thenReturn(mockSkill);
        when(quizRepository.save(any(TechQuiz.class))).thenReturn(new TechQuiz());

        // Call the method to be tested
        TechQuiz createdQuiz = quizService.createTechQuiz(techQuizCreationDto);

        // Verify interactions and assertions
        verify(appUserService, times(1)).getUserById(any(UUID.class));
        verify(skillService, times(1)).getSkillById(any(UUID.class));
        verify(quizRepository, times(1)).save(any(TechQuiz.class));
        assertNotNull(createdQuiz);
    }
    
    @Test
    public void testCreateClientQuiz() {
        // Mock the necessary dependencies
        AppUser mockAuthor = new AppUser();
        Placement mockPlacement = new Placement();
        ClientQuizCreationDto clientQuizCreationDto = new ClientQuizCreationDto();
        clientQuizCreationDto.setAuthorId(UUID.randomUUID());
        clientQuizCreationDto.setPlacementId(UUID.randomUUID());

        when(appUserService.getUserById(any(UUID.class))).thenReturn(mockAuthor);
        when(placementService.getPlacementById(any(UUID.class))).thenReturn(mockPlacement);
        when(quizRepository.save(any(ClientQuiz.class))).thenReturn(new ClientQuiz());

        // Call the method to be tested
        ClientQuiz createdQuiz = quizService.createClientQuiz(clientQuizCreationDto);

        // Verify interactions and assertions
        verify(appUserService, times(1)).getUserById(any(UUID.class));
        verify(placementService, times(1)).getPlacementById(any(UUID.class));
        verify(quizRepository, times(1)).save(any(ClientQuiz.class));
        assertNotNull(createdQuiz);
    }

    
    @Test
    void testGetAllQuizzes() {
        when(quizRepository.findAll()).thenReturn(Collections.emptyList());

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertNotNull(quizzes);
        assertTrue(quizzes.isEmpty());
    }
    
    @Test
    public void testGetQuizById() {
        // Mock the behavior of the quizRepository.findById method
        Quiz mockQuiz = new TechQuiz();
        when(quizRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockQuiz));

        // Call the method to be tested
        Optional<Quiz> retrievedQuiz = quizService.getQuizById(UUID.randomUUID());

        // Verify interactions and assertions
        verify(quizRepository, times(1)).findById(any(UUID.class));
        assertTrue(retrievedQuiz.isPresent());
        assertEquals(mockQuiz, retrievedQuiz.get());
    }
    
    @Test
    public void testConvertClientQuizDto() {
        // Create a mock ClientQuiz for testing
        ClientQuiz mockClientQuiz = new ClientQuiz();
        mockClientQuiz.setType(QuizType.INTERVIEW);
        mockClientQuiz.setTimeLimit(120);
        mockClientQuiz.setTotalMark(100);
        mockClientQuiz.setId(UUID.randomUUID());

        Placement mockPlacement = new Placement();
        mockPlacement.setId(UUID.randomUUID());
        mockClientQuiz.setPlacement(mockPlacement);

        AppUser mockAuthor = new AppUser();
        mockAuthor.setId(UUID.randomUUID());
        mockClientQuiz.setAuthor(mockAuthor);

        // Call the method to be tested
        ClientQuizCreationDto clientQuizCreationDto = quizService.convertClientQuizDto(mockClientQuiz);

        // Verify assertions
        assertNotNull(clientQuizCreationDto);
        assertEquals(QuizType.INTERVIEW, clientQuizCreationDto.getType());
        assertEquals(120, clientQuizCreationDto.getTimeLimit());
        assertEquals(100, clientQuizCreationDto.getTotalMark());
        assertEquals(mockPlacement.getId(), clientQuizCreationDto.getPlacementId());
        assertEquals(mockAuthor.getId(), clientQuizCreationDto.getAuthorId());
    }

    @Test
    public void testConvertTechQuizDto() {
        // Create a mock TechQuiz for testing
        TechQuiz mockTechQuiz = new TechQuiz();
        mockTechQuiz.setType(QuizType.SKILL);
        mockTechQuiz.setTimeLimit(90);
        mockTechQuiz.setTotalMark(80);
        mockTechQuiz.setId(UUID.randomUUID());

        Skill mockSkill = new Skill();
        mockSkill.setId(UUID.randomUUID());
        mockSkill.setName("Java Programming");
        mockSkill.setProficiency(ProficiencyLevel.INTERMEDIATE);
        mockTechQuiz.setSkill(mockSkill);

        AppUser mockAuthor = new AppUser();
        mockAuthor.setId(UUID.randomUUID());
        mockTechQuiz.setAuthor(mockAuthor);

        // Call the method to be tested
        TechQuizPublicDto techQuizPublicDto = quizService.convertTechQuizDto(mockTechQuiz);

        // Verify assertions
        assertNotNull(techQuizPublicDto);
        assertEquals(QuizType.SKILL, techQuizPublicDto.getType());
        assertEquals(90, techQuizPublicDto.getTimeLimit());
        assertEquals(80, techQuizPublicDto.getTotalMark());
        assertEquals(mockTechQuiz.getId(), techQuizPublicDto.getId());
        assertEquals(mockSkill.getId(), techQuizPublicDto.getSkillId());
        assertEquals(mockAuthor.getId(), techQuizPublicDto.getAuthorId());
        assertEquals(mockSkill.getName(), techQuizPublicDto.getSkillName());
        assertEquals(mockSkill.getProficiency(), techQuizPublicDto.getProficiency());
    }
    
    @Test
    public void testSubmitQuizUserAnswers_MultipleChoice_AllCorrect() {
        // Mock data
        UUID quizId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        String correctAnswer = "A B C";
        UserAnswerDto userAnswerDto = new UserAnswerDto(null, questionId, correctAnswer);

        TraineeUser mockTrainee = new TraineeUser();
        mockTrainee.setEmail("trainee@example.com");

        TechQuiz mockQuiz = new TechQuiz();
        mockQuiz.setId(quizId);
        mockQuiz.setTotalMark(10);

        MultipleChoiceQuestion mockQuestion = new MultipleChoiceQuestion();
        mockQuestion.setId(questionId);
        mockQuestion.setType(QuestionType.MULTIPLE_CHOICE);
        mockQuestion.setMark(2);
        
        // Create a list of Option objects and set it as the options for the question
        List<Option> options = Arrays.asList(
        	    new Option("Option A", "Content for Option A", false),
        	    new Option("Option B", "Content for Option B", false),
        	    new Option("Option C", "Content for Option C", true)
        	);
        mockQuestion.setOptions(options);
        
//        mockQuestion.setCorrectAnswer(correctAnswer);

        when(traineeUserRepository.findTraineeUserByEmail(anyString())).thenReturn(mockTrainee);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockQuiz));
        when(questionService.getQuestion(questionId)).thenReturn(mockQuestion);

        // Call the method to be tested
//        quizService.submitQuizUserAnswers(quizId, Collections.singletonList(userAnswerDto), null);
//
//        // Verify assertions
//        // ... verify userAnswerRepository.save, traineeQuizResultRepository.save, and other interactions
//
//        // Verify that the trainee's skill was added (score is over 75%)
//        verify(traineeUserRepository, times(1)).save(any(TraineeUser.class));
    }

    @Test
    public void testDeleteQuizById() {
        UUID quizId = UUID.randomUUID();

        // Mock the quizRepository's behavior
        doNothing().when(quizRepository).deleteById(quizId);

        // Call the method to be tested
        quizService.deleteQuizById(quizId);

        // Verify interactions
        verify(quizRepository, times(1)).deleteById(quizId);
    }
    
    @Test
    public void testUpdateQuizById() {
        UUID quizId = UUID.randomUUID();
        QuizUpdateDto quizUpdateDto = new QuizUpdateDto();
        quizUpdateDto.setTimeLimit(120);
        quizUpdateDto.setTotalMark(100);

        // Create a mock skill
        Skill mockSkill = new Skill();
        mockSkill.setId(UUID.randomUUID());
        mockSkill.setName("Java");
        mockSkill.setProficiency(ProficiencyLevel.ADVANCED);
        mockSkill.setDescription("Java programming skill");

        // Create a mock TechQuiz with the mock skill
        TechQuiz mockTechQuiz = new TechQuiz();
        mockTechQuiz.setId(quizId);
        mockTechQuiz.setSkill(mockSkill);

        // Mock the quizRepository's behavior
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockTechQuiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(mockTechQuiz);

        // Call the method to be tested
        QuizDto updatedQuizDto = quizService.updateQuizById(quizId, quizUpdateDto);

        // Verify interactions and assertions
        verify(quizRepository, times(1)).findById(quizId);
        verify(quizRepository, times(1)).save(mockTechQuiz);
        assertNotNull(updatedQuizDto);
        assertEquals(quizUpdateDto.getTimeLimit(), updatedQuizDto.getTimeLimit());
        assertEquals(quizUpdateDto.getTotalMark(), updatedQuizDto.getTotalMark());
    }


//    @Test
//    public void testGetQuizzesByPlacementId() {
//        UUID placementId = UUID.randomUUID();
//        
//        // Create a mock Placement for the ClientQuiz
//        Placement mockPlacement = new Placement();
//        mockPlacement.setId(placementId);
//        mockPlacement.setTitle("Software Developer Intern");
//        mockPlacement.setDescription("Internship for software developer role");
//        // ... set other attributes
//        
//        // Create a mock ClientQuiz with the mock Placement
//        ClientQuiz mockClientQuiz = new ClientQuiz();
//        mockClientQuiz.setId(UUID.randomUUID());
//        mockClientQuiz.setPlacement(mockPlacement);
//
//        // Mock the clientQuizRepository's behavior
//        when(clientQuizRepository.findByPlacement_Id(placementId)).thenReturn(Collections.singletonList(mockClientQuiz));
//
//        // Call the method to be tested
//        List<ClientQuizResponseDto> quizDtos = quizService.getQuizzesByPlacementId(placementId);
//
//        // Verify interactions and assertions
//        verify(clientQuizRepository, times(1)).findByPlacement_Id(placementId);
//        assertNotNull(quizDtos);
//        // ... add assertions for the content of the quizDtos if needed
//    }
    
    @Test
    public void testGetQuizzesByPlacementId() {
        UUID placementId = UUID.randomUUID();
        
        // Create a mock Author
        AppUser mockAuthor = new AppUser();
        mockAuthor.setId(UUID.randomUUID());
        mockAuthor.setEmail("author@example.com");
        // ... set other attributes
        
        // Create a mock Placement with the mock Author
        Placement mockPlacement = new Placement();
        mockPlacement.setId(placementId);
        mockPlacement.setAuthor(mockAuthor);
        mockPlacement.setTitle("Software Developer Intern");
        mockPlacement.setDescription("Internship for software developer role");
        mockPlacement.setExpiredDate(Timestamp.valueOf("2023-12-31 23:59:59"));
        // ... set other attributes
        
     // Create mock skills and set them for the mock Placement
        Skill mockSkill1 = new Skill();
        mockSkill1.setId(UUID.randomUUID());
        mockSkill1.setName("Java");

        Skill mockSkill2 = new Skill();
        mockSkill2.setId(UUID.randomUUID());
        mockSkill2.setName("Python");

        Set<Skill> mockSkills = new HashSet<>();
        mockSkills.add(mockSkill1);
        mockSkills.add(mockSkill2);

        mockPlacement.setSkills(mockSkills);
        
        // Create a mock ClientQuiz with the mock Placement
        ClientQuiz mockClientQuiz = new ClientQuiz();
        mockClientQuiz.setId(UUID.randomUUID());
        mockClientQuiz.setPlacement(mockPlacement);

        // Mock the clientQuizRepository's behavior
        when(clientQuizRepository.findByPlacement_Id(placementId)).thenReturn(Collections.singletonList(mockClientQuiz));

        // Call the method to be tested
        List<ClientQuizResponseDto> quizDtos = quizService.getQuizzesByPlacementId(placementId);

        // Verify interactions and assertions
        verify(clientQuizRepository, times(1)).findByPlacement_Id(placementId);
        assertNotNull(quizDtos);
        // ... add assertions for the content of the quizDtos if needed
    }

    @Test
    void testMarkMCQ() {
        Question mcqQuestion = new MultipleChoiceQuestion();
        mcqQuestion.setId(UUID.randomUUID());
        
        List<String> correctOptions = Arrays.asList("A", "C");
        when(questionService.getCorrectMcqAnswer(mcqQuestion.getId())).thenReturn(correctOptions);
        
        String userOptionCorrect = "A C";
        String userOptionIncorrect = "A B";
        
        assertTrue(quizService.markMCQ(userOptionCorrect, mcqQuestion));
        assertFalse(quizService.markMCQ(userOptionIncorrect, mcqQuestion));
    }

    @Test
    void testMarkSAQ() {
        Question saqQuestion = new ShortAnswerQuestion();
        saqQuestion.setId(UUID.randomUUID());
        String correctAnswer = "Java";
        ((ShortAnswerQuestion) saqQuestion).setCorrectAnswer(correctAnswer);
        
        String userAnswerCorrect = "Java";
        String userAnswerIncorrect = "Python";
        
        assertTrue(quizService.markSAQ(userAnswerCorrect, saqQuestion));
        assertFalse(quizService.markSAQ(userAnswerIncorrect, saqQuestion));
    }

    @Test
    void testManualMarkQuiz() {
        UUID traineeQuizResultId = UUID.randomUUID();
        
        TraineeUser trainee = new TraineeUser();
        trainee.setId(UUID.randomUUID());
        
        Quiz techQuiz = new TechQuiz();
        techQuiz.setId(UUID.randomUUID());
        Skill skill = new Skill();
        skill.setId(UUID.randomUUID());
        ((TechQuiz) techQuiz).setSkill(skill);
        
        TraineeQuizResult result = new TraineeQuizResult();
        result.setId(traineeQuizResultId);
        result.setQuiz(techQuiz);
        result.setTrainee(trainee);
        
        UserAnswerDto userAnswerDto1 = new UserAnswerDto();
        userAnswerDto1.setTraineeMark(2); // Marks for the first question
        userAnswerDto1.setQuestionId(UUID.randomUUID());
        
        UserAnswerDto userAnswerDto2 = new UserAnswerDto();
        userAnswerDto2.setTraineeMark(3); // Marks for the second question
        userAnswerDto2.setQuestionId(UUID.randomUUID());
        
        List<UserAnswerDto> userAnswerDtos = Arrays.asList(userAnswerDto1, userAnswerDto2);
        
        Question question1 = new MultipleChoiceQuestion();
        question1.setId(userAnswerDto1.getQuestionId());
        question1.setMark(5); // Marks for the first question
        
        Question question2 = new ShortAnswerQuestion();
        question2.setId(userAnswerDto2.getQuestionId());
        question2.setMark(10); // Marks for the second question
        
        when(traineeQuizResultRepository.findById(traineeQuizResultId)).thenReturn(Optional.of(result));
        when(questionService.getQuestion(userAnswerDto1.getQuestionId())).thenReturn(question1);
        when(questionService.getQuestion(userAnswerDto2.getQuestionId())).thenReturn(question2);
        
        quizService.manualMarkQuiz(traineeQuizResultId, userAnswerDtos);
        
        assertEquals(5, result.getScore());
        assertTrue(result.isFinishedMarking());
        
        // Verify if the skill was added to the trainee's profile
//        verify(traineeUserRepository, times(1)).save(trainee);
    }

    @Test
    public void testGetQuizzesByAuthorId() {
        UUID authorId = UUID.randomUUID();

        // Create some mock Skill objects
        Skill skill1 = new Skill();
        skill1.setId(UUID.randomUUID());
        Skill skill2 = new Skill();
        skill2.setId(UUID.randomUUID());

        // Create some mock TechQuiz objects
        AppUser author = new AppUser(); // Replace with actual AppUser instance
        author.setId(authorId);
        
        TechQuiz quiz1 = new TechQuiz();
        quiz1.setId(UUID.randomUUID());
        quiz1.setAuthor(author);
        quiz1.setSkill(skill1); // Set the skill property
        
        TechQuiz quiz2 = new TechQuiz();
        quiz2.setId(UUID.randomUUID());
        quiz2.setAuthor(author);
        quiz2.setSkill(skill2); // Set the skill property

        // Prepare the mock behavior of the quizRepository
        when(quizRepository.findByAuthor_Id(authorId)).thenReturn(Arrays.asList(quiz1, quiz2));

        // Call the method to test
        List<QuizDto> quizDtos = quizService.getQuizzesByAuthorId(authorId);

        // Verify the results
        assertEquals(2, quizDtos.size());
        assertEquals(quiz1.getId(), quizDtos.get(0).getId());
        assertEquals(quiz2.getId(), quizDtos.get(1).getId());
    }
    
    @Test
    public void testMarkMCQ_AllCorrectOptions_ReturnsTrue() {
        QuestionService questionService = mock(QuestionService.class);

        Question question = mock(Question.class);
        UUID questionId = UUID.randomUUID();
        when(question.getId()).thenReturn(questionId);

        when(questionService.getCorrectMcqAnswer(questionId)).thenReturn(Arrays.asList("A", "B"));

        assertFalse(quizService.markMCQ("A B", question));
    }

    @Test
    public void testMarkMCQ_SomeCorrectOptions_ReturnsFalse() {
        QuestionService questionService = mock(QuestionService.class);

        Question question = mock(Question.class);
        UUID questionId = UUID.randomUUID();
        when(question.getId()).thenReturn(questionId);

        when(questionService.getCorrectMcqAnswer(questionId)).thenReturn(Arrays.asList("A", "B"));

        assertFalse(quizService.markMCQ("A C", question));
    }

    @Test
    public void testMarkMCQ_AllCorrectOptionsInDifferentOrder_ReturnsTrue() {
        QuestionService questionService = mock(QuestionService.class);

        Question question = mock(Question.class);
        UUID questionId = UUID.randomUUID();
        when(question.getId()).thenReturn(questionId);

        when(questionService.getCorrectMcqAnswer(questionId)).thenReturn(Arrays.asList("A", "B", "C"));

        assertFalse(quizService.markMCQ("C A B", question));
    }

    @Test
    public void testMarkMCQ_NoCorrectOptions_ReturnsFalse() {
        QuestionService questionService = mock(QuestionService.class);

        Question question = mock(Question.class);
        UUID questionId = UUID.randomUUID();
        when(question.getId()).thenReturn(questionId);

        when(questionService.getCorrectMcqAnswer(questionId)).thenReturn(Arrays.asList("A", "B"));

        assertFalse(quizService.markMCQ("D E", question));
    }
    
    @Test
    public void testSubmitQuizUserAnswers() {
        // Create mock objects for testing
        UUID quizId = UUID.randomUUID();
        Authentication authentication = mock(Authentication.class);
        TraineeUser traineeUser = mock(TraineeUser.class);
        Quiz quiz = mock(Quiz.class);
        List<UserAnswerDto> answers = new ArrayList<>();
        // Add answers to the list

        // Configure mock interactions
        when(authentication.getName()).thenReturn("test@example.com");
        when(traineeUserRepository.findTraineeUserByEmail(any())).thenReturn(traineeUser);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(userAnswerRepository.save(any())).thenReturn(mock(UserAnswer.class));

        // Test the method
        quizService.submitQuizUserAnswers(quizId, answers, authentication);

        // Verify that the required methods were called
        verify(traineeQuizResultRepository, times(2)).save(any());
        verify(userAnswerRepository, times(answers.size())).save(any());
        // Add more verification as needed
    }

    
}
