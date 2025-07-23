/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.models;

/**
 * Enum for character details in the Stars Without Number (SWN) system.
 * Represents the various descriptive elements of a character that aren't
 * attributes or skills, plus a special NONE value used for initialization
 * and default states.
 */
public enum DetailEnum
{
	/**
	 * Represents the character's name.
	 * This is the primary identifier for the character.
	 */
	NAME,
	
	/**
	 * Special value used to indicate no detail is selected.
	 * Used for initialization and default states.
	 */
	NONE
}