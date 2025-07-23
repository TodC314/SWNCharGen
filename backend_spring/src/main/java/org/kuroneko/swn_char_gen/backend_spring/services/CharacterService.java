/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.services;

import org.kuroneko.swn_char_gen.backend_spring.models.AttributeEnum;
import org.kuroneko.swn_char_gen.backend_spring.models.CharacterStore;
import org.kuroneko.swn_char_gen.backend_spring.models.DetailEnum;
import org.kuroneko.swn_char_gen.backend_spring.models.SWNChar;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Service for SWN character operations.
 */
@Service
public class CharacterService
{

	/**
	 * Get the current HTTP session.
	 *
	 * @return The current HTTP session
	 */
	private HttpSession getCurrentSession()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

	/**
	 * Get or create a session ID.
	 *
	 * @return The session ID
	 */
	public String getOrCreateSessionId()
	{
		HttpSession session = getCurrentSession();
		String sessionId = (String) session.getAttribute("session_id");
		if (sessionId == null)
		{
			sessionId = UUID.randomUUID().toString();
			session.setAttribute("session_id", sessionId);
		}
		return sessionId;
	}

	/**
	 * Upload a character from JSON data.
	 *
	 * @param characterData A Map containing character data
	 * @return The uploaded character data as a Map
	 */
	public Map<String, Object> uploadCharacter(Map<String, Object> characterData) throws IOException
	{
		String sessionId = getOrCreateSessionId();
		// Create a new character from the uploaded data
		SWNChar character = new SWNChar(characterData);
		// Store the character
		CharacterStore.getInstance().storeCharacter(sessionId, character);
		return character.toMap();
	}

	/**
	 * Create a new character.
	 *
	 * @return The new character data as a Map
	 */
	public Map<String, Object> createNewCharacter()
	{
		String sessionId = getOrCreateSessionId();
		SWNChar character = new SWNChar();
		CharacterStore.getInstance().storeCharacter(sessionId, character);
		return character.toMap();
	}

	/**
	 * Get the current character.
	 *
	 * @return The current character data as a Map
	 */
	public Map<String, Object> getCharacter()
	{
		String sessionId = getOrCreateSessionId();
		CharacterStore store = CharacterStore.getInstance();

		if (store.characterMissing(sessionId))
		{
			SWNChar character = new SWNChar();
			store.storeCharacter(sessionId, character);
			return character.toMap();
		}
		else
		{
			SWNChar character = store.getCharacter(sessionId);
			return character.toMap();
		}
	}

	/**
	 * Roll attributes for the current character.
	 *
	 * @return The updated character data as a Map
	 */
	public Map<String, Object> rollAttributes()
	{
		String sessionId = getOrCreateSessionId();
		CharacterStore store = CharacterStore.getInstance();

		if (store.characterMissing(sessionId))
		{
			SWNChar character = new SWNChar();
			store.storeCharacter(sessionId, character);
			character.rollAttributes();
			return character.toMap();
		}
		else
		{
			SWNChar character = store.getCharacter(sessionId);
			character.rollAttributes();
			return character.toMap();
		}
	}

	/**
	 * Change an attribute for the current character.
	 *
	 * @param attributeName The name of the attribute to change
	 * @return The updated character data as a Map
	 * @throws IllegalArgumentException if no character is found or the attribute is invalid
	 */
	public Map<String, Object> changeAttribute(String attributeName)
	{
		String sessionId = getOrCreateSessionId();
		CharacterStore store = CharacterStore.getInstance();

		if (store.characterMissing(sessionId))
		{
			throw new IllegalArgumentException("No character found");
		}

		SWNChar character = store.getCharacter(sessionId);
		AttributeEnum attributeEnum = AttributeEnum.valueOf(attributeName.toUpperCase());
		character.changeOneAttribute(attributeEnum);
		return character.toMap();
	}

	/**
	 * Set a detail for the current character.
	 *
	 * @param detailName  The name of the detail to set
	 * @param detailValue The value to set
	 * @return The updated character data as a Map
	 * @throws IllegalArgumentException if no character is found or the detail is invalid
	 */
	public Map<String, Object> setDetail(String detailName, String detailValue)
	{
		String sessionId = getOrCreateSessionId();
		CharacterStore store = CharacterStore.getInstance();

		if (store.characterMissing(sessionId))
		{
			throw new IllegalArgumentException("No character found");
		}

		SWNChar character = store.getCharacter(sessionId);
		DetailEnum detailEnum = DetailEnum.valueOf(detailName.toUpperCase());
		character.setDetail(detailEnum, detailValue);
		return character.toMap();
	}
}