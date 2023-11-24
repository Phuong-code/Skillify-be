package com.fdmgroup.skillify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdmgroup.skillify.dto.user.UserProfileUpdateDto;
import com.fdmgroup.skillify.dto.user.UserPublicDto;
import com.fdmgroup.skillify.dto.user.UserRegisterDto;
import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.enums.RoleType;
import com.fdmgroup.skillify.repository.AppUserRepository;
import com.fdmgroup.skillify.repository.SkillRepository;
import com.fdmgroup.skillify.service.AppUserService;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {
	@InjectMocks
	AppUserService appUserService;
	
	@Mock
	AppUserRepository appUserRepository;

	@Mock
	SkillRepository skillRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;

	@Test
	void test_createUser() {
		UserRegisterDto userRegisterDto = new UserRegisterDto();
		UserPublicDto userPublicDto = new UserPublicDto();
		
		assertEquals(userPublicDto.toString(), appUserService.createUser(userRegisterDto).toString());
	}
	
    @Test
    public void test_updateAppUser() {
        UUID userId = UUID.randomUUID();
        UserProfileUpdateDto userProfileUpdateDto = new UserProfileUpdateDto();
        userProfileUpdateDto.setEmail("new.email@example.com");
        userProfileUpdateDto.setFirstName("NewFirstName");
        userProfileUpdateDto.setLastName("NewLastName");

        AppUser mockUser = new AppUser();
        mockUser.setId(userId);

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        UserProfileUpdateDto updatedUserProfile = appUserService.updateAppUser(userId, userProfileUpdateDto);


        assertEquals(userProfileUpdateDto, updatedUserProfile);
        assertEquals("new.email@example.com", mockUser.getEmail());
        assertEquals("NewFirstName", mockUser.getFirstName());
        assertEquals("NewLastName", mockUser.getLastName());

        verify(appUserRepository, times(1)).findById(userId);
        verify(appUserRepository, times(1)).save(mockUser);
    }
    
    @Test
    public void test_deleteUserById() {
        UUID userId = UUID.randomUUID();

        appUserService.deleteUserById(userId);

        verify(appUserRepository, times(1)).deleteById(userId);
    }


    @Test
    public void test_getUserById_existingUser() {
        UUID userId = UUID.randomUUID();
        AppUser mockUser = new AppUser();
        mockUser.setId(userId);

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        AppUser resultUser = appUserService.getUserById(userId);

        assertEquals(mockUser, resultUser);

        verify(appUserRepository, times(1)).findById(userId);
    }
    
    @Test
    public void test_getUserByEmail_existingUser() {
        String email = "test@example.com";
        AppUser mockUser = new AppUser();
        mockUser.setEmail(email);

        when(appUserRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        AppUser resultUser = appUserService.getUserByEmail(email);

        assertEquals(mockUser, resultUser);

        verify(appUserRepository, times(1)).findByEmail(email);
    }


    @Test
    public void test_deleteAllAppUser() {
        List<AppUser> mockUsers = new ArrayList<>();
        mockUsers.add(new AppUser());
        mockUsers.add(new AppUser());
        mockUsers.add(new AppUser());

        when(appUserRepository.findAll()).thenReturn(mockUsers);

        int deletedCount = appUserService.deleteAllAppUser();

        assertEquals(mockUsers.size(), deletedCount);

        verify(appUserRepository, times(1)).findAll();
    }
    
    @Test
    public void test_searchByUserName() {
        String keyword = "john";
        AppUser mockUser1 = new AppUser();
        mockUser1.setId(UUID.randomUUID());
        mockUser1.setRole(RoleType.TRAINEE);

        when(appUserRepository.filterByUserName(keyword)).thenReturn(Arrays.asList(mockUser1));

        when(skillRepository.findSkillNamesBYTrainee(mockUser1.getId())).thenReturn(Arrays.asList("Java", "Python"));

        List<UserPublicDto> result = appUserService.searchByUserName(keyword);

        assertFalse(result.isEmpty());

        UserPublicDto userPublicDto = result.get(0);
        assertEquals(mockUser1.getId(), userPublicDto.getId());

        verify(appUserRepository, times(1)).filterByUserName(keyword);
        verify(skillRepository, times(1)).findSkillNamesBYTrainee(mockUser1.getId());
    }
    
    @Test
    public void test_searchByRole() {
        RoleType role = RoleType.TRAINEE;
        AppUser mockUser1 = new AppUser();
        mockUser1.setId(UUID.randomUUID());
        mockUser1.setRole(role);

        when(appUserRepository.filterByRole(role)).thenReturn(Arrays.asList(mockUser1));

        List<UserPublicDto> result = appUserService.searchByRole(role);

        assertFalse(result.isEmpty());

        UserPublicDto userPublicDto = result.get(0);
        assertEquals(mockUser1.getId(), userPublicDto.getId());
        assertEquals(role, userPublicDto.getRole());

        verify(appUserRepository, times(1)).filterByRole(role);
    }
    
    
    @Test
    public void test_convertUserPublicDto() {
        AppUser mockUser = new AppUser();
        mockUser.setId(UUID.randomUUID());
        mockUser.setEmail("test@example.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setRole(RoleType.TRAINEE);

        UserPublicDto userPublicDto = appUserService.convertUserPublicDto(mockUser);

        assertEquals(mockUser.getId(), userPublicDto.getId());
        assertEquals(mockUser.getEmail(), userPublicDto.getEmail());
        assertEquals(mockUser.getFirstName(), userPublicDto.getFirstName());
        assertEquals(mockUser.getLastName(), userPublicDto.getLastName());
        assertEquals(mockUser.getRole(), userPublicDto.getRole());
    }
    
    
    @Test
    public void test_getAllTrainees() {
        AppUser mockUser1 = new AppUser();
        mockUser1.setId(UUID.randomUUID());
        mockUser1.setRole(RoleType.TRAINEE);

        AppUser mockUser2 = new AppUser();
        mockUser2.setId(UUID.randomUUID());
        mockUser2.setRole(RoleType.TRAINEE);

        when(appUserRepository.getAllTrainees()).thenReturn(Arrays.asList(mockUser1, mockUser2));

        List<AppUser> result = appUserService.getAllTrainees();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        verify(appUserRepository, times(1)).getAllTrainees();
    }
    
    @Test
    public void test_getTraineeById_existingTrainee() {
        UUID traineeId = UUID.randomUUID();
        AppUser mockTrainee = new AppUser();
        mockTrainee.setId(traineeId);
        mockTrainee.setRole(RoleType.TRAINEE);

        when(appUserRepository.findById(traineeId)).thenReturn(Optional.of(mockTrainee));

        Optional<AppUser> result = appUserService.getTraineeById(traineeId);

        assertTrue(result.isPresent());
        assertEquals(mockTrainee, result.get());

        verify(appUserRepository, times(1)).findById(traineeId);
    }
    
    @Test
    public void test_getAutheticatedTrainerOrSalesEmail() {
        RoleType role = RoleType.TRAINER;
        List<String> mockEmails = Arrays.asList("trainer1@example.com", "trainer2@example.com");

        when(appUserRepository.findAutheticatedTrainerOrSalesEmail(role)).thenReturn(mockEmails);

        List<String> result = appUserService.getAutheticatedTrainerOrSalesEmail(role);

        assertFalse(result.isEmpty());
        assertEquals(mockEmails, result);

        verify(appUserRepository, times(1)).findAutheticatedTrainerOrSalesEmail(role);
    }
   



}
