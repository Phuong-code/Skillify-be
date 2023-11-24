
package com.fdmgroup.skillify.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.dto.Request.RequestDto;
import com.fdmgroup.skillify.dto.Request.RequestPublicDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.Request;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.enums.ProficiencyLevel;
import com.fdmgroup.skillify.enums.RequestType;
import com.fdmgroup.skillify.repository.AppUserRepository;
import com.fdmgroup.skillify.repository.RequestRepository;
import com.fdmgroup.skillify.repository.SkillRepository;


/**
 * This class defines service methods related to Request.
 */
@Service
public class RequestService {

	@Autowired
	RequestRepository requestRepository;	
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	SkillRepository skillRepository;
	
	/**
	 * This method saves a Request entity
	 * @param request Request object
	 */
	public void saveRequest(Request request) {	
		requestRepository.save(request);
	}
	
	/**
	 * This method updates a Request entity
	 * @param request Request object
	 */
	public void updateRequest(Request request) {	
		requestRepository.save(request);
	}
	
	/**
	 * This method deletes a Request object by id
	 * @param id Request UUID
	 */
	public void deleteRequestById(UUID id) {	
		requestRepository.deleteById(id);
	}
	
	/**
	 * This method gets all Requests objects
	 * @return A list of all Request objects
	 */
	public List<Request> getAllRequests(){	
		return requestRepository.findAll(); 
	}
	
	/**
	 * This method gets a Requests object by given id
	 * @param id Request UUID
	 * @return A Request Object by given id
	 */
	public Request getRequestById(UUID id) {	
		Optional<Request> request = requestRepository.findById(id);
		
		if(request.isPresent())			
			return request.get();
		else 
			return null;
	}
	
	/**
	 * This method returns all requests received by current users, and converts it into Dto
	 * @param receiver_email String, receiver's email
	 * @return List of RequestPublicDto
	 */
	public List<RequestPublicDto> getRequestByReceiver(String receiver_email){
		List<Request> requests = requestRepository.findRequestByReceiverEmail(receiver_email);
		List<RequestPublicDto> requestPublicDtos = new ArrayList<RequestPublicDto>();
		
		for(Request request : requests) {
			RequestPublicDto requestPublicDto = new RequestPublicDto();
			requestPublicDto.setRequestId(request.getId());
			requestPublicDto.setContent(request.getContent());
			requestPublicDto.setSenderEmail(request.getSender().getEmail());
			requestPublicDto.setType(request.getType());
			requestPublicDto.setContent(request.getContent());		
			requestPublicDto.setCreatedDate(request.getCreatedDate().toString());
			requestPublicDto.setSenderName(request.getSender().getFirstName() + " " + request.getSender().getLastName());
			requestPublicDtos.add(requestPublicDto);			
		}
		return requestPublicDtos;
	}
	
	/**
	 * This method returns all requests sent by current users, and converts it into Dto
	 * @param sender_email String, sender's email
	 * @return List of RequestPublicDto
	 */
	public List<RequestPublicDto> getRequestBySender(String sender_email){
		List<Request> requests = requestRepository.findRequestBySenderEmail(sender_email);
		List<RequestPublicDto> requestPublicDtos = new ArrayList<RequestPublicDto>();
		
		for(Request request : requests) {
			RequestPublicDto requestPublicDto = new RequestPublicDto();
			requestPublicDto.setRequestId(request.getId());
			requestPublicDto.setContent(request.getContent());
			requestPublicDto.setReceiverEmail(request.getReceiver().getEmail());
			requestPublicDto.setType(request.getType());
			requestPublicDto.setContent(request.getContent());		
			requestPublicDto.setCreatedDate(request.getCreatedDate().toString());
			requestPublicDto.setReceiverName(request.getReceiver().getFirstName() + " " + request.getReceiver().getLastName());
			requestPublicDtos.add(requestPublicDto);			
		}
		return requestPublicDtos;
	}
	
	/**
	 * This method returns all requests by give type(skill, authentication)
	 * @param type enum RequestType
	 * @return List of request objects
	 */
	public List<Request> getRequestByType(RequestType type){
		
		return requestRepository.findRequestByType(type);
	}
	
