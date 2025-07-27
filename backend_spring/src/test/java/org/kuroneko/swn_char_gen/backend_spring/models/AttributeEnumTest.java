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
 * Tests for the AttributeEnum class.
 */
class AttributeEnumTest
{

	@Test
	@DisplayName("Test that all expected attribute enum values exist")
	void testEnumValues()
	{
		// Verify all expected enum values exist
		assertEquals(7, AttributeEnum.values().length, "AttributeEnum should have 7 values");

		// Verify each enum value
		assertNotNull(AttributeEnum.STRENGTH, "STRENGTH enum value should exist");
		assertNotNull(AttributeEnum.DEXTERITY, "DEXTERITY enum value should exist");
		assertNotNull(AttributeEnum.CONSTITUTION, "CONSTITUTION enum value should exist");
		assertNotNull(AttributeEnum.INTELLIGENCE, "INTELLIGENCE enum value should exist");
		assertNotNull(AttributeEnum.WISDOM, "WISDOM enum value should exist");
		assertNotNull(AttributeEnum.CHARISMA, "CHARISMA enum value should exist");
		assertNotNull(AttributeEnum.NONE, "NONE enum value should exist");
	}

	@Test
	@DisplayName("Test enum value names")
	void testEnumNames()
	{
		// Verify the name of each enum value
		assertEquals("STRENGTH", AttributeEnum.STRENGTH.name(), "STRENGTH enum name should match");
		assertEquals("DEXTERITY", AttributeEnum.DEXTERITY.name(), "DEXTERITY enum name should match");
		assertEquals("CONSTITUTION", AttributeEnum.CONSTITUTION.name(), "CONSTITUTION enum name should match");
		assertEquals("INTELLIGENCE", AttributeEnum.INTELLIGENCE.name(), "INTELLIGENCE enum name should match");
		assertEquals("WISDOM", AttributeEnum.WISDOM.name(), "WISDOM enum name should match");
		assertEquals("CHARISMA", AttributeEnum.CHARISMA.name(), "CHARISMA enum name should match");
		assertEquals("NONE", AttributeEnum.NONE.name(), "NONE enum name should match");
	}

	@Test
	@DisplayName("Test valueOf method")
	void testValueOf()
	{
		// Verify valueOf method works for each enum value
		assertEquals(AttributeEnum.STRENGTH, AttributeEnum.valueOf("STRENGTH"), "valueOf should return STRENGTH enum");
		assertEquals(AttributeEnum.DEXTERITY, AttributeEnum.valueOf("DEXTERITY"), "valueOf should return DEXTERITY enum");
		assertEquals(AttributeEnum.CONSTITUTION, AttributeEnum.valueOf("CONSTITUTION"), "valueOf should return CONSTITUTION enum");
		assertEquals(AttributeEnum.INTELLIGENCE, AttributeEnum.valueOf("INTELLIGENCE"), "valueOf should return INTELLIGENCE enum");
		assertEquals(AttributeEnum.WISDOM, AttributeEnum.valueOf("WISDOM"), "valueOf should return WISDOM enum");
		assertEquals(AttributeEnum.CHARISMA, AttributeEnum.valueOf("CHARISMA"), "valueOf should return CHARISMA enum");
		assertEquals(AttributeEnum.NONE, AttributeEnum.valueOf("NONE"), "valueOf should return NONE enum");
	}

	@Test
	@DisplayName("Test valueOf method with invalid name")
	void testValueOfInvalid()
	{
		// Verify valueOf method throws exception for invalid enum name
		assertThrows(IllegalArgumentException.class, () -> AttributeEnum.valueOf("INVALID"),
					 "valueOf should throw IllegalArgumentException for invalid enum name");
	}

	@Test
	@DisplayName("Test ordinal values")
	void testOrdinals()
	{
		// Verify the ordinal of each enum value
		assertEquals(0, AttributeEnum.STRENGTH.ordinal(), "STRENGTH ordinal should be 0");
		assertEquals(1, AttributeEnum.DEXTERITY.ordinal(), "DEXTERITY ordinal should be 1");
		assertEquals(2, AttributeEnum.CONSTITUTION.ordinal(), "CONSTITUTION ordinal should be 2");
		assertEquals(3, AttributeEnum.INTELLIGENCE.ordinal(), "INTELLIGENCE ordinal should be 3");
		assertEquals(4, AttributeEnum.WISDOM.ordinal(), "WISDOM ordinal should be 4");
		assertEquals(5, AttributeEnum.CHARISMA.ordinal(), "CHARISMA ordinal should be 5");
		assertEquals(6, AttributeEnum.NONE.ordinal(), "NONE ordinal should be 6");
	}
}