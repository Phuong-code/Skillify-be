package com.fdmgroup.skillify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.skillify.dto.Request.RequestDto;
import com.fdmgroup.skillify.dto.Request.RequestPublicDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.Request;
import com.fdmgroup.skillify.enums.RequestType;
import com.fdmgroup.skillify.repository.AppUserRepository;
import com.fdmgroup.skillify.repository.RequestRepository;
import com.fdmgroup.skillify.repository.SkillRepository;

@SpringBootTest
class RequestServiceTest {
	@MockBean
	SkillRepository skillRepository;
	
	@MockBean
	RequestRepository requestRepository;
	
	@MockBean
	AppUserRepository appUserRepository;
	
	@Autowired
	RequestService requestService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test_TraineeRequestSkill_Successful() throws ParseException {
		String senderEmail = "sender@fdm.com";
		String receiverEmail = "receiver@fdm.com";
		
		RequestDto requestDto = new RequestDto();
		
		requestDto.setSenderEmail(senderEmail);
		requestDto.setReceiverEmail(receiverEmail);
		   
	    AppUser sender = new AppUser();
	    AppUser receiver = new AppUser();
	    
	    sender.setEmail(senderEmail);
	    receiver.setEmail(receiverEmail);
	     
	    when(appUserRepository.findByEmail(senderEmail)).thenReturn(Optional.of(sender));
	    when(appUserRepository.findByEmail(receiverEmail)).thenReturn(Optional.of(receiver));
	    
	    RequestPublicDto resultDto = requestService.traineeRequestSkill(requestDto);
	    
	    verify(appUserRepository).findByEmail(senderEmail);
	    verify(appUserRepository).findByEmail(receiverEmail);
    
	    assertNotNull(resultDto);
	    assertEquals(sender.getEmail(), resultDto.getSenderEmail());
	    assertEquals(receiver.getEmail(), resultDto.getReceiverEmail());
	    

	}
	
	@Test
	void test_TraineeRequestSkill_senderNotFound() throws ParseException {
		String senderEmail = "sender@fdm.com";
		String receiverEmail = "receiver@fdm.com";
		
		RequestDto requestDto = new RequestDto();
		
		requestDto.setSenderEmail(senderEmail);
		requestDto.setReceiverEmail(receiverEmail);
		    
	    when(appUserRepository.findByEmail(senderEmail)).thenReturn(Optional.empty());
	    when(appUserRepository.findByEmail(receiverEmail)).thenReturn(Optional.empty());
	    
	    assertThrows(RuntimeException.class, () -> requestService.traineeRequestSkill(requestDto));
	    
	    verify(appUserRepository).findByEmail(senderEmail);
	    verify(appUserRepository).findByEmail(receiverEmail);
				
	}
	
	@Test
	void test_trainerProcessSkillRequest_accept(){
		
        RequestPublicDto requestResponseDto = new RequestPublicDto();
        requestResponseDto.setRequestId(UUID.randomUUID());
        requestResponseDto.setContent("New Skill");
        
        Request request = new Request();
        request.setId(requestResponseDto.getRequestId());
        
        when(requestRepository.findById(requestResponseDto.getRequestId())).thenReturn(Optional.of(request));
        when(requestRepository.save(request)).thenReturn(request);

        requestService.trainerProcessSkillRequest(requestResponseDto, true);

        verify(requestRepository, times(1)).findById(requestResponseDto.getRequestId());
        verify(requestRepository, times(1)).deleteById(request.getId());
	}

	@Test
	void test_requestAuthentication_Successful() throws ParseException {
		
        RequestDto requestDto = new RequestDto();
        requestDto.setSenderEmail("sender@fdm.com");
        requestDto.setReceiverEmail("receiver@fdm.com");

        AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");

        Request request = new Request();
        request.setType(RequestType.AUTHORIZATION);

        when(appUserRepository.findByEmail(sender.getEmail())).thenReturn(Optional.of(sender));
        when(appUserRepository.findByEmail(receiver.getEmail())).thenReturn(Optional.of(receiver));
        when(requestRepository.save(request)).thenReturn(request);

        RequestPublicDto response = requestService.requestAuthentication(requestDto);

        assertNotNull(response);
	    assertEquals(sender.getEmail(), response.getSenderEmail());
	    assertEquals(receiver.getEmail(), response.getReceiverEmail());
        verify(appUserRepository, times(1)).findByEmail(sender.getEmail());
        verify(appUserRepository, times(1)).findByEmail(receiver.getEmail());

    }
	
	@Test
	void test_requestAuthentication_senderNotFound() throws ParseException {
		
        RequestDto requestDto = new RequestDto();
        requestDto.setSenderEmail("sender@fdm.com");
        requestDto.setReceiverEmail("receiver@fdm.com");

        AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");

        Request request = new Request();
        request.setType(RequestType.AUTHORIZATION);

        when(appUserRepository.findByEmail(sender.getEmail())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> requestService.requestAuthentication(requestDto));
        
        verify(appUserRepository, times(1)).findByEmail(sender.getEmail());
	    				
	}
	
