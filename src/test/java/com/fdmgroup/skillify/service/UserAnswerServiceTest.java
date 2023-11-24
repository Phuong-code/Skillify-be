package com.fdmgroup.skillify.service;

import com.fdmgroup.skillify.dto.userAnswer.UserAnswerDto;
import com.fdmgroup.skillify.entity.*;
import com.fdmgroup.skillify.enums.QuestionType;
import com.fdmgroup.skillify.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserAnswerServiceTest {
    @MockBean
    UserAnswerRepository userAnswerRepository;
    @MockBean
    TraineeUserRepository traineeUserRepository;
    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;
    @MockBean
    ShortAnswerQuestionRepository shortAnswerQuestionRepository;

    @Autowired
    UserAnswerService userAnswerService;

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testGetAllUserAnswer() {
        UserAnswer userAnswer1 = new UserAnswer();
        UserAnswer userAnswer2 = new UserAnswer();

        List<UserAnswer> userAnswerList = new ArrayList<>();
        userAnswerList.add(userAnswer1);
        userAnswerList.add(userAnswer2);

        when(userAnswerRepository.findAll()).thenReturn(userAnswerList);

        List<UserAnswer> result = userAnswerService.getAllUserAnswer();

        verify(userAnswerRepository, times(1)).findAll();
        assertEquals(userAnswerList, result);

    }

    @Test
    void testGetUserAnswerById() {
        UUID userAnswerId = UUID.randomUUID();

        UserAnswer mockUserAnswer = new UserAnswer();

        mockUserAnswer.setId(userAnswerId);

        when(userAnswerRepository.findById(userAnswerId)).thenReturn(Optional.of(mockUserAnswer));

        Optional<UserAnswer> result = userAnswerService.getUserAnswerById(userAnswerId);

        verify(userAnswerRepository, times(1)).findById(userAnswerId);
        assertTrue(result.isPresent());
        assertEquals(mockUserAnswer, result.get());
    }

    @Test
    void testGetUserAnswerById_notFound() {
        UUID userAnswerId = UUID.randomUUID();

        when(userAnswerRepository.findById(userAnswerId)).thenReturn(null);

        Optional<UserAnswer> result = userAnswerService.getUserAnswerById(userAnswerId);
        verify(userAnswerRepository, times(1)).findById(userAnswerId);
        assertNull(result);
    }

    @Test
    void test_saveUserAnswer() {
        // Arrange
        String answer = "answer";
        String traineeEmail = "email";
        UUID questionId = UUID.randomUUID();

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setId(questionId);

        TraineeUser traineeUser = new TraineeUser();
        traineeUser.setEmail(traineeEmail);

        UserAnswerDto userAnswerDto = new UserAnswerDto();
        userAnswerDto.setAnswer(answer);
        userAnswerDto.setTraineeEmail(traineeEmail);
        userAnswerDto.setQuestionId(questionId);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(multipleChoiceQuestion));
        when(traineeUserRepository.findTraineeUserByEmail(traineeEmail)).thenReturn(traineeUser);

        // Act
        UserAnswerDto savedUserAnswerDto = userAnswerService.saveUserAnswer(userAnswerDto);

        // Assert
        verify(userAnswerRepository, times(1)).save(any(UserAnswer.class));
        assertEquals(answer, savedUserAnswerDto.getAnswer());
        assertEquals(traineeEmail, savedUserAnswerDto.getTraineeEmail());
        assertEquals(questionId, savedUserAnswerDto.getQuestionId());
    }

    @Test
    void testDeleteUserAnswerById() {
        UUID userAnswerId = UUID.randomUUID();

        userAnswerService.deleteUserAnswer(userAnswerId);
        verify(userAnswerRepository, times(1)).deleteById(userAnswerId);
    }

    @Test
    void test_getUserAnswerByTraineeQuizResultId() {
        // Arrange
        UUID quizId = UUID.randomUUID();
        UUID traineeQuizResultId = UUID.randomUUID();
        UUID mcqId = UUID.randomUUID();
        UUID saqId = UUID.randomUUID();
        String questionSubject = "subject";

        TechQuiz techQuiz = new TechQuiz();
        techQuiz.setId(quizId);

        TraineeUser traineeUser = new TraineeUser();
        traineeUser.setEmail("email");

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setId(mcqId);
        multipleChoiceQuestion.setQuestion(questionSubject);
        multipleChoiceQuestion.setType(QuestionType.MULTIPLE_CHOICE);
        multipleChoiceQuestion.setQuiz(techQuiz);
        multipleChoiceQuestion.setMark(1);
        multipleChoiceQuestion.setOptions(new ArrayList<Option>());

        ShortAnswerQuestion shortAnswerQuestion = new ShortAnswerQuestion();
        shortAnswerQuestion.setId(saqId);
        shortAnswerQuestion.setQuestion(questionSubject);
        shortAnswerQuestion.setType(QuestionType.SHORT_ANSWER);
        shortAnswerQuestion.setQuiz(techQuiz);
        shortAnswerQuestion.setMark(1);
        shortAnswerQuestion.setCorrectAnswer("correct answer");

        UserAnswer userAnswer1 = new UserAnswer();
        userAnswer1.setId(UUID.randomUUID());
        userAnswer1.setQuestion(multipleChoiceQuestion);
        userAnswer1.setTrainee(traineeUser);
        UserAnswer userAnswer2 = new UserAnswer();
        userAnswer2.setId(UUID.randomUUID());
        userAnswer2.setQuestion(shortAnswerQuestion);
        userAnswer2.setTrainee(traineeUser);

        List<UserAnswer> userAnswerList = new ArrayList<>();
        userAnswerList.add(userAnswer1);
        userAnswerList.add(userAnswer2);

        when(userAnswerRepository.findUserAnswerByTraineeQuizResultId(traineeQuizResultId)).thenReturn(userAnswerList);
        when(multipleChoiceQuestionRepository.findById(mcqId)).thenReturn(Optional.of(multipleChoiceQuestion));
        when(shortAnswerQuestionRepository.findById(saqId)).thenReturn(Optional.of(shortAnswerQuestion));

        // Act
        List<UserAnswerDto> resultDtos = userAnswerService.getUserAnswerByTraineeQuizResultId(traineeQuizResultId);

        // Assert
        verify(userAnswerRepository, times(1)).findUserAnswerByTraineeQuizResultId(traineeQuizResultId);
        assertEquals(2, resultDtos.size());
        assertEquals(QuestionType.MULTIPLE_CHOICE, resultDtos.get(0).getQuestionDto().getType());
        assertEquals(QuestionType.SHORT_ANSWER, resultDtos.get(1).getQuestionDto().getType());
    }

}