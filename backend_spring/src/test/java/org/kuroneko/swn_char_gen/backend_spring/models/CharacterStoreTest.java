/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CharacterStore class.
 */
class CharacterStoreTest
{

	private static final String TEST_SESSION_ID = "test-session-id";

	/**
	 * Reset the singleton instance before each test to ensure test isolation.
	 */
	@BeforeEach
	void resetSingleton() throws Exception
	{
		// Use reflection to reset the singleton instance
		Field instanceField = CharacterStore.class.getDeclaredField("instance");
		instanceField.setAccessible(true);
		instanceField.set(null, null);
	}

	@Test
	@DisplayName("Test singleton pattern")
	void testSingletonPattern()
	{
		// Get the instance twice
		CharacterStore instance1 = CharacterStore.getInstance();
		CharacterStore instance2 = CharacterStore.getInstance();

		// Verify both instances are the same object
		assertSame(instance1, instance2, "getInstance should always return the same instance");
		assertNotNull(instance1, "getInstance should not return null");
	}

	@Test
	@DisplayName("Test store and retrieve character")
	void testStoreAndRetrieveCharacter()
	{
		// Get the singleton instance
		CharacterStore store = CharacterStore.getInstance();

		// Create a character
		SWNChar character = new SWNChar();

		// Store the character
		store.storeCharacter(TEST_SESSION_ID, character);

		// Retrieve the character
		SWNChar retrievedCharacter = store.getCharacter(TEST_SESSION_ID);

		// Verify the retrieved character is the same object
		assertSame(character, retrievedCharacter, "getCharacter should return the same character that was stored");
	}

	@Test
	@DisplayName("Test get non-existent character")
	void testGetNonExistentCharacter()
	{
		// Get the singleton instance
		CharacterStore store = CharacterStore.getInstance();

		// Generate a random session ID that doesn't exist
		String nonExistentSessionId = UUID.randomUUID().toString();

		// Try to retrieve a character with a non-existent session ID
		SWNChar retrievedCharacter = store.getCharacter(nonExistentSessionId);

		// Verify the retrieved character is null
		assertNull(retrievedCharacter, "getCharacter should return null for a non-existent session ID");
	}

	@Test
	@DisplayName("Test character missing")
	void testCharacterMissing()
	{
		// Get the singleton instance
		CharacterStore store = CharacterStore.getInstance();

		// Generate a random session ID that doesn't exist
		String nonExistentSessionId = UUID.randomUUID().toString();

		// Verify characterMissing returns true for a non-existent session ID
		assertTrue(store.characterMissing(nonExistentSessionId),
				   "characterMissing should return true for a non-existent session ID");

		// Create and store a character
		SWNChar character = new SWNChar();
		store.storeCharacter(TEST_SESSION_ID, character);

		// Verify characterMissing returns false for an existing session ID
		assertFalse(store.characterMissing(TEST_SESSION_ID),
					"characterMissing should return false for an existing session ID");
	}

	@Test
	@DisplayName("Test store multiple characters")
	void testStoreMultipleCharacters()
	{
		// Get the singleton instance
		CharacterStore store = CharacterStore.getInstance();

		// Create multiple session IDs and characters
		String sessionId1 = "session-1";
		String sessionId2 = "session-2";
		String sessionId3 = "session-3";

		SWNChar character1 = new SWNChar();
		SWNChar character2 = new SWNChar();
		SWNChar character3 = new SWNChar();

		// Store the characters
		store.storeCharacter(sessionId1, character1);
		store.storeCharacter(sessionId2, character2);
		store.storeCharacter(sessionId3, character3);

		// Retrieve the characters
		SWNChar retrievedCharacter1 = store.getCharacter(sessionId1);
		SWNChar retrievedCharacter2 = store.getCharacter(sessionId2);
		SWNChar retrievedCharacter3 = store.getCharacter(sessionId3);

		// Verify the retrieved characters are the same objects
		assertSame(character1, retrievedCharacter1, "getCharacter should return the same character that was stored");
		assertSame(character2, retrievedCharacter2, "getCharacter should return the same character that was stored");
		assertSame(character3, retrievedCharacter3, "getCharacter should return the same character that was stored");
	}

	@Test
	@DisplayName("Test overwrite character")
	void testOverwriteCharacter()
	{
		// Get the singleton instance
		CharacterStore store = CharacterStore.getInstance();

		// Create two characters
		SWNChar character1 = new SWNChar();
		SWNChar character2 = new SWNChar();

		// Store the first character
		store.storeCharacter(TEST_SESSION_ID, character1);

		// Verify the first character is stored
		assertSame(character1, store.getCharacter(TEST_SESSION_ID),
				   "getCharacter should return the first character");

		// Store the second character with the same session ID
		store.storeCharacter(TEST_SESSION_ID, character2);

		// Verify the second character overwrote the first
		assertSame(character2, store.getCharacter(TEST_SESSION_ID),
				   "getCharacter should return the second character after overwriting");
		assertNotSame(character1, store.getCharacter(TEST_SESSION_ID),
					  "getCharacter should not return the first character after overwriting");
	}
}