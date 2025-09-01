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
import java.util.Random;

/**
 * System rules and calculations for SWN characters.
 */
public class SWNSystem
{
	// Dictionary mapping attribute values to modifiers
	private static final Map<Integer, Integer> M_ATTRIBUTE_MODIFIERS = new HashMap<>();

	// SWN number mapping of attributes bonuses
	static
	{
		M_ATTRIBUTE_MODIFIERS.put(3, -2);
		M_ATTRIBUTE_MODIFIERS.put(4, -1);
		M_ATTRIBUTE_MODIFIERS.put(5, -1);
		M_ATTRIBUTE_MODIFIERS.put(6, -1);
		M_ATTRIBUTE_MODIFIERS.put(7, -1);
		M_ATTRIBUTE_MODIFIERS.put(8, 0);
		M_ATTRIBUTE_MODIFIERS.put(9, 0);
		M_ATTRIBUTE_MODIFIERS.put(10, 0);
		M_ATTRIBUTE_MODIFIERS.put(11, 0);
		M_ATTRIBUTE_MODIFIERS.put(12, 0);
		M_ATTRIBUTE_MODIFIERS.put(13, 0);
		M_ATTRIBUTE_MODIFIERS.put(14, 1);
		M_ATTRIBUTE_MODIFIERS.put(15, 1);
		M_ATTRIBUTE_MODIFIERS.put(16, 1);
		M_ATTRIBUTE_MODIFIERS.put(17, 1);
		M_ATTRIBUTE_MODIFIERS.put(18, 2);
	}

	private final Random random = new Random();

	/**
	 * Calculate a random attribute value (3d6).
	 *
	 * @return A random attribute value between 3 and 18
	 */
	public int calculateAttribute()
	{
		return random.nextInt(1, 6) +
			   random.nextInt(1, 6) +
			   random.nextInt(1, 6);
	}

	/**
	 * Calculate the modifier for an attribute value.
	 *
	 * @param theAttributeValue The attribute value
	 * @return The modifier for the attribute value
	 * @throws IllegalArgumentException if the attribute value is invalid
	 */
	public int calculateModifier(int theAttributeValue)
	{
		if (theAttributeValue == 0)
		{
			// unset attribute value
			return 0;
		}

		if (!M_ATTRIBUTE_MODIFIERS.containsKey(theAttributeValue))
		{
			throw new IllegalArgumentException("Invalid attribute value: " + theAttributeValue);
		}

		return M_ATTRIBUTE_MODIFIERS.get(theAttributeValue);
	}

	/**
	 * Get the value for a changed attribute (always 14).
	 *
	 * @return The value for a changed attribute
	 */
	@SuppressWarnings("SameReturnValue")
	public int getChangedAttributeValue()
	{
		return 14;
	}
}