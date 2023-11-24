package com.fdmgroup.skillify.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fdmgroup.skillify.dto.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.enums.RoleType;
import com.fdmgroup.skillify.repository.AppUserRepository;
import com.fdmgroup.skillify.repository.SkillRepository;



@Service
public class AppUserService {

	AppUserRepository appUserRepository;
	SkillRepository skillRepository;
	PasswordEncoder passwordEncoder;

	@Autowired
	public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder,
			SkillRepository skillRepository) {
		this.appUserRepository = appUserRepository;
		this.skillRepository = skillRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * create a User entity
	 * @param userRegisterDto
	 * @return A AppUser object representing the saved entity
	 * @see UserPublicDto
	 */
	public UserPublicDto createUser(UserRegisterDto userRegisterDto) {
		AppUser user = new AppUser();
		user.setEmail(userRegisterDto.getEmail());
		user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
		user.setRole(userRegisterDto.getRole());

		if (userRegisterDto.getFirstName()!= null && !userRegisterDto.getFirstName().isEmpty())
			user.setFirstName(userRegisterDto.getFirstName());
		if (userRegisterDto.getLastName()!= null && !userRegisterDto.getLastName().isEmpty())
			user.setLastName(userRegisterDto.getLastName());

		appUserRepository.save(user);
		UserPublicDto userPublicDto = new UserPublicDto();
		userPublicDto.setId(user.getId());
		userPublicDto.setEmail(user.getEmail());
		userPublicDto.setFirstName(user.getFirstName());
		userPublicDto.setLastName(user.getLastName());
		userPublicDto.setRole(user.getRole());
		return userPublicDto;
	}
	

	
	/**
	 * Update a AppUser entity
	 * @param user
	 */
	public UserProfileUpdateDto updateAppUser(UUID id, UserProfileUpdateDto userProfileUpdateDto) {
		
		AppUser user = getUserById(id);
		user.setEmail(userProfileUpdateDto.getEmail());
		user.setFirstName(userProfileUpdateDto.getFirstName());
		user.setLastName(userProfileUpdateDto.getLastName());

		appUserRepository.save(user);
		
		return userProfileUpdateDto;
		
	}
	
	/**
	 * Update a AppUser password
	 * @param user
	 */
	public UserPasswordUpdateDto updateAppUserPassword(UUID id, UserPasswordUpdateDto userPasswordUpdateDto) {
		
		AppUser user = getUserById(id);
		if (passwordEncoder.matches(userPasswordUpdateDto.getOldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(userPasswordUpdateDto.getNewPassword()));
			appUserRepository.save(user);
		}
		
		return userPasswordUpdateDto;
		
	}
	
	/**
	 * Delete a AppUser object by given id
	 * @param id
	 */
	public void deleteUserById(UUID id) {
		
		appUserRepository.deleteById(id);
	}
	
//	/**
//	 * 
//	 * @return A list of all AppUser objects
//	 */
//	public List<AppUser> getAllAppUsers(){
//		
//		return appUserRepository.findAll(); 
//	}
	
	/**
	 * 
	 * @param id AppUser Id
	 * @return A AppUser Object by given id
	 */
	public AppUser getUserById(UUID id) {
		
		Optional<AppUser> user = appUserRepository.findById(id);
		
		if(user.isPresent())			
			return user.get();
		else 
			return null;
	}

	public AppUser getUserByEmail(String email) {
		return appUserRepository.findByEmail(email).orElseThrow();
	}

	/**
	 *
	 * Delete All AppUser Objects
	 * @return number of objects deleted
	 */
	public int deleteAllAppUser() {

		int count = 0;
		List<AppUser> appUsers = appUserRepository.findAll();

		for(AppUser appUser : appUsers) {
			appUserRepository.delete(appUser);
			count++;
		}

		return count;

	}

	/**
	 * 
	 * @param keyword SearchTerm
	 * @return A list of UserPublicDto contain the SearchTerm in their first name or last name
	 */
	public List <UserPublicDto> searchByUserName(String keyword){
		
		List<AppUser> userList = appUserRepository.filterByUserName(keyword);
		List <UserPublicDto> userPublicDtoList = new ArrayList<UserPublicDto>();	
		
		for (AppUser user : userList) {			
			UserPublicDto userPublicDto = new UserPublicDto();
						
			if (user.getRole().equals(RoleType.TRAINEE)){
				userPublicDto.setSkillNames(skillRepository.findSkillNamesBYTrainee(user.getId()));
			}
		
			userPublicDto = convertUserPublicDto(user);
			userPublicDtoList.add(userPublicDto);

		}
		return userPublicDtoList;
	}
	
	public List <UserPublicDto> searchByRole(RoleType role){
		
		List<AppUser> userList = appUserRepository.filterByRole(role);
		List <UserPublicDto> userPublicDtoList = new ArrayList<UserPublicDto>();	
		
		for (AppUser user : userList) {			
			UserPublicDto userPublicDto = convertUserPublicDto(user);						
			userPublicDtoList.add(userPublicDto);
		}
		return userPublicDtoList;
	}
	
	/**
	 * This method convert a AppUser object to userPublicDto
	 * @param user
	 * @return
	 */
	public UserPublicDto convertUserPublicDto(AppUser user) {
		
		UserPublicDto userPublicDto = new UserPublicDto();
		
		userPublicDto.setId(user.getId());
		userPublicDto.setEmail(user.getEmail());
		userPublicDto.setFirstName(user.getFirstName());
		userPublicDto.setLastName(user.getLastName());
		userPublicDto.setRole(user.getRole());
		
		return userPublicDto;
	}

	public List<AppUser> getAllTrainees() {
		return appUserRepository.getAllTrainees();
		
	}
	
	public Optional<AppUser> getTraineeById(UUID id) {
		return appUserRepository.findById(id);
	}
	
	public List<String> getAutheticatedTrainerOrSalesEmail(@Param("role") RoleType role){
		return appUserRepository.findAutheticatedTrainerOrSalesEmail(role);
	}
}
