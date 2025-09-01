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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DetailEnum class.
 */
class DetailEnumTest
{

	@Test
	@DisplayName("Test that all expected detail enum values exist")
	void testEnumValues()
	{
		// Verify all expected enum values exist
		assertEquals(2, DetailEnum.values().length, "DetailEnum should have 2 values");

		// Verify each enum value
		assertNotNull(DetailEnum.NAME, "NAME enum value should exist");
		assertNotNull(DetailEnum.NONE, "NONE enum value should exist");
	}

	@Test
	@DisplayName("Test enum value names")
	void testEnumNames()
	{
		// Verify the name of each enum value
		assertEquals("NAME", DetailEnum.NAME.name(), "NAME enum name should match");
		assertEquals("NONE", DetailEnum.NONE.name(), "NONE enum name should match");
	}

	@Test
	@DisplayName("Test valueOf method")
	void testValueOf()
	{
		// Verify valueOf method works for each enum value
		assertEquals(DetailEnum.NAME, DetailEnum.valueOf("NAME"), "valueOf should return NAME enum");
		assertEquals(DetailEnum.NONE, DetailEnum.valueOf("NONE"), "valueOf should return NONE enum");
	}

	@Test
	@DisplayName("Test valueOf method with invalid name")
	void testValueOfInvalid()
	{
		// Verify valueOf method throws exception for invalid enum name
		assertThrows(IllegalArgumentException.class, () -> DetailEnum.valueOf("INVALID"),
					 "valueOf should throw IllegalArgumentException for invalid enum name");
	}

	@Test
	@DisplayName("Test ordinal values")
	void testOrdinals()
	{
		// Verify the ordinal of each enum value
		assertEquals(0, DetailEnum.NAME.ordinal(), "NAME ordinal should be 0");
		assertEquals(1, DetailEnum.NONE.ordinal(), "NONE ordinal should be 1");
	}
}