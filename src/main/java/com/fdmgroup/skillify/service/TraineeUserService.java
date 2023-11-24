package com.fdmgroup.skillify.service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.fdmgroup.skillify.dto.user.TraineePasswordUpdateDto;
import com.fdmgroup.skillify.dto.user.TraineePublicDto;
import com.fdmgroup.skillify.dto.user.TraineeUpdateDto;
import com.fdmgroup.skillify.dto.user.UserPublicDto;
import com.fdmgroup.skillify.dto.user.UserRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.fdmgroup.skillify.entity.Skill;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.enums.StatusType;
import com.fdmgroup.skillify.repository.SkillRepository;
import com.fdmgroup.skillify.repository.TraineeUserRepository;

@Service
public class TraineeUserService {

	private final PasswordEncoder passwordEncoder;
	private final TraineeUserRepository traineeUserRepository;
	private SkillRepository skillRepository;
	
	@Autowired
	public TraineeUserService(PasswordEncoder passwordEncoder, TraineeUserRepository traineeUserRepository,
			SkillRepository skillRepository) {
		this.passwordEncoder = passwordEncoder;
		this.traineeUserRepository = traineeUserRepository;
		this.skillRepository = skillRepository;
	}

	/**
	 * This method is used to create a trainee user
	 * @param userRegisterDto
	 * @return
	 */
	public UserPublicDto createTrainee(UserRegisterDto userRegisterDto) {
		TraineeUser traineeUser = new TraineeUser();
		traineeUser.setEmail(userRegisterDto.getEmail());
		traineeUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
		traineeUser.setRole(userRegisterDto.getRole());
		traineeUser.setStatus(StatusType.TRAINING);
		traineeUser.setAuthenticated(true);

		if (userRegisterDto.getFirstName()!= null && !userRegisterDto.getFirstName().isEmpty())
			traineeUser.setFirstName(userRegisterDto.getFirstName());
		if (userRegisterDto.getLastName()!= null && !userRegisterDto.getLastName().isEmpty())
			traineeUser.setLastName(userRegisterDto.getLastName());

		traineeUserRepository.save(traineeUser);
		UserPublicDto userPublicDto = new UserPublicDto();
		userPublicDto.setId(traineeUser.getId());
		userPublicDto.setEmail(traineeUser.getEmail());
		userPublicDto.setFirstName(traineeUser.getFirstName());
		userPublicDto.setLastName(traineeUser.getLastName());
		userPublicDto.setRole(traineeUser.getRole());
		userPublicDto.setSkillNames(skillRepository.findSkillNamesBYTrainee(traineeUser.getId()));
		
		return userPublicDto;
	}

	/**
	 * This method is used to update the status of a trainee user
	 * @param id
	 * @param type
	 * @return
	 */
	public TraineePublicDto updateStatus(UUID id, StatusType type) {
		TraineePublicDto traineePublicDto = new TraineePublicDto();
		TraineeUser traineeUser = (TraineeUser) traineeUserRepository.findById(id).get();
	
		traineePublicDto.setEmail(traineeUser.getEmail());
		traineePublicDto.setFirstName(traineeUser.getFirstName());
		traineePublicDto.setLastName(traineeUser.getLastName());
		traineePublicDto.setSkillNames(skillRepository.findSkillNamesBYTrainee(traineeUser.getId()));		
		traineePublicDto.setType(type);
		
		return traineePublicDto;
	}

	/**
	 * This method is used to update the status of a trainee user which also updates the authentication status
	 * @param id
	 * @param type
	 * @return
	 */
	public AppUser updateTraineeStatus(UUID id, StatusType type) {
		TraineeUser trainee = (TraineeUser) traineeUserRepository.findById(id).get();
		trainee.setStatus(type);
		if(trainee.getStatus() == StatusType.ABSENT) {
			trainee.setAuthenticated(false);
		}else {
			trainee.setAuthenticated(true);
		}
		
		return traineeUserRepository.save(trainee);
	}


	/**
	 * This method is used to update a trainee user which might include updating the status
	 * @param id
	 * @param traineeUpdateDto
	 * @return
	 */
	public AppUser updateTrainee(UUID id, TraineeUpdateDto traineeUpdateDto) {
		TraineeUser trainee = (TraineeUser) traineeUserRepository.findById(id).get();
		
		trainee.setFirstName(traineeUpdateDto.getFirstName());
		trainee.setLastName(traineeUpdateDto.getLastName());
		trainee.setEmail(traineeUpdateDto.getEmail());
		trainee.setRole(traineeUpdateDto.getRole());
		trainee.setStatus(traineeUpdateDto.getStatus());
		
		if(trainee.getStatus() == StatusType.ABSENT) {
			trainee.setAuthenticated(false);
		}else {
			trainee.setAuthenticated(true);
		}
		
		TraineeUser savedTrainee = traineeUserRepository.save(trainee);
		
		return savedTrainee;
	}

	/**
	 * This method is used to update the password of a trainee user
	 * @param id
	 * @param dto
	 */
	public void updateTraineePassword(UUID id, TraineePasswordUpdateDto dto) {
		TraineeUser trainee = (TraineeUser) traineeUserRepository.findById(id).get();

		trainee.setPassword(passwordEncoder.encode(dto.getPassword()));

		traineeUserRepository.save(trainee);
	}

	/**
	 * This method is used to get the full information of a trainee user by email
	 * @param email
	 * @return
	 */
	public TraineePublicDto getTraineeFullInfo(String email) {
		TraineePublicDto traineePublicDto = new TraineePublicDto();
		TraineeUser traineeUser = traineeUserRepository.findTraineeUserByEmail(email);
	
		traineePublicDto.setEmail(traineeUser.getEmail());
		traineePublicDto.setFirstName(traineeUser.getFirstName());
		traineePublicDto.setLastName(traineeUser.getLastName());
		
	    List<Skill> skills = skillRepository.findSkillFullInfoByTrainee(traineeUser.getId());
		List<String> skillNameAndProficiency = new ArrayList<>();
		
	    for (Skill skill : skills) {
	        String skillInfo = skill.getName() + " - " + skill.getProficiency();
	        skillNameAndProficiency.add(skillInfo);
	    }
		traineePublicDto.setSkillNames(skillNameAndProficiency);		
		traineePublicDto.setType(traineeUser.getStatus());
		
		return traineePublicDto;
	}
}
