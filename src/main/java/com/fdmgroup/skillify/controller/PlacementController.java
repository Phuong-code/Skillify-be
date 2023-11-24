package com.fdmgroup.skillify.controller;

import java.text.ParseException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fdmgroup.skillify.dto.quiz.ClientQuizResponseDto;
import com.fdmgroup.skillify.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.skillify.dto.placement.SearchPlacementDto;
import com.fdmgroup.skillify.dto.placement.PlacementRequestDTO;
import com.fdmgroup.skillify.dto.placement.PlacementResponseDto;
import com.fdmgroup.skillify.dto.placement.SearchPlacementDto;
import com.fdmgroup.skillify.dto.placement.UpdatedPlacementDto;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.service.PlacementService;

@RestController
@RequestMapping("/api")
public class PlacementController {
	
	@Autowired
	private PlacementService placementService;

	@Autowired
	private QuizService quizService;

	@GetMapping("/placements")
	public ResponseEntity<List<Placement>> getAllPlacement() {
	    List<Placement> placements = placementService.getAllNonExpiredPlacement();
	    return ResponseEntity.ok(placements);
	}

	@GetMapping("/placements/my")
	public ResponseEntity<List<PlacementResponseDto>> getMyPlacement(Authentication authentication) {
		String email = authentication.getName();
	    List<PlacementResponseDto> placements = placementService.getPlacementsByAuthorEmail(email);
	    return ResponseEntity.ok(placements);
	}
	
    @GetMapping("/placement/{placementId}")
    public ResponseEntity<Placement> getPlacementById(@PathVariable UUID placementId) {
        Optional<Placement> placementOptional = Optional.of(placementService.getPlacementById(placementId));
        if (placementOptional.isPresent()) {
            Placement placement = placementOptional.get();
            return ResponseEntity.ok(placement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/placement/create")
	public ResponseEntity<PlacementResponseDto> createPlacement(@RequestBody PlacementRequestDTO placementRequestDto, Authentication authentication) throws ParseException{	
		String email = authentication.getName();
		placementRequestDto.setAuthorEmail(email);
		PlacementResponseDto placementResponseDto = placementService.savePlacement(placementRequestDto);
		return ResponseEntity.ok(placementResponseDto);		
		
	}
	
	@PutMapping("/placement/{placementId}")
	public ResponseEntity<UpdatedPlacementDto> updatePlacement(@PathVariable UUID placementId, @RequestBody UpdatedPlacementDto updatedPlacementDto){
		 UpdatedPlacementDto updatedPlacement = placementService.updatePlacement(placementId, updatedPlacementDto);
	        return ResponseEntity.ok(updatedPlacement);
	}
	
	@DeleteMapping("/placement/{placementId}")
	 public ResponseEntity<String> deletePlacement(@PathVariable UUID placementId) {
            placementService.deletePlacement(placementId);
            return ResponseEntity.ok("Placement deleted successfully.");
    }
	
	
    @GetMapping("/placement/search")
    public ResponseEntity<List<SearchPlacementDto>>searchPlacement(@RequestParam("keyword") String keyword){
    	
    	return ResponseEntity.ok(placementService.searchPlacement(keyword));
    }

	@GetMapping("/placement/{placementId}/quiz")
	public ResponseEntity<List<ClientQuizResponseDto>> getPlacementQuiz(@PathVariable UUID placementId){
		List<ClientQuizResponseDto> quizDtos = quizService.getQuizzesByPlacementId(placementId);
		return ResponseEntity.ok(quizDtos);
	}
}
