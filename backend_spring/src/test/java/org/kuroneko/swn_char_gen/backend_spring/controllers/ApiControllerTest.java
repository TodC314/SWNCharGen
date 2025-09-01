/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kuroneko.swn_char_gen.backend_spring.services.CharacterService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApiController.
 * This class tests all the REST endpoints provided by the ApiController.
 * <p>
 * These tests focus on the controller layer, verifying that:
 * - Endpoints are correctly mapped and respond to HTTP requests
 * - Request parameters are properly processed
 * - Response status codes and bodies are as expected
 * - Error handling works correctly
 * <p>
 * The tests use MockMvc with a standalone setup to test the controller in isolation,
 * with the CharacterService mocked to control its behavior and avoid dependencies
 * on the actual service implementation.
 * <p>
 * Each endpoint has both happy path tests (successful requests) and error case tests
 * (invalid inputs or service exceptions) to ensure comprehensive coverage.
 */
@ExtendWith(MockitoExtension.class)
public class ApiControllerTest
{
	private MockMvc mockMvc;

	@Mock
	private CharacterService characterService;

	@InjectMocks
	private ApiController apiController;

	@BeforeEach
	void setUp()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}

	/**
	 * Tests the /api/new endpoint for creating a new character.
	 */
	@Test
	@DisplayName("New Character Endpoint")
	void testNewCharacterEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		mockCharacter.put("id", "test-id");
		mockCharacter.put("name", "Test Character");

		// Configure mock service
		when(characterService.createNewCharacter()).thenReturn(mockCharacter);

		// Perform request and validate response
		mockMvc.perform(get("/api/new-character"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("test-id"))
				.andExpect(jsonPath("$.name").value("Test Character"));
	}

	/**
	 * Tests the /api/get endpoint for retrieving the current character.
	 */
	@Test
	@DisplayName("Get Character Endpoint")
	void testGetCharacterEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		mockCharacter.put("id", "test-id");
		mockCharacter.put("name", "Test Character");

		// Configure mock service
		when(characterService.getCharacter()).thenReturn(mockCharacter);

		// Perform request and validate response
		mockMvc.perform(get("/api/character"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("test-id"))
				.andExpect(jsonPath("$.name").value("Test Character"));
	}

	/**
	 * Tests the /api/roll endpoint for rolling attributes.
	 */
	@Test
	@DisplayName("Roll Attributes Endpoint")
	void testRollAttributesEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("strength", 14);
		attributes.put("dexterity", 12);
		attributes.put("constitution", 10);
		attributes.put("intelligence", 16);
		attributes.put("wisdom", 8);
		attributes.put("charisma", 11);
		mockCharacter.put("attributes", attributes);

		// Configure mock service
		when(characterService.rollAttributes()).thenReturn(mockCharacter);

		// Perform request and validate response
		mockMvc.perform(get("/api/roll-attributes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.attributes.strength").value(14))
				.andExpect(jsonPath("$.attributes.dexterity").value(12))
				.andExpect(jsonPath("$.attributes.constitution").value(10))
				.andExpect(jsonPath("$.attributes.intelligence").value(16))
				.andExpect(jsonPath("$.attributes.wisdom").value(8))
				.andExpect(jsonPath("$.attributes.charisma").value(11));
	}

	/**
	 * Tests the /api/change endpoint for changing an attribute.
	 */
	@Test
	@DisplayName("Change Attribute Endpoint")
	void testChangeAttributeEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("strength", 16);
		mockCharacter.put("attributes", attributes);

		// Configure mock service
		when(characterService.changeAttribute(anyString())).thenReturn(mockCharacter);

		// Prepare request body
		String requestBody = "{\"attribute\":\"strength\"}";

		// Perform request and validate response
		mockMvc.perform(post("/api/change-attribute")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.attributes.strength").value(16));
	}

	/**
	 * Tests the /api/set endpoint for setting a character detail.
	 */
	@Test
	@DisplayName("Set Detail Endpoint")
	void testSetDetailEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		Map<String, Object> details = new HashMap<>();
		details.put("name", "John Doe");
		mockCharacter.put("details", details);

		// Configure mock service
		when(characterService.setDetail(anyString(), anyString())).thenReturn(mockCharacter);

		// Prepare request body
		String requestBody = "{\"detail\":\"name\",\"value\":\"John Doe\"}";

		// Perform request and validate response
		mockMvc.perform(post("/api/set-detail")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.details.name").value("John Doe"));
	}

	/**
	 * Tests the /api/upload endpoint for uploading a character.
	 */
	@Test
	@DisplayName("Upload Character Endpoint")
	void testUploadCharacterEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		mockCharacter.put("id", "uploaded-id");
		mockCharacter.put("name", "Uploaded Character");

		// Configure mock service
		when(characterService.uploadCharacter(any())).thenReturn(mockCharacter);

		// Prepare mock file
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"character.json",
				MediaType.APPLICATION_JSON_VALUE,
				"{\"name\":\"Uploaded Character\"}".getBytes()
		);

		// Perform request and validate response
		mockMvc.perform(multipart("/api/upload-character").file(file))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("uploaded-id"))
				.andExpect(jsonPath("$.name").value("Uploaded Character"));
	}

	/**
	 * Tests the /api/download endpoint for downloading a character.
	 */
	@Test
	@DisplayName("Download Character Endpoint")
	void testDownloadCharacterEndpoint() throws Exception
	{
		// Prepare mock response
		Map<String, Object> mockCharacter = new HashMap<>();
		mockCharacter.put("id", "download-id");
		mockCharacter.put("name", "Download Character");

		// Configure mock service
		when(characterService.getCharacter()).thenReturn(mockCharacter);

		// Perform request and validate response
		mockMvc.perform(get("/api/download-character"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(
						"Content-Disposition",
						"attachment; filename=\"character.json\""));
	}

	/**
	 * Tests error handling for the /api/change endpoint when an invalid attribute is provided.
	 */
	@Test
	@DisplayName("Change Attribute Error Handling")
	void testChangeAttributeErrorHandling() throws Exception
	{
		// Configure mock service to throw exception
		when(characterService.changeAttribute(anyString()))
				.thenThrow(new IllegalArgumentException("Invalid attribute"));

		// Prepare request body
		String requestBody = "{\"attribute\":\"invalid\"}";

		// Perform request and validate response
		mockMvc.perform(post("/api/change-attribute")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestBody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").exists());
	}

	/**
	 * Tests error handling for the /api/set endpoint when an invalid detail is provided.
	 */
	@Test
	@DisplayName("Set Detail Error Handling")
	void testSetDetailErrorHandling() throws Exception
	{
		// Configure mock service to throw exception
		when(characterService.setDetail(anyString(), anyString()))
				.thenThrow(new IllegalArgumentException("Invalid detail"));

		// Prepare request body
		String requestBody = "{\"detail\":\"invalid\",\"value\":\"test\"}";

		// Perform request and validate response
		mockMvc.perform(post("/api/set-detail")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestBody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").exists());
	}
}