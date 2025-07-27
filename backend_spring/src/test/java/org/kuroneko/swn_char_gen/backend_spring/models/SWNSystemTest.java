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
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SWNSystem class.
 */
class SWNSystemTest
{

	@Test
	@DisplayName("Test getChangedAttributeValue returns 14")
	void testGetChangedAttributeValue()
	{
		// Create a new SWNSystem instance
		SWNSystem system = new SWNSystem();

		// Verify getChangedAttributeValue returns 14
		assertEquals(14, system.getChangedAttributeValue(),
					 "getChangedAttributeValue should return 14");
	}

	@RepeatedTest(100)
	@DisplayName("Test calculateAttribute returns value between 3 and 18")
	void testCalculateAttribute()
	{
		// Create a new SWNSystem instance
		SWNSystem system = new SWNSystem();

		// Calculate an attribute value
		int attributeValue = system.calculateAttribute();

		// Verify the attribute value is between 3 and 18 (inclusive)
		assertTrue(attributeValue >= 3 && attributeValue <= 18,
				   "calculateAttribute should return a value between 3 and 18");
	}

	@ParameterizedTest
	@CsvSource({
			"3, -2",
			"4, -1",
			"5, -1",
			"6, -1",
			"7, -1",
			"8, 0",
			"9, 0",
			"10, 0",
			"11, 0",
			"12, 0",
			"13, 0",
			"14, 1",
			"15, 1",
			"16, 1",
			"17, 1",
			"18, 2"
	})
	@DisplayName("Test calculateModifier returns correct modifier for attribute value")
	void testCalculateModifier(int attributeValue, int expectedModifier)
	{
		// Create a new SWNSystem instance
		SWNSystem system = new SWNSystem();

		// Calculate the modifier for the attribute value
		int modifier = system.calculateModifier(attributeValue);

		// Verify the modifier matches the expected value
		assertEquals(expectedModifier, modifier,
					 "calculateModifier should return " + expectedModifier + " for attribute value " + attributeValue);
	}

	@Test
	@DisplayName("Test calculateModifier returns 0 for attribute value 0")
	void testCalculateModifierForZero()
	{
		// Create a new SWNSystem instance
		SWNSystem system = new SWNSystem();

		// Calculate the modifier for attribute value 0
		int modifier = system.calculateModifier(0);

		// Verify the modifier is 0
		assertEquals(0, modifier,
					 "calculateModifier should return 0 for attribute value 0");
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 2, 19, 20})
	@DisplayName("Test calculateModifier throws exception for invalid attribute value")
	void testCalculateModifierForInvalidValue(int invalidValue)
	{
		// Create a new SWNSystem instance
		SWNSystem system = new SWNSystem();

		// Verify calculateModifier throws IllegalArgumentException for invalid attribute value
		Exception exception = assertThrows(IllegalArgumentException.class,
										   () -> system.calculateModifier(invalidValue),
										   "calculateModifier should throw IllegalArgumentException for invalid attribute value");

		// Verify the exception message
		assertTrue(exception.getMessage().contains("Invalid attribute value: " + invalidValue),
				   "Exception message should contain 'Invalid attribute value: " + invalidValue + "'");
	}

	@Test
	@DisplayName("Test attribute distribution is roughly bell-shaped")
	void testAttributeDistribution()
	{
		// Create a new SWNSystem instance
		SWNSystem system = new SWNSystem();

		// Number of samples - increased for more reliable statistics
		int numSamples = 5000;

		// Count occurrences of each attribute value
		int[] counts = new int[19]; // Index 0 unused, values 3-18 at indices 3-18

		// Generate attribute values
		for (int i = 0; i < numSamples; i++)
		{
			int attributeValue = system.calculateAttribute();
			counts[attributeValue]++;
		}

		// Group the values into low (3-7), middle (8-13), and high (14-18) ranges
		int lowSum = 0;
		for (int i = 3; i <= 7; i++)
		{
			lowSum += counts[i];
		}

		int middleSum = 0;
		for (int i = 8; i <= 13; i++)
		{
			middleSum += counts[i];
		}

		int highSum = 0;
		for (int i = 14; i <= 18; i++)
		{
			highSum += counts[i];
		}

		// Verify the distribution is roughly bell-shaped (middle values more common)
		assertTrue(middleSum > lowSum,
				   "Middle attribute values (8-13) should be more common than low values (3-7)");
		assertTrue(middleSum > highSum,
				   "Middle attribute values (8-13) should be more common than high values (14-18)");
	}
}