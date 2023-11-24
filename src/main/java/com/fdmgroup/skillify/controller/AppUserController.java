package com.fdmgroup.skillify.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.skillify.dto.user.TraineePasswordUpdateDto;
import com.fdmgroup.skillify.dto.user.TraineePublicDto;
import com.fdmgroup.skillify.dto.user.TraineeUpdateDto;
import com.fdmgroup.skillify.dto.user.UserPasswordUpdateDto;
import com.fdmgroup.skillify.dto.user.UserProfileUpdateDto;
import com.fdmgroup.skillify.dto.user.UserPublicDto;
import com.fdmgroup.skillify.dto.user.UserRegisterDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.enums.RoleType;
import com.fdmgroup.skillify.enums.StatusType;
import com.fdmgroup.skillify.service.AppUserService;
import com.fdmgroup.skillify.service.TraineeUserService;

@RestController
@RequestMapping("/api/user")
public class AppUserController {
    private AppUserService appUserService;
    private final TraineeUserService traineeUserService;

    @Autowired
    public AppUserController(AppUserService appUserService,
                             TraineeUserService traineeUserService) {
        this.appUserService = appUserService;
        this.traineeUserService = traineeUserService;
    }

    @PostMapping
    public ResponseEntity<UserPublicDto> createUser(@RequestBody UserRegisterDto userRegisterDto) {
        UserPublicDto userPublicDto;
        if (userRegisterDto.getRole().equals(RoleType.TRAINEE)) {
            userPublicDto = traineeUserService.createTrainee(userRegisterDto);
        } else {
            userPublicDto = appUserService.createUser(userRegisterDto);
        }
        return ResponseEntity.ok(userPublicDto);
    }
    
    //localhost:8080/api/user/search?keyword=a
    /**
     * This method search user by user's first or last name
     * @param keyword String
     * @return filtered User
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserPublicDto>> searchUser(@RequestParam("keyword") String keyword){

        return ResponseEntity.ok(appUserService.searchByUserName(keyword));
    }
    
    /**
     * This method get user by user's id
     * @param keyword String
     * @return User
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserPublicDto> getUser(@PathVariable UUID id){

        return ResponseEntity.ok(appUserService.convertUserPublicDto(appUserService.getUserById(id)));
    }
    
    /**
     * This method updates user's profile (email and name)
     * @param id user id
     * @param userProfileUpdateDto
     * @return updated user profile
     */
	@PutMapping("/edit-profile")
	public ResponseEntity<UserProfileUpdateDto> updateUser(@RequestBody UserProfileUpdateDto userProfileUpdateDto, Authentication authentication){
		String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
		UserProfileUpdateDto updatedUser = appUserService.updateAppUser(appUser.getId(), userProfileUpdateDto);
	    return ResponseEntity.ok(updatedUser);
	}
	
    /**
     * This method updates user's password
     * @param id user id
     * @param userProfileUpdateDto
     * @return updated user profile
     */
	@PutMapping("/edit-password")
	public ResponseEntity<UserPasswordUpdateDto> updateUserPassword(@RequestBody UserPasswordUpdateDto userPasswordUpdateDto, Authentication authentication){
		String email = authentication.getName();
        AppUser appUser = appUserService.getUserByEmail(email);
        UserPasswordUpdateDto updatedUserPassword = appUserService.updateAppUserPassword(appUser.getId(), userPasswordUpdateDto);
	    return ResponseEntity.ok(updatedUserPassword);
	}
	
	/**
	 * This method let trainer or sales update trainee's status(training,beached...)
	 * @param id user id
	 * @param type trainee status
	 * @return Trainee's Public Dto
	 */
	@PutMapping("/{id}/trainee_status")
	public ResponseEntity<TraineePublicDto> updateTraineeStatus(@PathVariable UUID id, @RequestParam("type") StatusType type){
		TraineePublicDto updatedTrainee = traineeUserService.updateStatus(id, type);
	        return ResponseEntity.ok(updatedTrainee);
	}    
	
	/**
	 * This method get trainee full information including skills and status
	 * @param id user id
	 * @param type trainee status
	 * @return Trainee's Public Dto
	 */
	@GetMapping("/trainee/{email}")
	public ResponseEntity<TraineePublicDto> getTraineeFullInfo(@PathVariable String email){
		TraineePublicDto trainee = traineeUserService.getTraineeFullInfo(email);
	        return ResponseEntity.ok(trainee);
	}   
	
	/**
	 * This method gets user by role (trainee, trainer or sales)
	 * @param role RoleType
	 * @return UserDto by user role
	 */
    @GetMapping("/filter-by/{role}")
    public ResponseEntity<List<UserPublicDto>> listByRole(@PathVariable("role") RoleType role){

        return ResponseEntity.ok(appUserService.searchByRole(role));
    }
    
    @GetMapping("/alltrainees")
    public List<AppUser> getAllTrainees(){
    	return appUserService.getAllTrainees();
    }
    
    @GetMapping("/getTrainee/{id}")
    public AppUser getTraineeById(@PathVariable UUID id) {
    	return appUserService.getTraineeById(id).get();
    }
    
    @PutMapping("/update_trainee_status/{id}")
    public AppUser updateTraineesStatus(@PathVariable UUID id, @RequestParam("type") StatusType type) {
    	return traineeUserService.updateTraineeStatus(id, type);
    }
    
    @PutMapping("/update_trainee/{id}")
    public AppUser updateTrainee(@PathVariable UUID id, @RequestBody TraineeUpdateDto traineeUpdateDto) {
    	return traineeUserService.updateTrainee(id, traineeUpdateDto);
    }
    
    @PutMapping("/update_trainee_password/{id}")
    public void updateTraineePassword(@PathVariable UUID id, @RequestBody TraineePasswordUpdateDto dto) {
    	traineeUserService.updateTraineePassword(id, dto);
    }
    
    @GetMapping("/authenticated/Trainer")
    public ResponseEntity<List<String>> getAuthenticatedTrainer(){
    	return ResponseEntity.ok(appUserService.getAutheticatedTrainerOrSalesEmail(RoleType.TRAINER));
    }
    
    @GetMapping("/authenticated/Sales")
    public ResponseEntity<List<String>> getAuthenticatedSales(){
    	return ResponseEntity.ok(appUserService.getAutheticatedTrainerOrSalesEmail(RoleType.SALES));
    }
    
    
    
    
    
}