	@Test
	void test_requestAuthentication_request_already_exists() throws ParseException {
		
	    AppUser sender = new AppUser();
	    sender.setEmail("sender@fdm.com");
	    
        RequestDto requestDto = new RequestDto();
        requestDto.setSenderEmail(sender.getEmail());

		Request request = new Request();
		request.setSender(sender);
		request.setId(UUID.randomUUID());
		
		when(requestRepository.findAuthenticationRequestBySenderEmail(sender.getEmail())).thenReturn(request);
		
		assertThrows(RuntimeException.class, () -> requestService.requestAuthentication(requestDto));
		
		verify(requestRepository, times(1)).findAuthenticationRequestBySenderEmail(sender.getEmail());
	}
	
    @Test
    public void test_ProcessAuthenticationRequest_Approved() {
    	
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        RequestPublicDto requestResponseDto = new RequestPublicDto();
        requestResponseDto.setRequestId(UUID.randomUUID());
        requestResponseDto.setSenderEmail("sender@fdm.com");

        when(appUserRepository.findByEmail(requestResponseDto.getSenderEmail())).thenReturn(Optional.of(sender));
        when(appUserRepository.save(sender)).thenReturn(sender);
        
        requestService.processAuthenticationRequest(requestResponseDto, true);

        verify(appUserRepository, times(1)).save(sender);
        verify(appUserRepository, times(1)).findByEmail(requestResponseDto.getSenderEmail());
        verify(requestRepository, times(1)).deleteById(requestResponseDto.getRequestId());

    }
    
    @Test
    public void testProcessAuthenticationRequest_sender_NotFound() {
    	
        RequestPublicDto requestResponseDto = new RequestPublicDto();

        when(appUserRepository.findByEmail(requestResponseDto.getSenderEmail())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> requestService.processAuthenticationRequest(requestResponseDto, true));

    }
    
    @Test
    public void test_GetRequestByReceiver() {
    	
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        List<Request> requests = new ArrayList<>();
        Request request1 = new Request();
        request1.setId(UUID.randomUUID());
        request1.setContent("Request Content 1");
        request1.setType(RequestType.SKILL_ADDITION);
        request1.setReceiver(receiver);
        request1.setSender(sender);
        request1.setCreatedDate(new Timestamp(100));
        requests.add(request1);
        RequestPublicDto response1 = new RequestPublicDto();
        response1.setRequestId(request1.getId());
        response1.setContent(request1.getContent());
        response1.setType(request1.getType());
        response1.setReceiverEmail(request1.getReceiver().getEmail()); 
        response1.setSenderEmail(request1.getSender().getEmail()); 
        response1.setCreatedDate(request1.getCreatedDate().toString());
                   
        Request request2 = new Request();
        request2.setId(UUID.randomUUID());
        request2.setContent("Request Content 2");
        request2.setType(RequestType.AUTHORIZATION);
        request2.setReceiver(receiver);
        request2.setSender(sender);
        request2.setCreatedDate(new Timestamp(100));
        requests.add(request2);
        RequestPublicDto response2 = new RequestPublicDto();
        response2.setRequestId(request2.getId());
        response2.setContent(request2.getContent());
        response2.setType(request2.getType());
        response2.setReceiverEmail(request2.getReceiver().getEmail()); 
        response2.setSenderEmail(request2.getSender().getEmail()); 
        response2.setCreatedDate(request2.getCreatedDate().toString());
        
        when(appUserRepository.findByEmail(receiver.getEmail())).thenReturn(Optional.of(receiver));
        when(requestRepository.findRequestByReceiverEmail(receiver.getEmail())).thenReturn(requests);

        List<RequestPublicDto> responses = requestService.getRequestByReceiver(receiver.getEmail());

        verify(requestRepository, times(1)).findRequestByReceiverEmail(receiver.getEmail());
        assertEquals(requests.size(), responses.size());
    }
    
    @Test
    public void test_GetRequestBySender() {
    	
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        List<Request> requests = new ArrayList<>();
        Request request1 = new Request();
        request1.setId(UUID.randomUUID());
        request1.setContent("Request Content");
        request1.setType(RequestType.SKILL_ADDITION);
        request1.setReceiver(receiver);
        request1.setSender(sender);
        request1.setCreatedDate(new Timestamp(100));
        requests.add(request1);
        RequestPublicDto response1 = new RequestPublicDto();
        response1.setRequestId(request1.getId());
        response1.setContent(request1.getContent());
        response1.setType(request1.getType());
        response1.setReceiverEmail(request1.getReceiver().getEmail()); 
        response1.setSenderEmail(request1.getSender().getEmail()); 
        response1.setCreatedDate(request1.getCreatedDate().toString());
                   
        Request request2 = new Request();
        request2.setId(UUID.randomUUID());
        request2.setContent("Request Content");
        request2.setType(RequestType.AUTHORIZATION);
        request2.setReceiver(receiver);
        request2.setSender(sender);
        request2.setCreatedDate(new Timestamp(100));
        requests.add(request2);
        RequestPublicDto response2 = new RequestPublicDto();
        response2.setRequestId(request2.getId());
        response2.setContent(request2.getContent());
        response2.setType(request2.getType());
        response2.setReceiverEmail(request2.getReceiver().getEmail()); 
        response2.setSenderEmail(request2.getSender().getEmail()); 
        response2.setCreatedDate(request2.getCreatedDate().toString());
        
        when(appUserRepository.findByEmail(sender.getEmail())).thenReturn(Optional.of(sender));
        when(requestRepository.findRequestBySenderEmail(sender.getEmail())).thenReturn(requests);

        List<RequestPublicDto> responses = requestService.getRequestBySender(sender.getEmail());

        verify(requestRepository, times(1)).findRequestBySenderEmail(sender.getEmail());
        assertEquals(requests.size(), responses.size());
    }
    
