package com.fdmgroup.skillify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdmgroup.skillify.dto.quiz.TechQuizCreationDto;
import com.fdmgroup.skillify.dto.quiz.TechQuizPublicDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.entity.TechQuiz;
import com.fdmgroup.skillify.enums.QuizType;
import com.fdmgroup.skillify.repository.QuizRepository;
import com.fdmgroup.skillify.repository.TechQuizRepository;

@SpringBootTest
class TechQuizServiceTest {
	
    @Mock
    private TechQuizRepository techQuizRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private AppUserService appUserService;

    @Mock
    private SkillService skillService;

    private TechQuizService techQuizService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        techQuizService = new TechQuizService(techQuizRepository, appUserService, skillService, quizRepository);
    }

    @Test
    public void testCreateTechQuiz_Success() {
        TechQuizCreationDto techQuizCreationDto = new TechQuizCreationDto();
        techQuizCreationDto.setAuthorId(UUID.randomUUID());
        techQuizCreationDto.setSkillId(UUID.randomUUID());
        techQuizCreationDto.setTimeLimit(60);
        techQuizCreationDto.setTotalMark(100);
        techQuizCreationDto.setType(QuizType.SKILL);

        AppUser author = new AppUser();
        author.setId(techQuizCreationDto.getAuthorId());

        Skill skill = new Skill();
        skill.setId(techQuizCreationDto.getSkillId());

        TechQuiz techQuiz = new TechQuiz();
        techQuiz.setId(UUID.randomUUID());
        techQuiz.setAuthor(author);
        techQuiz.setSkill(skill);
        techQuiz.setTimeLimit(techQuizCreationDto.getTimeLimit());
        techQuiz.setTotalMark(techQuizCreationDto.getTotalMark());
        techQuiz.setType(techQuizCreationDto.getType());

        when(appUserService.getUserById(techQuizCreationDto.getAuthorId())).thenReturn(author);
        when(skillService.getSkillById(techQuizCreationDto.getSkillId())).thenReturn(skill);
        when(quizRepository.save(any())).thenReturn(techQuiz);

        TechQuizPublicDto techQuizPublicDto = techQuizService.createTechQuiz(techQuizCreationDto);

        // Perform assertions based on your expectations
    }
    
    @Test
    public void testGetAllTechQuizzes() {
        TechQuiz quiz1 = new TechQuiz();
        TechQuiz quiz2 = new TechQuiz();
        List<TechQuiz> techQuizList = new ArrayList<>(Arrays.asList(quiz1, quiz2));

        when(techQuizRepository.findAll()).thenReturn(techQuizList);

        List<TechQuiz> result = techQuizService.getAllTechQuizzes();

        assertEquals(2, result.size());
        assertTrue(result.contains(quiz1));
        assertTrue(result.contains(quiz2));
    }

    @Test
    public void testGetTechQuizById() {
        UUID quizId = UUID.randomUUID();
        TechQuiz quiz = new TechQuiz();
        quiz.setId(quizId);

        when(techQuizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        Optional<TechQuiz> result = techQuizService.getTechQuizById(quizId);

        assertTrue(result.isPresent());
        assertEquals(quiz, result.get());
    }

    @Test
    public void testSaveTechQuiz() {
        TechQuiz quiz = new TechQuiz();
        when(techQuizRepository.save(quiz)).thenReturn(quiz);

        TechQuiz savedQuiz = techQuizService.saveTechQuiz(quiz);

        assertEquals(quiz, savedQuiz);
    }

    @Test
    public void testDeleteTechQuiz() {
        UUID quizId = UUID.randomUUID();

        techQuizService.deleteTechQuiz(quizId);

        verify(techQuizRepository, times(1)).deleteById(quizId);
    }

    @Test
    public void testSearchTechQuizBySkill() {
        String keyword = "Java";
        
        // Create a mock skill and author
        Skill skill = new Skill();
        skill.setId(UUID.randomUUID());
        
        AppUser author = new AppUser();
        author.setId(UUID.randomUUID());

        // Create a TechQuiz instance and associate it with the skill and author
        TechQuiz quiz = new TechQuiz();
        quiz.setSkill(skill);
        quiz.setAuthor(author);
        // Set other quiz properties
        
        // Mock the behavior of your techQuizRepository
        when(techQuizRepository.filterBySkill(keyword)).thenReturn(Arrays.asList(quiz));

        List<TechQuizCreationDto> result = techQuizService.searchTechQuizBySkill(keyword);

        assertEquals(1, result.size());
        assertEquals(quiz.getId(), result.get(0).getId());
        assertEquals(skill.getId(), result.get(0).getSkillId()); // Validate skill ID
        assertEquals(author.getId(), result.get(0).getAuthorId()); // Validate author ID
        // ... other assertions ...
    }

}
