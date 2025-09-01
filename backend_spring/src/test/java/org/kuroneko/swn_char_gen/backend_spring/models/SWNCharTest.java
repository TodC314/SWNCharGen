/*
 * SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
 * https://sine-nomine-publishing.myshopify.com/
 * Used in accordance with his Discord message of limiting content to the free version of the rules.
 * Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.
 */

package org.kuroneko.swn_char_gen.backend_spring.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SWNChar class.
 * These tests verify the functionality of the SWNChar model class,
 * including character creation, attribute manipulation, and data conversion.
 */
public class SWNCharTest
{
	/**
	 * The SWNChar instance used for testing.
	 */
	private SWNChar character;

	/**
	 * Sets up a fresh SWNChar instance before each test.
	 * This ensures that each test starts with a clean character object.
	 */
	@BeforeEach
	void setUp()
	{
		character = new SWNChar();
	}

	/**
	 * Tests that the default constructor creates a character with the expected default values.
	 * Verifies that all attributes, modifiers, and other fields are initialized correctly.
	 */
	@Test
	@DisplayName("Test default constructor creates a character with default values")
	void testDefaultConstructor()
	{
		// A new character should have default values
		try
		{
			Map<String, Object> charMap = character.toMap();

			// Check default values
			assertEquals("Default Name", charMap.get("mName"));
			assertEquals(0, charMap.get("mStrength"));
			assertEquals(0, charMap.get("mDexterity"));
			assertEquals(0, charMap.get("mConstitution"));
			assertEquals(0, charMap.get("mIntelligence"));
			assertEquals(0, charMap.get("mWisdom"));
			assertEquals(0, charMap.get("mCharisma"));
			assertEquals(0, charMap.get("mStrengthModifier"));
			assertEquals(0, charMap.get("mDexterityModifier"));
			assertEquals(0, charMap.get("mConstitutionModifier"));
			assertEquals(0, charMap.get("mIntelligenceModifier"));
			assertEquals(0, charMap.get("mWisdomModifier"));
			assertEquals(0, charMap.get("mCharismaModifier"));
			assertEquals(AttributeEnum.NONE.toString(), charMap.get("mChangedAttribute"));
			assertEquals(0, charMap.get("mChangedAttributeOriginalValue"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests that the constructor with a Map argument correctly initializes a character.
	 * Creates a character with specific attribute values and verifies that all fields
	 * are set correctly from the provided Map.
	 *
	 * @throws IOException if there's an error during character creation
	 */
	@SuppressWarnings("ExtractMethodRecommender")
	@Test
	@DisplayName("Test constructor with Map argument")
	void testConstructorWithMap() throws IOException
	{
		// Create a Map with character data
		// suppressing ExtractMethodRecommender - want to keep original and test values in same function
		Map<String, Object> charData = new HashMap<>();
		charData.put("mName", "Test Character");
		charData.put("mStrength", 14);
		charData.put("mDexterity", 12);
		charData.put("mConstitution", 10);
		charData.put("mIntelligence", 16);
		charData.put("mWisdom", 8);
		charData.put("mCharisma", 6);
		charData.put("mStrengthModifier", 1);
		charData.put("mDexterityModifier", 0);
		charData.put("mConstitutionModifier", 0);
		charData.put("mIntelligenceModifier", 2);
		charData.put("mWisdomModifier", -1);
		charData.put("mCharismaModifier", -2);
		charData.put("mChangedAttribute", "STRENGTH");
		charData.put("mChangedAttributeOriginalValue", 10);

		// Create a character with the Map
		SWNChar charFromMap = new SWNChar(charData);

		// Check that the character data was loaded correctly
		try
		{
			Map<String, Object> resultMap = charFromMap.toMap();

			assertEquals("Test Character", resultMap.get("mName"));
			assertEquals(14, resultMap.get("mStrength"));
			assertEquals(12, resultMap.get("mDexterity"));
			assertEquals(10, resultMap.get("mConstitution"));
			assertEquals(16, resultMap.get("mIntelligence"));
			assertEquals(8, resultMap.get("mWisdom"));
			assertEquals(6, resultMap.get("mCharisma"));
			assertEquals(1, resultMap.get("mStrengthModifier"));
			assertEquals(0, resultMap.get("mDexterityModifier"));
			assertEquals(0, resultMap.get("mConstitutionModifier"));
			assertEquals(2, resultMap.get("mIntelligenceModifier"));
			assertEquals(-1, resultMap.get("mWisdomModifier"));
			assertEquals(-2, resultMap.get("mCharismaModifier"));
			assertEquals("STRENGTH", resultMap.get("mChangedAttribute"));
			assertEquals(10, resultMap.get("mChangedAttributeOriginalValue"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests that the constructor with a null Map argument creates a character with default values.
	 * Verifies that when null is passed to the constructor, it creates a character with
	 * the same default values as the default constructor.
	 *
	 * @throws IOException if there's an error during character creation
	 */
	@Test
	@DisplayName("Test constructor with null Map")
	void testConstructorWithNullMap() throws IOException
	{
		// Create a character with a null Map
		SWNChar charFromNullMap = new SWNChar(null);

		// Check that the character has default values
		try
		{
			Map<String, Object> resultMap = charFromNullMap.toMap();

			assertEquals("Default Name", resultMap.get("mName"));
			assertEquals(0, resultMap.get("mStrength"));
			assertEquals(0, resultMap.get("mDexterity"));
			assertEquals(0, resultMap.get("mConstitution"));
			assertEquals(0, resultMap.get("mIntelligence"));
			assertEquals(0, resultMap.get("mWisdom"));
			assertEquals(0, resultMap.get("mCharisma"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests the fromMap method for loading character data from a Map.
	 * Verifies that the method correctly updates the character's fields
	 * with the values from the provided Map.
	 *
	 * @throws IOException if there's an error during data loading
	 */
	@Test
	@DisplayName("Test fromMap method")
	void testFromMap() throws IOException
	{
		// Create a Map with character data
		Map<String, Object> charData = new HashMap<>();
		charData.put("mName", "Test Character");
		charData.put("mStrength", 14);
		charData.put("mDexterity", 12);
		charData.put("mConstitution", 10);
		charData.put("mIntelligence", 16);
		charData.put("mWisdom", 8);
		charData.put("mCharisma", 6);

		// Load the data into the character
		character.fromMap(charData);

		// Check that the character data was loaded correctly
		try
		{
			Map<String, Object> resultMap = character.toMap();
			assertEquals("Test Character", resultMap.get("mName"));
			assertEquals(14, resultMap.get("mStrength"));
			assertEquals(12, resultMap.get("mDexterity"));
			assertEquals(10, resultMap.get("mConstitution"));
			assertEquals(16, resultMap.get("mIntelligence"));
			assertEquals(8, resultMap.get("mWisdom"));
			assertEquals(6, resultMap.get("mCharisma"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests the setDetail method for setting a character detail.
	 * Verifies that the method correctly updates the character's name
	 * when the NAME detail is set.
	 */
	@Test
	@DisplayName("Test setDetail method")
	void testSetDetail()
	{
		// Set the name
		character.setDetail(DetailEnum.NAME, "Test Character");

		// Check that the name was set correctly
		try
		{
			Map<String, Object> resultMap = character.toMap();

			assertEquals("Test Character", resultMap.get("mName"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests that the setDetail method throws an IllegalArgumentException when an invalid detail is provided.
	 * Verifies that the method correctly validates the detail parameter and throws
	 * an exception with the appropriate message when an invalid detail (NONE) is used.
	 */
	@Test
	@DisplayName("Test setDetail method with invalid detail")
	void testSetDetailWithInvalidDetail()
	{
		// Try to set an invalid detail
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				character.setDetail(DetailEnum.NONE, "Test Value"));

		// Check that the exception message is correct
		assertEquals("Invalid detail: NONE", exception.getMessage());
	}

	/**
	 * Tests the rollAttributes method for generating random attribute values.
	 * Verifies that:
	 * 1. All attributes are assigned non-zero values
	 * 2. All attributes are within the valid range (3-18)
	 * 3. All attribute modifiers are calculated correctly based on the attribute values
	 */
	@Test
	@DisplayName("Test rollAttributes method")
	void testRollAttributes()
	{
		// Roll attributes
		character.rollAttributes();

		// Check that all attributes have non-zero values and are between 3 and 18 inclusive
		try
		{
			Map<String, Object> resultMap = character.toMap();

			int strength = (int) resultMap.get("mStrength");
			int dexterity = (int) resultMap.get("mDexterity");
			int constitution = (int) resultMap.get("mConstitution");
			int intelligence = (int) resultMap.get("mIntelligence");
			int wisdom = (int) resultMap.get("mWisdom");
			int charisma = (int) resultMap.get("mCharisma");

			assertNotEquals(0, strength);
			assertNotEquals(0, dexterity);
			assertNotEquals(0, constitution);
			assertNotEquals(0, intelligence);
			assertNotEquals(0, wisdom);
			assertNotEquals(0, charisma);

			assertTrue(strength >= 3 && strength <= 18);
			assertTrue(dexterity >= 3 && dexterity <= 18);
			assertTrue(constitution >= 3 && constitution <= 18);
			assertTrue(intelligence >= 3 && intelligence <= 18);
			assertTrue(wisdom >= 3 && wisdom <= 18);
			assertTrue(charisma >= 3 && charisma <= 18);

			// Check that modifiers are set correctly
			int strengthModifier = (int) resultMap.get("mStrengthModifier");
			int dexterityModifier = (int) resultMap.get("mDexterityModifier");
			int constitutionModifier = (int) resultMap.get("mConstitutionModifier");
			int intelligenceModifier = (int) resultMap.get("mIntelligenceModifier");
			int wisdomModifier = (int) resultMap.get("mWisdomModifier");
			int charismaModifier = (int) resultMap.get("mCharismaModifier");

			// Create a SWNSystem to calculate expected modifiers
			SWNSystem system = new SWNSystem();

			assertEquals(system.calculateModifier(strength), strengthModifier);
			assertEquals(system.calculateModifier(dexterity), dexterityModifier);
			assertEquals(system.calculateModifier(constitution), constitutionModifier);
			assertEquals(system.calculateModifier(intelligence), intelligenceModifier);
			assertEquals(system.calculateModifier(wisdom), wisdomModifier);
			assertEquals(system.calculateModifier(charisma), charismaModifier);
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests the changeOneAttribute method for changing a specific attribute to a special value.
	 * Verifies that:
	 * 1. The specified attribute is changed to the special value (14)
	 * 2. The attribute modifier is updated correctly
	 * 3. The changed attribute and its original value are tracked
	 * 4. When a different attribute is changed, the previously changed attribute is restored
	 * to its original value
	 */
	@Test
	@DisplayName("Test changeOneAttribute method")
	void testChangeOneAttribute()
	{
		// Roll attributes first to get initial values
		character.rollAttributes();

		// Get the initial strength value
		int initialStrength = 0;
		try
		{
			Map<String, Object> initialMap = character.toMap();
			initialStrength = (int) initialMap.get("mStrength");
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}

		// Change the strength attribute
		character.changeOneAttribute(AttributeEnum.STRENGTH);

		// Check that the strength attribute was changed to 14 and the modifier was updated
		try
		{
			Map<String, Object> resultMap = character.toMap();

			assertEquals(14, resultMap.get("mStrength"));
			assertEquals(1, resultMap.get("mStrengthModifier"));
			assertEquals("STRENGTH", resultMap.get("mChangedAttribute"));
			assertEquals(initialStrength, resultMap.get("mChangedAttributeOriginalValue"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}

		// Change another attribute
		character.changeOneAttribute(AttributeEnum.DEXTERITY);

		// Check that the dexterity attribute was changed to 14, the modifier was updated,
		// and the strength attribute was restored to its original value
		try
		{
			Map<String, Object> resultMap = character.toMap();

			assertEquals(initialStrength, resultMap.get("mStrength"));
			assertEquals(14, resultMap.get("mDexterity"));
			assertEquals(1, resultMap.get("mDexterityModifier"));
			assertEquals("DEXTERITY", resultMap.get("mChangedAttribute"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Tests the toMap method for converting a character to a Map representation.
	 * Verifies that:
	 * 1. The Map contains all the expected keys for character fields
	 * 2. The values in the Map match the character's field values
	 * 3. The conversion process works correctly after setting character details
	 * and rolling attributes
	 */
	@Test
	@DisplayName("Test toMap method")
	void testToMap()
	{
		// Set some values
		character.setDetail(DetailEnum.NAME, "Test Character");
		character.rollAttributes();

		// Get the Map representation
		try
		{
			Map<String, Object> resultMap = character.toMap();

			// Check that the Map has the expected keys
			assertTrue(resultMap.containsKey("mName"));
			assertTrue(resultMap.containsKey("mStrength"));
			assertTrue(resultMap.containsKey("mDexterity"));
			assertTrue(resultMap.containsKey("mConstitution"));
			assertTrue(resultMap.containsKey("mIntelligence"));
			assertTrue(resultMap.containsKey("mWisdom"));
			assertTrue(resultMap.containsKey("mCharisma"));
			assertTrue(resultMap.containsKey("mStrengthModifier"));
			assertTrue(resultMap.containsKey("mDexterityModifier"));
			assertTrue(resultMap.containsKey("mConstitutionModifier"));
			assertTrue(resultMap.containsKey("mIntelligenceModifier"));
			assertTrue(resultMap.containsKey("mWisdomModifier"));
			assertTrue(resultMap.containsKey("mCharismaModifier"));
			assertTrue(resultMap.containsKey("mChangedAttribute"));
			assertTrue(resultMap.containsKey("mChangedAttributeOriginalValue"));

			// Check that the values are as expected
			assertEquals("Test Character", resultMap.get("mName"));
			assertNotEquals(0, resultMap.get("mStrength"));
			assertNotEquals(0, resultMap.get("mDexterity"));
			assertNotEquals(0, resultMap.get("mConstitution"));
			assertNotEquals(0, resultMap.get("mIntelligence"));
			assertNotEquals(0, resultMap.get("mWisdom"));
			assertNotEquals(0, resultMap.get("mCharisma"));
		}
		catch (IllegalArgumentException e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}
}