    @Test
    public void test_add_a_request_then_delete_it() {
    	
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        Request request = new Request();
        request.setId(UUID.randomUUID());
        request.setContent("Request Content");
        request.setType(RequestType.SKILL_ADDITION);
        request.setReceiver(receiver);
        request.setSender(sender);
        request.setCreatedDate(new Timestamp(100));
        
        when(requestRepository.save(request)).thenReturn(request);
   
        requestService.saveRequest(request);
        requestService.deleteRequestById(request.getId());
        
        verify(requestRepository, times(1)).save(request);
        verify(requestRepository, times(1)).deleteById(request.getId());

    }
    
    @Test
    public void test_get_request_by_id() {
    	
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        Request request = new Request();
        request.setId(UUID.randomUUID());
        request.setContent("Request Content");
        request.setType(RequestType.SKILL_ADDITION);
        request.setReceiver(receiver);
        request.setSender(sender);
        request.setCreatedDate(new Timestamp(100));
        
        when(requestRepository.findById(request.getId())).thenReturn(Optional.of(request));
        
        requestService.getRequestById(request.getId());
        
        verify(requestRepository, times(1)).findById(request.getId());
    }
    
    @Test
    public void test_get_request_by_id_Not_Found() {
    	      
        Request request = new Request();
        
        when(requestRepository.findById(request.getId())).thenReturn(Optional.empty());
        
        requestService.getRequestById(request.getId());
        
        verify(requestRepository, times(1)).findById(request.getId());
    }
    
    @Test
    public void test_update_request() {
    	
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        Request request = new Request();
        request.setId(UUID.randomUUID());
        request.setContent("Request Content");
        request.setType(RequestType.SKILL_ADDITION);
        request.setReceiver(receiver);
        request.setSender(sender);
        request.setCreatedDate(new Timestamp(100));
        
        Request updatedRequest = request;
        updatedRequest.setContent("New Content");
        
        when(requestRepository.save(request)).thenReturn(updatedRequest);
        
        requestService.updateRequest(request);
        
        verify(requestRepository, times(1)).save(request);
        assertEquals("New Content", updatedRequest.getContent());
    }
    
    @Test
    public void test_get_all_requests() {
    	
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        Request request = new Request();
        request.setId(UUID.randomUUID());
        request.setContent("Request Content");
        request.setType(RequestType.SKILL_ADDITION);
        request.setReceiver(receiver);
        request.setSender(sender);
        request.setCreatedDate(new Timestamp(100));
        
        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        
        when(requestRepository.findAll()).thenReturn(requests);
        
        List<Request> requestsTest = requestService.getAllRequests();
        
        verify(requestRepository, times(1)).findAll();
        assertEquals(requests, requestsTest);
    }
    
    @Test
    public void test_get_requests_by_type() {
    	
        List<Request> requests = new ArrayList<Request>();
    	AppUser receiver = new AppUser();
        receiver.setEmail("receiver@fdm.com");
    	AppUser sender = new AppUser();
        sender.setEmail("sender@fdm.com");
        
        Request request = new Request();
        request.setId(UUID.randomUUID());
        request.setContent("Request Content");
        request.setType(RequestType.SKILL_ADDITION);
        request.setReceiver(receiver);
        request.setSender(sender);
        request.setCreatedDate(new Timestamp(100));
        requests.add(request);
        
        Request request2 = new Request();
        request2.setId(UUID.randomUUID());
        request2.setContent("Request Content");
        request2.setType(RequestType.SKILL_ADDITION);
        request2.setReceiver(receiver);
        request2.setSender(sender);
        request2.setCreatedDate(new Timestamp(100));
        requests.add(request2);
        
        RequestType type = RequestType.SKILL_ADDITION;
        
        when(requestRepository.findRequestByType(type)).thenReturn(requests);
        
        List<Request> requestsTest = requestService.getRequestByType(type);
        
        verify(requestRepository, times(1)).findRequestByType(type);
        assertEquals(requests, requestsTest);
    }
}
