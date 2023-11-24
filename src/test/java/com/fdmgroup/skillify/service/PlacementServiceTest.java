package com.fdmgroup.skillify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import com.fdmgroup.skillify.dto.placement.PlacementRequestDTO;
import com.fdmgroup.skillify.dto.placement.PlacementResponseDto;
import com.fdmgroup.skillify.dto.placement.SearchPlacementDto;
import com.fdmgroup.skillify.dto.placement.UpdatedPlacementDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.Placement;
import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.repository.AppUserRepository;
import com.fdmgroup.skillify.repository.PlacementRepository;
import com.fdmgroup.skillify.repository.SkillRepository;
import com.fdmgroup.skillify.service.PlacementService;
@SpringBootTest
class PlacementServiceTest {
	
	@MockBean
	private PlacementRepository placementRepository;
	@MockBean
    private AppUserRepository appUserRepository;
	@MockBean
    private SkillRepository skillRepository;
	
	@Autowired
	private PlacementService placementService;

	@BeforeEach
	void setUp() throws Exception {
	}
	
	
	  @Test
	    void testSavePlacement_Successful() throws ParseException {
	        // Mock data
	        String authorEmail = "test@example.com";
	       

	        PlacementRequestDTO placementRequestDto = new PlacementRequestDTO(); 

	        placementRequestDto.setAuthorEmail(authorEmail);

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = dateFormat.parse("2023-08-31");
	        placementRequestDto.setExpiredDate(dateFormat.format(date));

	        Set<Skill> skillNames = new HashSet<>();
	        
	        placementRequestDto.setSkillNames(skillNames);

	        AppUser author = new AppUser();

	        author.setEmail(authorEmail);
	        when(appUserRepository.findByEmail(authorEmail)).thenReturn(Optional.of(author));


	  
	        PlacementResponseDto placementResponseDto = placementService.savePlacement(placementRequestDto);

	   
	        assertNotNull(placementResponseDto);  
	        assertEquals(author.getEmail(), placementResponseDto.getAuthorEmail());

	        verify(appUserRepository).findByEmail(authorEmail);
	
	    }

	  @Test
	    void testSavePlacement_AuthorNotFound() throws ParseException  {
	
	        String authorEmail = "nonexistent@example.com";

	        PlacementRequestDTO placementRequestDto = new PlacementRequestDTO();
	        placementRequestDto.setAuthorEmail(authorEmail);

	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = dateFormat.parse("2023-08-31");
	        placementRequestDto.setExpiredDate(dateFormat.format(date));

	        
	        when(appUserRepository.findByEmail(authorEmail)).thenReturn(Optional.empty());

	        assertThrows(RuntimeException.class, () -> placementService.savePlacement(placementRequestDto));

	        verify(appUserRepository).findByEmail(authorEmail);
	  }


	    
	    @Test
	    void testGetPlacementById() {
	    	UUID placementId = UUID.randomUUID();
	    	Placement mockPlacement = new Placement();
	    	mockPlacement.setId(placementId);
	    	
	    	when(placementRepository.findById(placementId)).thenReturn(Optional.of(mockPlacement));
	    	
	    	Placement result = placementService.getPlacementById(placementId);
	    	
	    	verify(placementRepository, times(1)).findById(placementId);
	    	
	    	assertNotNull(result);
	    	assertEquals(placementId, result.getId());
	    	
	    }
	    
	    @Test
	    void testGetPlacementById_notExist() {
	    	UUID placementId = UUID.randomUUID();
	   
	    	
	    	when(placementRepository.findById(placementId)).thenReturn(Optional.empty());
	    	
	    	Placement result = placementService.getPlacementById(placementId);
	    	
	    	verify(placementRepository, times(1)).findById(placementId);
	    	
	    	assertNull(result);
	
	    	
	    }


    
    @Test
    void testDeletePlacementById() {
    	UUID placementId = UUID.randomUUID();
    	
    	placementService.deletePlacement(placementId);
    	verify(placementRepository).deleteById(placementId);
    }

