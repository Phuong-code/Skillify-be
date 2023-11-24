package com.fdmgroup.skillify.controller;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.skillify.dto.Request.RequestDto;
import com.fdmgroup.skillify.dto.Request.RequestPublicDto;
import com.fdmgroup.skillify.service.RequestService;

/**
 * This class defines the REST API endpoints related to requests.
 */
@RestController
@RequestMapping("/api/request")
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	
	/**
	 * Endpoint to allow a trainee to request a new skill.
	 * 
	 * @param requestDto The request data.
	 * @param authentication The authentication object.
	 * @return The saved skill request data.
	 * @throws ParseException if there is an error parsing the date.
	 */
	@PostMapping("/traineeRequestSkill")
	public ResponseEntity<RequestPublicDto> traineeRequestSkill(@RequestBody RequestDto requestDto, Authentication authentication) throws ParseException{
		String email = authentication.getName();
		requestDto.setSenderEmail(email);
		RequestPublicDto saveRequestSkill = requestService.traineeRequestSkill(requestDto);
		return ResponseEntity.ok(saveRequestSkill);
	}
	
	/**
	 * Endpoint to process a skill request by a trainer.
	 * 
	 * @param requestResponseDto The request response data.
	 * @param value The processing value.
	 * @return A response entity with no content.
	 */
	@PostMapping("/ProcessSkillRequest/{value}")
	public ResponseEntity<Void> processSkillRequest(@RequestBody RequestPublicDto requestResponseDto, @PathVariable boolean value){
		requestService.trainerProcessSkillRequest(requestResponseDto, value);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Endpoint to allow a user to request authentication.
	 * 
	 * @param requestDto The request data.
	 * @param authentication The authentication object.
	 * @return The saved authentication request data.
	 * @throws ParseException if there is an error parsing the date.
	 */
	@PostMapping("/RequestAuthentication")
	public ResponseEntity<RequestPublicDto> requestAuthentication(@RequestBody RequestDto requestDto, Authentication authentication) throws ParseException{
		String email = authentication.getName();
		requestDto.setSenderEmail(email);
		RequestPublicDto saveRequestAuthentication = requestService.requestAuthentication(requestDto);
		return ResponseEntity.ok(saveRequestAuthentication);
	}
	
	/**
	 * Endpoint to process an authentication request.
	 * 
	 * @param requestResponseDto The request response data.
	 * @param value The processing value.
	 * @return A response entity with no content.
	 */
	@PostMapping("/ProcessRequestAuthentication/{value}")
	public ResponseEntity<Void> processAuthenticationRequest(@RequestBody RequestPublicDto requestResponseDto, @PathVariable boolean value){
		requestService.processAuthenticationRequest(requestResponseDto, value);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Endpoint to get all requests received by the authenticated user.
	 * 
	 * @param authentication The authentication object.
	 * @return A list of received request data.
	 */
	@GetMapping("/received")
	public ResponseEntity<List<RequestPublicDto>> getAllRequestReceivedByUser(Authentication authentication){
		String email = authentication.getName();
		List<RequestPublicDto> requestPublicDto = requestService.getRequestByReceiver(email);
		return ResponseEntity.ok(requestPublicDto);
	}
	
	/**
	 * Endpoint to get all requests sent by the authenticated user.
	 * 
	 * @param authentication The authentication object.
	 * @return A list of sent request data.
	 */
	@GetMapping("/sent")
	public ResponseEntity<List<RequestPublicDto>> getAllRequestSentByUser(Authentication authentication){
		String email = authentication.getName();
		List<RequestPublicDto> requestPublicDto = requestService.getRequestBySender(email);
		return ResponseEntity.ok(requestPublicDto);
	}
	
	/**
	 * Endpoint to delete a request by its ID.
	 * 
	 * @param id The ID of the request to be deleted.
	 * @return A response entity with no content.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteRequest(@PathVariable UUID id){
		requestService.deleteRequestById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}