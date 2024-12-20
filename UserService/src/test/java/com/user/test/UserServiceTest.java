package com.user.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_MOCKS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.UserServiceApplication;
import com.user.entity.User;
import com.user.repository.UserCredentialRepository;
import com.user.service.AuthService;

@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceTest {
	
	@Autowired
	private AuthService authservice;
	
	@Autowired
	private UserCredentialRepository userRepo;
	
	@Autowired
	private static User user;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1);
		user.setName("Atul");
		user.setEmail("atul@gmail.com");
		user.setRole("user");
		user.setPassword("123");
	}
	
	@Test
	void testSaveUser() {
		String s = authservice.saveUser(user);
		assertEquals("user added to the system", s);
		
	}
	
	@Test
	void testGenerateToken() {
		String token = authservice.generateToken(user.getUsername(), user.getRole());
		assertNotNull(token);
        assertTrue(token.length() > 0);
	}
	
	@Test
	void testgetById() {
        int id = user.getId();
        User u = userRepo.findById(id).get();
        assertNotNull(u);
	}
	
}
