/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton store for SWN characters.
 */
public class CharacterStore
{
	private static CharacterStore instance;
	private final Map<String, SWNChar> characters = new HashMap<>();

	/**
	 * Private constructor to prevent direct instantiation.
	 */
	private CharacterStore()
	{
	}

	/**
	 * Get the singleton instance of CharacterStore.
	 *
	 * @return The singleton instance
	 */
	public static synchronized CharacterStore getInstance()
	{
		if (instance == null)
		{
			instance = new CharacterStore();
		}
		return instance;
	}

	/**
	 * Get a character by session ID.
	 *
	 * @param sessionId The session ID
	 * @return The character, or null if not found
	 */
	public SWNChar getCharacter(String sessionId)
	{
		return characters.get(sessionId);
	}

	/**
	 * Store a character by session ID.
	 *
	 * @param sessionId The session ID
	 * @param character The character to store
	 */
	public void storeCharacter(String sessionId, SWNChar character)
	{
		characters.put(sessionId, character);
	}

	/**
	 * Check if a character exists for a session ID.
	 *
	 * @param sessionId The session ID
	 * @return True if a character exists, false otherwise
	 */
	public boolean characterMissing(String sessionId)
	{
		return !characters.containsKey(sessionId);
	}
}