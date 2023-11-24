package com.fdmgroup.skillify.service;

import com.fdmgroup.skillify.dto.user.TraineePasswordUpdateDto;
import com.fdmgroup.skillify.dto.user.TraineePublicDto;
import com.fdmgroup.skillify.dto.user.TraineeUpdateDto;
import com.fdmgroup.skillify.dto.user.UserRegisterDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.entity.TraineeUser;
import com.fdmgroup.skillify.enums.RoleType;
import com.fdmgroup.skillify.enums.StatusType;
import com.fdmgroup.skillify.repository.SkillRepository;
import com.fdmgroup.skillify.repository.TraineeUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TraineeUserServiceTest {
    @MockBean
    private TraineeUserRepository mockTraineeUserRepository;

    @MockBean
    private SkillRepository mockSkillRepository;

    @MockBean
    private PasswordEncoder mockPasswordEncoder;

    @Autowired
    private TraineeUserService traineeUserService;

    @Test
    void test_createTrainee() {
        // Arrange
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail("Email");
        userRegisterDto.setPassword("Password");
        userRegisterDto.setRole(RoleType.TRAINEE);
        when(mockSkillRepository.findSkillNamesBYTrainee(any())).thenReturn(new ArrayList<>());

        // Act
        traineeUserService.createTrainee(userRegisterDto);

        // Assert
        verify(mockTraineeUserRepository,times(1)).save(any());
        verify(mockSkillRepository,times(1)).findSkillNamesBYTrainee(any());
    }

    @Test
    void test_updateStatus() {
        // Arrange
        String email = "Email";
        UUID userId = UUID.randomUUID();
        TraineeUser mockTraineeUser = new TraineeUser();
        mockTraineeUser.setId(userId);
        mockTraineeUser.setStatus(StatusType.TRAINING);
        mockTraineeUser.setEmail(email);

        when(mockTraineeUserRepository.findById(userId)).thenReturn(Optional.of(mockTraineeUser));

        // Act
        TraineePublicDto savedTrainee = traineeUserService.updateStatus(userId, StatusType.TRAINING);

        // Assert
        verify(mockTraineeUserRepository,times(1)).findById(userId);
        assertEquals(StatusType.TRAINING, savedTrainee.getType());
        assertEquals(email, savedTrainee.getEmail());
    }

    @Test
    void test_updateTraineeStatus() {
        // Arrange
        String email = "Email";
        UUID userId = UUID.randomUUID();
        TraineeUser mockTraineeUser = new TraineeUser();
        mockTraineeUser.setId(userId);
        mockTraineeUser.setStatus(StatusType.ABSENT);
        mockTraineeUser.setEmail(email);
        mockTraineeUser.setAuthenticated(false);

        when(mockTraineeUserRepository.findById(userId)).thenReturn(Optional.of(mockTraineeUser));
        when(mockTraineeUserRepository.save(any())).thenReturn(mockTraineeUser);

        // Act
        AppUser savedTrainee = traineeUserService.updateTraineeStatus(userId, StatusType.ABSENT);

        // Assert
        verify(mockTraineeUserRepository,times(1)).findById(userId);
        assertEquals(email, savedTrainee.getEmail());
        assertEquals(false, savedTrainee.isAuthenticated());
    }

    @Test
    void test_updateTrainee() {
        // Arrange
        String email = "Email";
        UUID userId = UUID.randomUUID();
        TraineeUser mockTraineeUser = new TraineeUser();
        mockTraineeUser.setId(userId);
        mockTraineeUser.setStatus(StatusType.ABSENT);
        mockTraineeUser.setEmail(email);
        mockTraineeUser.setAuthenticated(false);
        TraineeUpdateDto traineeUpdateDto = new TraineeUpdateDto();
        traineeUpdateDto.setEmail(email);
        traineeUpdateDto.setRole(RoleType.TRAINEE);
        traineeUpdateDto.setStatus(StatusType.ABSENT);


        when(mockTraineeUserRepository.findById(userId)).thenReturn(Optional.of(mockTraineeUser));
        when(mockTraineeUserRepository.save(any())).thenReturn(mockTraineeUser);

        // Act
        AppUser savedTrainee = traineeUserService.updateTrainee(userId, traineeUpdateDto);

        // Assert
        verify(mockTraineeUserRepository,times(1)).findById(userId);
        assertEquals(email, savedTrainee.getEmail());
        assertEquals(false, savedTrainee.isAuthenticated());
    }

    @Test
    void test_updateTraineePassword() {
        // Arrange
        UUID userId = UUID.randomUUID();
        TraineePasswordUpdateDto traineePasswordUpdateDto = new TraineePasswordUpdateDto();
        traineePasswordUpdateDto.setPassword("Password");

        when(mockTraineeUserRepository.findById(userId)).thenReturn(Optional.of(new TraineeUser()));
        when(mockPasswordEncoder.encode(any())).thenReturn("EncodedPassword");

        // Act
        traineeUserService.updateTraineePassword(userId, traineePasswordUpdateDto);

        // Assert
        verify(mockTraineeUserRepository,times(1)).findById(userId);
        verify(mockTraineeUserRepository,times(1)).save(any());
        verify(mockPasswordEncoder,times(1)).encode(any());
    }

    @Test
    void test_getTraineeFullInfo() {
        // Arrange
        String email = "Email";
        UUID userId = UUID.randomUUID();
        TraineeUser mockTraineeUser = new TraineeUser();
        mockTraineeUser.setId(userId);
        mockTraineeUser.setStatus(StatusType.ABSENT);
        mockTraineeUser.setEmail(email);
        mockTraineeUser.setAuthenticated(false);

        when(mockTraineeUserRepository.findTraineeUserByEmail(email)).thenReturn(mockTraineeUser);
        when(mockSkillRepository.findSkillFullInfoByTrainee(userId)).thenReturn(new ArrayList<>());

        // Act
        TraineePublicDto traineeFullInfo = traineeUserService.getTraineeFullInfo(email);

        // Assert
        verify(mockTraineeUserRepository,times(1)).findTraineeUserByEmail(email);
        verify(mockSkillRepository,times(1)).findSkillFullInfoByTrainee(userId);
        assertEquals(StatusType.ABSENT, traineeFullInfo.getType());
        assertEquals(email, traineeFullInfo.getEmail());
    }
}