	/**
	 * This method let trainee send a request to a trainer for suggesting a new skill
	 * @param requestDto RequestDto 
	 * @return  RequestPublicDto 
	 * @throws ParseException
	 */
	public RequestPublicDto traineeRequestSkill(RequestDto requestDto) throws ParseException {    
		
        Request request = new Request();
        request.setType(RequestType.SKILL_ADDITION);
        request.setContent(requestDto.getContent());      
        String senderEmail = requestDto.getSenderEmail();
        String receiverEmail = requestDto.getReceiverEmail();    
        Optional<AppUser> sender = appUserRepository.findByEmail(senderEmail);
        Optional<AppUser> receiver = appUserRepository.findByEmail(receiverEmail);
        
        if(sender.isEmpty() && receiver.isEmpty()) {
        	throw new RuntimeException("Sender and receiver with email is not found");
        }
        
        request.setSender(sender.get());
        request.setReceiver(receiver.get());      
        requestRepository.save(request);
        
        RequestPublicDto requestPublicDto = new RequestPublicDto();      
        requestPublicDto.setRequestId(request.getId());
        requestPublicDto.setContent(requestDto.getContent());
        requestPublicDto.setReceiverEmail(receiverEmail);
        requestPublicDto.setSenderEmail(senderEmail);
        requestPublicDto.setType(RequestType.SKILL_ADDITION);
        
        return requestPublicDto;

	}
	
	/**
	 * This method let trainer respond trainee's request of adding a new skill set
	 * If request is accepted, skill will be added with all proficiency levels, and request will be deleted
	 * If request is denied, request will be deleted
	 * A notification will be sent to trainee contains information of accepting or rejection of a skill
	 * @param requestResponseDto
	 * @param isAccepted boolean
	 */
	public void trainerProcessSkillRequest(RequestPublicDto requestResponseDto, Boolean isAccepted) {
		
		Request request = requestRepository.findById(requestResponseDto.getRequestId()).get();
	
		//If request is accepted, add new skill into database
		if ((isAccepted == true) && (skillRepository.countSkillByName(requestResponseDto.getContent()) == 0)) {
			
			for (ProficiencyLevel proficiencyLevel : ProficiencyLevel.values()) {
				Skill skill = new Skill();
				skill.setProficiency(proficiencyLevel );
				skill.setName(requestResponseDto.getContent());
				skill.setDescription(requestResponseDto.getContent()+ " " + proficiencyLevel.name());
				skillRepository.save(skill);
			}
		}

		//Sent notification to trainee
		 Request notification = new Request();
		 notification.setType(RequestType.Notification);
		 notification.setReceiver(request.getSender());
		 notification.setSender(request.getReceiver());
		 notification.setContent(isAccepted.toString());	 
		 System.out.println(notification);
		 requestRepository.save(notification);
		 
		 requestRepository.deleteById(request.getId());
	}
	
	/**
	 * This method let trainer/sales send other trainer/sales request for approving authentication
	 * @param requestDto
	 * @return RequestPublicDto
	 * @throws ParseException
	 */
	public RequestPublicDto requestAuthentication(RequestDto requestDto) throws ParseException {

        String senderEmail = requestDto.getSenderEmail();
        if(requestRepository.findAuthenticationRequestBySenderEmail(senderEmail) == null) {
            String receiverEmail = requestDto.getReceiverEmail();         
            Optional<AppUser> sender = appUserRepository.findByEmail(senderEmail);
            Optional<AppUser> receiver = appUserRepository.findByEmail(receiverEmail);
            
            if(sender.isEmpty() && receiver.isEmpty()) {
            	throw new RuntimeException("Sender and receiver with email is not found");
            }
            
            Request request = new Request();
            request.setType(RequestType.AUTHORIZATION);
            request.setSender(sender.get());
            request.setReceiver(receiver.get());            
            requestRepository.save(request);
            
            RequestPublicDto requestResponseDto = new RequestPublicDto();           
            requestResponseDto.setRequestId(request.getId());
            requestResponseDto.setReceiverEmail(receiverEmail);
            requestResponseDto.setSenderEmail(senderEmail);
            requestResponseDto.setType(RequestType.AUTHORIZATION);
            
            return requestResponseDto;
        }else
        	throw new RuntimeException("Request sent already");

	}
	
	/**
	 * This method let trainer/sales approve other trainer/sales's authentication
	 * If request is approved, sender 's status will be set to authenticated, and  request will be deleted
	 * If request is denied, request will be deleted
	 * @param requestResponseDto
	 * @param isAccepted boolean
	 */
	public void processAuthenticationRequest(RequestPublicDto requestResponseDto, Boolean isAccepted) {
		
		if (isAccepted == true) {
			
			Optional<AppUser> sender = appUserRepository.findByEmail(requestResponseDto.getSenderEmail());
			
			if(sender.isEmpty()) {
				throw new RuntimeException("Sender email is not found");
			}else {
				AppUser user = sender.get();
				user.setAuthenticated(true);	
				appUserRepository.save(user);
			}	
		}
	    requestRepository.deleteById(requestResponseDto.getRequestId());
		
	}
	
}
