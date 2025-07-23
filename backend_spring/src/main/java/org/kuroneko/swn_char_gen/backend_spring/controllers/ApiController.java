/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.controllers;

import org.kuroneko.swn_char_gen.backend_spring.services.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * REST controller for character management endpoints.
 * This controller duplicates the endpoints from the Flask backend.
 */
@RestController
@RequestMapping("/api")
public class ApiController
{
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	private final CharacterService characterService;
	private final ObjectMapper objectMapper;

	@Autowired
	public ApiController(CharacterService characterService)
	{
		this.characterService = characterService;
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Creates a new character.
	 *
	 * @return the new character data
	 */
	@GetMapping("/new-character")
	public ResponseEntity<Map<String, Object>> newCharacter()
	{
		try
		{
			Map<String, Object> character = characterService.createNewCharacter();
			return ResponseEntity.ok(character);
		}
		catch (Exception e)
		{
			logger.error("Error creating new character", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Gets the current character.
	 *
	 * @return the current character data
	 */
	@GetMapping("/character")
	public ResponseEntity<Map<String, Object>> getCharacter()
	{
		try
		{
			Map<String, Object> character = characterService.getCharacter();
			return ResponseEntity.ok(character);
		}
		catch (Exception e)
		{
			logger.error("Error getting character", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Rolls attributes for a character.
	 *
	 * @return the updated character data
	 */
	@GetMapping("/roll-attributes")
	public ResponseEntity<Map<String, Object>> rollAttributes()
	{
		try
		{
			Map<String, Object> character = characterService.rollAttributes();
			return ResponseEntity.ok(character);
		}
		catch (Exception e)
		{
			logger.error("Error rolling attributes", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Changes a specific attribute.
	 *
	 * @param requestBody the request body containing the attribute to change
	 * @return the updated character data
	 */
	@PostMapping("/change-attribute")
	public ResponseEntity<Map<String, Object>> changeAttribute(@RequestBody Map<String, String> requestBody)
	{
		try
		{
			String attribute = requestBody.get("attribute");
			if (attribute == null || attribute.isEmpty())
			{
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Attribute parameter is required");
				return ResponseEntity.badRequest().body(error);
			}

			Map<String, Object> character = characterService.changeAttribute(attribute);
			return ResponseEntity.ok(character);
		}
		catch (Exception e)
		{
			logger.error("Error changing attribute", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Sets a detail for a character.
	 *
	 * @param requestBody the request body containing the detail and value
	 * @return the updated character data
	 */
	@PostMapping("/set-detail")
	public ResponseEntity<Map<String, Object>> setDetail(@RequestBody Map<String, String> requestBody)
	{
		try
		{
			String detail = requestBody.get("detail");
			String value = requestBody.get("value");

			if (detail == null || detail.isEmpty() || value == null || value.isEmpty())
			{
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Detail and value parameters are required");
				return ResponseEntity.badRequest().body(error);
			}

			Map<String, Object> character = characterService.setDetail(detail, value);
			return ResponseEntity.ok(character);
		}
		catch (Exception e)
		{
			logger.error("Error setting detail", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Uploads a character from a JSON file.
	 *
	 * @param file the JSON file containing character data
	 * @return the uploaded character data
	 */
	@PostMapping("/upload-character")
	public ResponseEntity<Map<String, Object>> uploadCharacter(@RequestParam("file") MultipartFile file)
	{
		try
		{
			// check for file - for now, we're not worried about file name or extension
			if (file.isEmpty())
			{
				Map<String, Object> error = new HashMap<>();
				error.put("error", "No selected file");
				return ResponseEntity.badRequest().body(error);
			}

			// Parse the JSON file to a Map
			Map<String, Object> characterData = objectMapper.readValue( file.getInputStream(), new TypeReference<Map<String, Object>>() {});

			// Upload the character using the service
			Map<String, Object> character = characterService.uploadCharacter(characterData);

			return ResponseEntity.ok(character);
		}
		catch (Exception e)
		{
			logger.error("Error uploading character", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	/**
	 * Downloads the current character as a JSON file.
	 *
	 * @return the character data as a downloadable JSON file
	 */
	@GetMapping("/download-character")
	public ResponseEntity<String> downloadCharacter()
	{
		try
		{
			// Get the current character
			Map<String, Object> character = characterService.getCharacter();

			// Convert the character to JSON
			String characterJson = objectMapper.writeValueAsString(character);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setContentDispositionFormData("attachment", "character.json");

			return new ResponseEntity<>(characterJson, headers, HttpStatus.OK);
		}
		catch (Exception e)
		{
			logger.error("Error downloading character", e);
			Map<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);
		}
	}
}