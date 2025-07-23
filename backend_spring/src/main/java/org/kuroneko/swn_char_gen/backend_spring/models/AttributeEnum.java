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
 * Enum for character attributes in the Stars Without Number (SWN) system.
 * Represents the six primary attributes that define a character's capabilities,
 * plus a special NONE value used for initialization and default states.
 */
public enum AttributeEnum
{
	/**
	 * Represents physical power and melee combat ability.
	 * Affects carrying capacity and melee attack/damage bonuses.
	 */
	STRENGTH,
	
	/**
	 * Represents agility, reflexes, and hand-eye coordination.
	 * Affects initiative, armor class, and ranged attack bonuses.
	 */
	DEXTERITY,
	
	/**
	 * Represents health, stamina, and physical resilience.
	 * Affects hit points and resistance to physical threats.
	 */
	CONSTITUTION,
	
	/**
	 * Represents reasoning, memory, and learning ability.
	 * Affects skill points, language knowledge, and technical skills.
	 */
	INTELLIGENCE,
	
	/**
	 * Represents intuition, perception, and willpower.
	 * Affects saving throws and awareness of surroundings.
	 */
	WISDOM,
	
	/**
	 * Represents personality, leadership, and social influence.
	 * Affects reaction rolls and social interactions.
	 */
	CHARISMA,
	
	/**
	 * Special value used to indicate no attribute is selected.
	 * Used for initialization and default states.
	 */
	NONE
}