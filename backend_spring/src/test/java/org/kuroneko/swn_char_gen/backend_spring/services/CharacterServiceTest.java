/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kuroneko.swn_char_gen.backend_spring.models.AttributeEnum;
import org.kuroneko.swn_char_gen.backend_spring.models.CharacterStore;
import org.kuroneko.swn_char_gen.backend_spring.models.DetailEnum;
import org.kuroneko.swn_char_gen.backend_spring.models.SWNChar;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for the CharacterService.
 * This class tests all the methods provided by the CharacterService.
 * <p>
 * These tests focus on the service layer, verifying that:
 * - Service methods work correctly in isolation
 * - Session management functions properly
 * - Character creation, retrieval, and modification work as expected
 * - Error cases are handled appropriately
 * <p>
 * The tests use Mockito to mock dependencies such as:
 * - HttpServletRequest and HttpSession for session management
 * - ServletRequestAttributes for the RequestContextHolder
 * - CharacterStore for character storage
 * <p>
 * MockedStatic is used to mock the static getInstance() method of CharacterStore.
 * <p>
 * Each service method has both happy path tests (successful operations) and error case tests
 * (missing characters, invalid inputs) to ensure comprehensive coverage.
 */
@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest
{

	@InjectMocks
	private CharacterService characterService;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpSession session;

	@Mock
	private ServletRequestAttributes attributes;

	@Mock
	private CharacterStore characterStore;

	private static final String TEST_SESSION_ID = "test-session-id";

	@BeforeEach
	void setUp()
	{
		// Mock the RequestContextHolder to return our mocked ServletRequestAttributes
		RequestContextHolder.setRequestAttributes(attributes);

		// Mock the ServletRequestAttributes to return our mocked HttpServletRequest
		when(attributes.getRequest()).thenReturn(request);

		// Mock the HttpServletRequest to return our mocked HttpSession
		when(request.getSession(true)).thenReturn(session);
	}

	/**
	 * Tests the getOrCreateSessionId method when a session ID already exists.
	 */
	@Test
	@DisplayName("Get Existing Session ID")
	void testGetExistingSessionId()
	{
		// Mock the session to return an existing session ID
		when(session.getAttribute("session_id")).thenReturn(TEST_SESSION_ID);

		// Call the method under test
		String sessionId = characterService.getOrCreateSessionId();

		// Verify the result
		assertEquals(TEST_SESSION_ID, sessionId, "Should return the existing session ID");
		verify(session, never()).setAttribute(eq("session_id"), any());
	}

	/**
	 * Tests the getOrCreateSessionId method when a session ID doesn't exist.
	 */
	@Test
	@DisplayName("Create New Session ID")
	void testCreateNewSessionId()
	{
		// Mock the session to return null for the session ID
		when(session.getAttribute("session_id")).thenReturn(null);

		// Call the method under test
		String sessionId = characterService.getOrCreateSessionId();

		// Verify the result
		assertNotNull(sessionId, "Should create a new session ID");
		verify(session).setAttribute(eq("session_id"), eq(sessionId));
	}

	/**
	 * Tests the uploadCharacter method.
	 */
	@Test
	@DisplayName("Upload Character")
	void testUploadCharacter() throws IOException
	{
		// Prepare test data with correct field names matching SWNCharData class
		Map<String, Object> characterData = new HashMap<>();
		characterData.put("mName", "Test Character");
		characterData.put("mStrength", 10);
		characterData.put("mDexterity", 12);
		characterData.put("mConstitution", 14);
		characterData.put("mIntelligence", 16);
		characterData.put("mWisdom", 8);
		characterData.put("mCharisma", 10);
		characterData.put("mStrengthModifier", 0);
		characterData.put("mDexterityModifier", 1);
		characterData.put("mConstitutionModifier", 2);
		characterData.put("mIntelligenceModifier", 3);
		characterData.put("mWisdomModifier", -1);
		characterData.put("mCharismaModifier", 0);
		characterData.put("mChangedAttribute", "NONE");
		characterData.put("mChangedAttributeOriginalValue", 0);

		// Mock the getOrCreateSessionId method
		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.uploadCharacter(characterData);

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(characterStore).storeCharacter(anyString(), any(SWNChar.class));
		}
	}

	/**
	 * Tests the createNewCharacter method.
	 */
	@Test
	@DisplayName("Create New Character")
	void testCreateNewCharacter()
	{
		// Mock the getOrCreateSessionId method
		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.createNewCharacter();

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(characterStore).storeCharacter(anyString(), any(SWNChar.class));
		}
	}

	/**
	 * Tests the getCharacter method when a character exists.
	 */
	@Test
	@DisplayName("Get Existing Character")
	void testGetExistingCharacter()
	{
		// Mock the characterStore
		SWNChar mockChar = new SWNChar();
		when(characterStore.characterMissing(anyString())).thenReturn(false);
		when(characterStore.getCharacter(anyString())).thenReturn(mockChar);

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.getCharacter();

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(characterStore).getCharacter(anyString());
			verify(characterStore, never()).storeCharacter(anyString(), any(SWNChar.class));
		}
	}

	/**
	 * Tests the getCharacter method when a character doesn't exist.
	 */
	@Test
	@DisplayName("Get Non-Existing Character")
	void testGetNonExistingCharacter()
	{
		// Mock the characterStore
		when(characterStore.characterMissing(anyString())).thenReturn(true);

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.getCharacter();

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(characterStore, never()).getCharacter(anyString());
			verify(characterStore).storeCharacter(anyString(), any(SWNChar.class));
		}
	}

	/**
	 * Tests the rollAttributes method when a character exists.
	 */
	@Test
	@DisplayName("Roll Attributes for Existing Character")
	void testRollAttributesForExistingCharacter()
	{
		// Mock the characterStore
		SWNChar mockChar = mock(SWNChar.class);
		when(characterStore.characterMissing(anyString())).thenReturn(false);
		when(characterStore.getCharacter(anyString())).thenReturn(mockChar);
		when(mockChar.toMap()).thenReturn(new HashMap<>());

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.rollAttributes();

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(mockChar).rollAttributes();
			verify(characterStore).getCharacter(anyString());
			verify(characterStore, never()).storeCharacter(anyString(), any(SWNChar.class));
		}
	}

	/**
	 * Tests the changeAttribute method.
	 */
	@Test
	@DisplayName("Change Attribute")
	void testChangeAttribute()
	{
		// Mock the characterStore
		SWNChar mockChar = mock(SWNChar.class);
		when(characterStore.characterMissing(anyString())).thenReturn(false);
		when(characterStore.getCharacter(anyString())).thenReturn(mockChar);
		when(mockChar.toMap()).thenReturn(new HashMap<>());

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.changeAttribute("strength");

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(mockChar).changeOneAttribute(AttributeEnum.STRENGTH);
		}
	}

	/**
	 * Tests the changeAttribute method when no character is found.
	 */
	@Test
	@DisplayName("Change Attribute with No Character")
	void testChangeAttributeWithNoCharacter()
	{
		// Mock the characterStore
		when(characterStore.characterMissing(anyString())).thenReturn(true);

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test and verify it throws an exception
			assertThrows(IllegalArgumentException.class, () -> characterService.changeAttribute("strength"));
		}
	}

	/**
	 * Tests the setDetail method.
	 */
	@Test
	@DisplayName("Set Detail")
	void testSetDetail()
	{
		// Mock the characterStore
		SWNChar mockChar = mock(SWNChar.class);
		when(characterStore.characterMissing(anyString())).thenReturn(false);
		when(characterStore.getCharacter(anyString())).thenReturn(mockChar);
		when(mockChar.toMap()).thenReturn(new HashMap<>());

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test
			Map<String, Object> result = characterService.setDetail("name", "Test Character");

			// Verify the result
			assertNotNull(result, "Should return character data");
			verify(mockChar).setDetail(DetailEnum.NAME, "Test Character");
		}
	}

	/**
	 * Tests the setDetail method when no character is found.
	 */
	@Test
	@DisplayName("Set Detail with No Character")
	void testSetDetailWithNoCharacter()
	{
		// Mock the characterStore
		when(characterStore.characterMissing(anyString())).thenReturn(true);

		try (MockedStatic<CharacterStore> mockedStatic = mockStatic(CharacterStore.class))
		{
			mockedStatic.when(CharacterStore::getInstance).thenReturn(characterStore);

			// Call the method under test and verify it throws an exception
			assertThrows(IllegalArgumentException.class, () -> characterService.setDetail("name", "Test Character"));
		}
	}
}