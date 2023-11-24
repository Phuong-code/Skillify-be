package com.fdmgroup.skillify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.skillify.entity.ClientQuiz;
import com.fdmgroup.skillify.repository.ClientQuizRepository;
import com.fdmgroup.skillify.repository.QuizRepository;
import com.fdmgroup.skillify.service.AppUserService;
import com.fdmgroup.skillify.service.ClientQuizService;
import com.fdmgroup.skillify.service.PlacementService;
import com.fdmgroup.skillify.service.QuizService;

@ExtendWith(MockitoExtension.class)
class ClientQuizServiceTest {
	@InjectMocks
	ClientQuizService clientQuizService;
	
	@Mock
	QuizRepository quizRepository;
	
	@Mock
    AppUserService appUserService;
	
	@Mock
    PlacementService placementService;
	
	@Mock
    ClientQuizRepository clientQuizRepository;
	
	@Mock
    QuizService quizService;

	@Test
    public void test_getClientQuizById_ValidId() {
        UUID quizId = UUID.randomUUID();
        ClientQuiz mockClientQuiz = new ClientQuiz();
        when(clientQuizRepository.findById(quizId)).thenReturn(Optional.of(mockClientQuiz));

        Optional<ClientQuiz> result = clientQuizService.getClientQuizById(quizId);

        assertTrue(result.isPresent());
        assertEquals(mockClientQuiz, result.get());

        verify(clientQuizRepository, times(1)).findById(quizId);
    }

    @Test
    public void test_getClientQuizById_InvalidId() {
        UUID quizId = UUID.randomUUID();
        when(clientQuizRepository.findById(quizId)).thenReturn(Optional.empty());

        Optional<ClientQuiz> result = clientQuizService.getClientQuizById(quizId);

        assertFalse(result.isPresent());

        verify(clientQuizRepository, times(1)).findById(quizId);
    }


}
