package com.fdmgroup.skillify.service;

import java.util.Optional;

import java.util.UUID;

import com.fdmgroup.skillify.dto.quiz.ClientQuizResponseDto;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.dto.quiz.ClientQuizCreationDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.ClientQuiz;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.repository.ClientQuizRepository;
import com.fdmgroup.skillify.repository.QuizRepository;

@Service
public class ClientQuizService {
    private final QuizRepository quizRepository;
    private final AppUserService appUserService;
    private final PlacementService placementService;
    private final ClientQuizRepository clientQuizRepository;
    private final QuizService quizService;

    
    public ClientQuizService(QuizRepository quizRepository, AppUserService appUserService, PlacementService placeService,
    		 ClientQuizRepository clientQuizRepository, QuizService quizService) {
        this.appUserService = appUserService;
		this.quizRepository = quizRepository;
		this.placementService = placeService;
		this.clientQuizRepository = clientQuizRepository;
        this.quizService = quizService;
    }

    public ClientQuizResponseDto createClientQuiz(ClientQuizCreationDto clientQuizCreationDto) {
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
        quizRepository.save(clientQuiz);

        return quizService.convertToClientQuizDto(clientQuiz);
    }
	
    public Optional<ClientQuiz> getClientQuizById(UUID id) {
        return clientQuizRepository.findById(id);
    }
}
