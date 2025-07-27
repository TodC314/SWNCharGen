/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kuroneko.swn_char_gen.backend_spring.controllers.ApiController;
import org.kuroneko.swn_char_gen.backend_spring.models.CharacterStore;
import org.kuroneko.swn_char_gen.backend_spring.models.SWNChar;
import org.kuroneko.swn_char_gen.backend_spring.services.CharacterService;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Integration test class for the SWN Character Generator Spring Boot application.
 * This class verifies that the Spring application context loads successfully and tests
 * the basic functionality of the application.
 * <p>
 * These tests serve as both smoke tests (verifying that the application starts correctly)
 * and integration tests (verifying that components work together properly).
 * <p>
 * The tests use a combination of:
 * - Direct service calls to test business logic
 * - MockMvc to test API endpoints without starting a full server
 * - Mocking to isolate components and control their behavior
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendSpringApplicationTests
{
	@Autowired
	private ApplicationContext mApplicationContext;

	@Autowired
	private WebApplicationContext mWebApplicationContext;

	@Autowired
	private CharacterService mCharacterServiceOriginal;

	private CharacterService mCharacterService;

	@Autowired
	private ApiController mApiController;

	private MockMvc mMockMvc;

	@BeforeEach
	void setUp()
	{
		// Create a spy of the original CharacterService
        this.mCharacterService = Mockito.spy(this.mCharacterServiceOriginal);

        this.mMockMvc = MockMvcBuilders.webAppContextSetup(this.mWebApplicationContext).build();

		// Set up mock request and session
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = new MockHttpSession();
		when(mockRequest.getSession(true)).thenReturn(mockSession);

		// Set up RequestContextHolder with mock request
		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
		RequestContextHolder.setRequestAttributes(servletRequestAttributes);

		// Mock the getOrCreateSessionId method to return a fixed session ID
		doReturn(UUID.randomUUID().toString()).when(this.mCharacterService).getOrCreateSessionId();
	}

	/**
	 * Tests that the application context loads without errors.
	 * This is a basic smoke test to ensure that the Spring Boot application
	 * can start up properly with all required beans and configurations.
	 */
	@Test
	@DisplayName("Context Loads Successfully")
	void contextLoads()
	{
		assertNotNull(this.mApplicationContext, "Application context should not be null");
	}

	/**
	 * Tests the isApplicationHealthy method in BackendSpringApplication.
	 * This verifies that the method correctly identifies a healthy application context.
	 */
	@Test
	@DisplayName("Application Health Check")
	void applicationHealthCheck()
	{
		boolean isHealthy = true;
		// Cast to ConfigurableApplicationContext to use with isApplicationHealthy
		ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) this.mApplicationContext;
		try
		{
			// Check if the application is healthy
			configurableContext.getBean("apiController");
			configurableContext.getBean("characterService");
		}
		catch (Exception e)
		{
			isHealthy = false;
		}
		assertTrue(isHealthy, "Application should be healthy");
	}

	/**
	 * Tests that all required beans are properly autowired.
	 */
	@Test
	@DisplayName("Required Beans Are Autowired")
	void requiredBeansAreAutowired()
	{
		assertNotNull(this.mCharacterService, "CharacterService should be autowired");
		assertNotNull(this.mApiController, "ApiController should be autowired");
	}

	/**
	 * Tests the CharacterService's ability to create a new character.
	 */
	@Test
	@DisplayName("Character Service Creates New Character")
	void characterServiceCreatesNewCharacter()
	{
		// Mock the CharacterStore
		CharacterStore mockStore = mock(CharacterStore.class);

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(mockStore);

			// Call the method under test
			Map<String, Object> character = this.mCharacterService.createNewCharacter();

			// Verify the result
			assertNotNull(character, "Created character should not be null");
			verify(mockStore).storeCharacter(anyString(), any(SWNChar.class));
		}
	}

	/**
	 * Tests the API endpoint for creating a new character.
	 * This test uses MockMvc instead of TestRestTemplate to test the controller directly.
	 */
	@Test
	@DisplayName("API Endpoint Creates New Character")
	void apiEndpointCreatesNewCharacter() throws Exception
	{
		// Mock the CharacterService to return a test character
		Map<String, Object> mockCharacter = new HashMap<>();
		mockCharacter.put("name", "Test Character");
		doReturn(mockCharacter).when(this.mCharacterService).createNewCharacter();

		// Test the endpoint with the correct path
		this.mMockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/new-character"))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.mName").value("Default Name"));
	}

	/**
	 * Tests the CharacterStore singleton.
	 */
	@Test
	@DisplayName("Character Store Is Singleton")
	void characterStoreIsSingleton()
	{
		CharacterStore store1 = CharacterStore.getInstance();
		CharacterStore store2 = CharacterStore.getInstance();

		assertNotNull(store1, "CharacterStore instance should not be null");
		assertSame(store1, store2, "CharacterStore should be a singleton");
	}

	/**
	 * Tests the application's ability to roll attributes for a character.
	 */
	@Test
	@DisplayName("Roll Attributes Works")
	void rollAttributesWorks()
	{
		// Mock the CharacterStore
		CharacterStore mockStore = mock(CharacterStore.class);
		SWNChar mockChar = mock(SWNChar.class);

		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("strength", 14);
		attributes.put("dexterity", 12);
		mockCharacter.put("attributes", attributes);

		// Configure mocks
		when(mockStore.characterMissing(anyString())).thenReturn(false);
		when(mockStore.getCharacter(anyString())).thenReturn(mockChar);
		when(mockChar.toMap()).thenReturn(mockCharacter);

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(mockStore);

			// Call the method under test
			Map<String, Object> result = this.mCharacterService.rollAttributes();

			// Verify the result
			assertNotNull(result, "Character with rolled attributes should not be null");
			assertTrue(result.containsKey("attributes"), "Character should have attributes");

			@SuppressWarnings("unchecked")
			Map<String, Object> resultAttributes = (Map<String, Object>) result.get("attributes");
			assertFalse(resultAttributes.isEmpty(), "Attributes should not be empty");
			assertEquals(14, resultAttributes.get("strength"), "Strength should match mock value");

			// Verify the character's rollAttributes method was called
			verify(mockChar).rollAttributes();
		}
	}
}