    @Test
    void testUpdatePlacement_Success() {
    	UUID placementId = UUID.randomUUID();
    	UpdatedPlacementDto updatedPlacementDto = new UpdatedPlacementDto();
    	updatedPlacementDto.setTitle("Updated Title");


    	Placement existingPlacement = new Placement();
    	existingPlacement.setId(placementId);
   

    	when(placementRepository.findById(placementId)).thenReturn(Optional.of(existingPlacement));


    	UpdatedPlacementDto result = placementService.updatePlacement(placementId, updatedPlacementDto);

    	verify(placementRepository).findById(placementId);
    	verify(placementRepository).save(existingPlacement);

    	assertEquals(updatedPlacementDto.getTitle(), result.getTitle());
  
    }
    @Test
    void testUpdatePlacement_notExist() {
    	UUID placementId = UUID.randomUUID();
    	UpdatedPlacementDto updatedPlacementDto = new UpdatedPlacementDto();
    	updatedPlacementDto.setTitle("Updated Title");
    	
    	when(placementRepository.findById(placementId)).thenReturn(Optional.empty());
    		
    	assertThrows(RuntimeException.class, () -> placementService.updatePlacement(placementId, updatedPlacementDto));
    	
    	verify(placementRepository).findById(placementId);
    
    	
    	
    }
    
    
    @Test
    void testgetAllNonExpiredPlacement() {
    	Placement placement1 = new Placement();
    	Placement placement2 = new Placement();
	  
    	List<Placement> placementList = new ArrayList<>();
    	placementList.add(placement1);
    	placementList.add(placement2);
	  
    	when(placementRepository.findAllNonExpiredPlacement()).thenReturn(placementList);
	  
    	List<Placement> result = placementService.getAllNonExpiredPlacement();
	  
    	verify(placementRepository, times(1)).findAllNonExpiredPlacement();
    	assertEquals(placementList, result);
	  
    }

    @Test
    public void testSearchPlacement() {
        String keyword = "searchKeyword";
        
        // Create user
        AppUser mockUser = new AppUser();
        mockUser.setEmail("test@test.com");

        List<Placement> mockPlacementList = new ArrayList<>();
        Placement placement1 = new Placement();
        placement1.setId(UUID.randomUUID());
        placement1.setTitle("Test Title 1");
        placement1.setAuthor(mockUser);
     
        placement1.setExpiredDate(new Timestamp(0));
        mockPlacementList.add(placement1);

        when(placementRepository.filterByAuthorOrTitleOrCompanyOrSkill(keyword)).thenReturn(mockPlacementList);

        List<SearchPlacementDto> result = placementService.searchPlacement(keyword);


        assertEquals(1, result.size());
        assertEquals("Test Title 1", result.get(0).getTitle());
        verify(placementRepository, times(1)).filterByAuthorOrTitleOrCompanyOrSkill(keyword);
        assertEquals("test@test.com", result.get(0).getAuthorEmail());
      
    }
    
    @Test
    void testGetPlacementByAuthorEmail() {
    	String placementEmail = "test@test.com";
    	AppUser mockUser = new AppUser();
    	mockUser.setEmail(placementEmail);
    	
    	List<Placement> mockPlacements = new ArrayList<>();
    	Placement placement1 = new Placement();
    	placement1.setAuthor(mockUser);
    	placement1.setExpiredDate(new Timestamp(0));
    	
    	mockPlacements.add(placement1);
    	
    	when(placementRepository.findByAuthor_Email(placementEmail)).thenReturn(mockPlacements);
    	
    	List<PlacementResponseDto> result = placementService.getPlacementsByAuthorEmail(placementEmail);
    	
    	verify(placementRepository, times(1)).findByAuthor_Email(placementEmail);
    	
    	assertNotNull(result);
    	assertEquals(placementEmail, result.get(0).getAuthorEmail());
    	
    }
    @Test
    public void testConvertToPlacementResponseDto() {
        Placement mockPlacement = new Placement();
        AppUser author = new AppUser();
        
        author.setEmail("test@test.com");
        mockPlacement.setAuthor(author);
        Timestamp expiredTimestamp = new Timestamp(0); 
        mockPlacement.setExpiredDate(expiredTimestamp); 
        
        PlacementResponseDto result = placementService.convertToPlacementResponseDto(mockPlacement);

        assertEquals("test@test.com", result.getAuthorEmail());
        assertNotNull(result.getExpiredDate());
    }
}






