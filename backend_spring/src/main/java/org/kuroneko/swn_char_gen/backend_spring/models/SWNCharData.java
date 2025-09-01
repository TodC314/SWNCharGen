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
 * Data class for SWN character data.
 * <p>
 * This is an echo of the Python dataclass swn_char_data. The Python class is used to get
 * "clean" data-only using to_dict. This way the methods attached to the parent swn_char class
 * can change, without the dict (used for export and import) being affected.
 * <p>
 * Java doesn't really have a setup for that, so I'm using this for now by using default (package) access.
 */
public class SWNCharData
{
	// ####
	// Details section
	// ####
	/**
	 * The character's name. Defaults to "Default Name".
	 */
	String mName = "Default Name";

	// ####
	// Attributes section
	// ####
	/**
	 * The character's Strength attribute value. Defaults to 0.
	 */
	int mStrength = 0;
	
	/**
	 * The character's Dexterity attribute value. Defaults to 0.
	 */
	int mDexterity = 0;
	
	/**
	 * The character's Constitution attribute value. Defaults to 0.
	 */
	int mConstitution = 0;
	
	/**
	 * The character's Intelligence attribute value. Defaults to 0.
	 */
	int mIntelligence = 0;
	
	/**
	 * The character's Wisdom attribute value. Defaults to 0.
	 */
	int mWisdom = 0;
	
	/**
	 * The character's Charisma attribute value. Defaults to 0.
	 */
	int mCharisma = 0;

	/**
	 * The modifier for the character's Strength attribute. Calculated based on the Strength value.
	 */
	int mStrengthModifier = 0;
	
	/**
	 * The modifier for the character's Dexterity attribute. Calculated based on the Dexterity value.
	 */
	int mDexterityModifier = 0;
	
	/**
	 * The modifier for the character's Constitution attribute. Calculated based on the Constitution value.
	 */
	int mConstitutionModifier = 0;
	
	/**
	 * The modifier for the character's Intelligence attribute. Calculated based on the Intelligence value.
	 */
	int mIntelligenceModifier = 0;
	
	/**
	 * The modifier for the character's Wisdom attribute. Calculated based on the Wisdom value.
	 */
	int mWisdomModifier = 0;
	
	/**
	 * The modifier for the character's Charisma attribute. Calculated based on the Charisma value.
	 */
	int mCharismaModifier = 0;

	/**
	 * Tracks which attribute has been changed to a special value (14).
	 * NONE indicates no attribute has been changed.
	 */
	AttributeEnum mChangedAttribute = AttributeEnum.NONE;
	
	/**
	 * Stores the original value of the attribute that was changed to 14.
	 * Used to restore the attribute if a different attribute is changed.
	 */
	int mChangedAttributeOriginalValue = 0;

}