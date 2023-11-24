package com.fdmgroup.skillify.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fdmgroup.skillify.dto.quiz.QuizDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class PlacementService {

	
	@Autowired
	PlacementRepository placementRepository;
	
	@Autowired 
	AppUserRepository appUserRepository;
	
	@Autowired
	SkillRepository skillsRepository;
	
	/**
	 * Retrieves all non-expired placements
	 * @return List of non-expired placements
	 */
	public List<Placement> getAllNonExpiredPlacement(){
		return placementRepository.findAllNonExpiredPlacement();
		
	}
	
	/**
	 * Retrieves a placement by its unique ID
	 * @param id The Id of the placement to retrieve
	 * @return The placement with the given ID, or null
	 */
	public Placement getPlacementById(UUID id){ 

		Optional<Placement> placement = placementRepository.findById(id);
		
		if(placement.isPresent())			
			return placement.get();
		else 
			return null;
	}
	
	
	/**
	 * Saves a new placement based on the placeRequestDto
	 * @param placementRequestDto The DTO that contains the information that needs to be saved
	 * @return The PlacementResponseDto containing the information of the saved placement.
	 * @throws ParseException 
	 */
	public PlacementResponseDto savePlacement(PlacementRequestDTO placementRequestDto) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(placementRequestDto.getExpiredDate());
        Timestamp timestamp = new Timestamp(date.getTime());
		
		//create placement and store the data
		Placement placement = new Placement();
		placement.setTitle(placementRequestDto.getTitle());
		placement.setDescription(placementRequestDto.getDescription());
		placement.setCompanyName(placementRequestDto.getCompanyName());
		placement.setExpiredDate(timestamp);
		
		//retrieve the author's email from the PlacementRequestDTO
		String authorEmail = placementRequestDto.getAuthorEmail();
				
		//Find the author in the database by email and check if the author exists
		Optional<AppUser> author = appUserRepository.findByEmail(authorEmail);
		 if (author.isEmpty()) {
	            throw new RuntimeException("Author with email not found: " + authorEmail);
	        }
		 placement.setAuthor(author.get());
		 

	        placement.setSkills(placementRequestDto.getSkillNames());
	        
	
	        System.out.println(placement);
	        

	        placementRepository.save(placement);
	        
	        PlacementResponseDto placementResponseDto = new PlacementResponseDto();
	        placementResponseDto.setId(placement.getId());
	        placementResponseDto.setTitle(placement.getTitle());
	        placementResponseDto.setDescription(placement.getDescription());
	        placementResponseDto.setCompanyName(placement.getCompanyName());
	        placementResponseDto.setAuthorEmail(author.get().getEmail());
	        return placementResponseDto;	 

	}
	
	/**
	 * Deletes a placement with the specified ID
	 * @param placementId The ID of the placement to delete
	 */
	public void deletePlacement(UUID placementId ) {
		placementRepository.deleteById(placementId);
	}
	


	/**
	 * Updates an existing placement information
	 * @param placementId The ID of the placement to update
	 * @param updatedPlacementDto The DTO containing updated placement information
	 * @return The UpdatedPlacementDto containing the updated placement's information.
	 */
	public UpdatedPlacementDto updatePlacement(UUID placementId, UpdatedPlacementDto updatedPlacementDto) {
	    Optional<Placement> placementOptional = placementRepository.findById(placementId);
	    if (placementOptional.isEmpty()) {
	        throw new RuntimeException("Placement not found for ID: " + placementId);
	    }

	    Placement existingPlacement = placementOptional.get();
	    existingPlacement.setTitle(updatedPlacementDto.getTitle());
	    existingPlacement.setDescription(updatedPlacementDto.getDescription());
	    existingPlacement.setCompanyName(updatedPlacementDto.getCompanyName());
	    existingPlacement.setExpiredDate(updatedPlacementDto.getExpiredDate());


	    existingPlacement.setSkills(updatedPlacementDto.getSkillNames());
	    placementRepository.save(existingPlacement);

	    
	    return updatedPlacementDto; 

	} 
	
	/**
	 * Searches for placements using a keyword in various fields
	 * @param keyword SearchTerm
	 * @return A list of Placements contain the SearchTerm in  title, company name, author name or skill
	 */
	public List<SearchPlacementDto> searchPlacement(String keyword){
		
		List<Placement> placementList = placementRepository.filterByAuthorOrTitleOrCompanyOrSkill(keyword);
		List<SearchPlacementDto> placementDtoList = new ArrayList<SearchPlacementDto>();
		
		for (Placement placement : placementList) {
			SearchPlacementDto searchPlacementDto = new SearchPlacementDto();
			searchPlacementDto.setTitle(placement.getTitle());
			searchPlacementDto.setCompanyName(placement.getCompanyName());
			searchPlacementDto.setId(placement.getId());
			searchPlacementDto.setSkillNames(skillsRepository.findSkillNamesBYPlacement(placement.getId()));
			searchPlacementDto.setAuthorEmail(placement.getAuthor().getEmail());
			searchPlacementDto.setAuthorFirstName(placement.getAuthor().getFirstName());
			searchPlacementDto.setAuthorLastName(placement.getAuthor().getLastName());
			searchPlacementDto.setExpiredDate(placement.getExpiredDate().toString());
			placementDtoList.add(searchPlacementDto);
		}
		return placementDtoList;
	}

	/**
	 *Retrieves placements associated with a specific author's email
	 * @param email The email of the author
	 * @return A list of PlacementResponseDto containing placements by the author.
	 */
    public List<PlacementResponseDto> getPlacementsByAuthorEmail(String email) {
		List<Placement> placements = placementRepository.findByAuthor_Email(email);
		return placements.stream().map(this::convertToPlacementResponseDto).collect(Collectors.toList());
    }
    
    /**
     * Converts a placement object to a placementResponseDto object
     * @param placement The placement object to convert
     * @return The corresponding PlacementResponseDto
     */
	public PlacementResponseDto convertToPlacementResponseDto(Placement placement) {
		PlacementResponseDto placementResponseDto = new PlacementResponseDto();
		placementResponseDto.setTitle(placement.getTitle());
		placementResponseDto.setCompanyName(placement.getCompanyName());
		placementResponseDto.setId(placement.getId());
		placementResponseDto.setSkillNames(skillsRepository.findSkillNamesBYPlacement(placement.getId()));
		placementResponseDto.setAuthorEmail(placement.getAuthor().getEmail());
		placementResponseDto.setExpiredDate(placement.getExpiredDate().toString());
		return placementResponseDto;
	}
}
