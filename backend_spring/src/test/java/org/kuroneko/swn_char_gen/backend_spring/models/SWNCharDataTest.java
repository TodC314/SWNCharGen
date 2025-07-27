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

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SWNCharData class.
 */
class SWNCharDataTest
{

	@Test
	@DisplayName("Test default values")
	void testDefaultValues() throws Exception
	{
		// Create a new SWNCharData instance
		SWNCharData charData = new SWNCharData();

		// Use reflection to access the private fields
		Field nameField = SWNCharData.class.getDeclaredField("mName");
		nameField.setAccessible(true);
		assertEquals("Default Name", nameField.get(charData), "Default name should be 'Default Name'");

		// Test attribute values
		Field strengthField = SWNCharData.class.getDeclaredField("mStrength");
		strengthField.setAccessible(true);
		assertEquals(0, strengthField.getInt(charData), "Default strength should be 0");

		Field dexterityField = SWNCharData.class.getDeclaredField("mDexterity");
		dexterityField.setAccessible(true);
		assertEquals(0, dexterityField.getInt(charData), "Default dexterity should be 0");

		Field constitutionField = SWNCharData.class.getDeclaredField("mConstitution");
		constitutionField.setAccessible(true);
		assertEquals(0, constitutionField.getInt(charData), "Default constitution should be 0");

		Field intelligenceField = SWNCharData.class.getDeclaredField("mIntelligence");
		intelligenceField.setAccessible(true);
		assertEquals(0, intelligenceField.getInt(charData), "Default intelligence should be 0");

		Field wisdomField = SWNCharData.class.getDeclaredField("mWisdom");
		wisdomField.setAccessible(true);
		assertEquals(0, wisdomField.getInt(charData), "Default wisdom should be 0");

		Field charismaField = SWNCharData.class.getDeclaredField("mCharisma");
		charismaField.setAccessible(true);
		assertEquals(0, charismaField.getInt(charData), "Default charisma should be 0");

		// Test attribute modifiers
		Field strengthModField = SWNCharData.class.getDeclaredField("mStrengthModifier");
		strengthModField.setAccessible(true);
		assertEquals(0, strengthModField.getInt(charData), "Default strength modifier should be 0");

		Field dexterityModField = SWNCharData.class.getDeclaredField("mDexterityModifier");
		dexterityModField.setAccessible(true);
		assertEquals(0, dexterityModField.getInt(charData), "Default dexterity modifier should be 0");

		Field constitutionModField = SWNCharData.class.getDeclaredField("mConstitutionModifier");
		constitutionModField.setAccessible(true);
		assertEquals(0, constitutionModField.getInt(charData), "Default constitution modifier should be 0");

		Field intelligenceModField = SWNCharData.class.getDeclaredField("mIntelligenceModifier");
		intelligenceModField.setAccessible(true);
		assertEquals(0, intelligenceModField.getInt(charData), "Default intelligence modifier should be 0");

		Field wisdomModField = SWNCharData.class.getDeclaredField("mWisdomModifier");
		wisdomModField.setAccessible(true);
		assertEquals(0, wisdomModField.getInt(charData), "Default wisdom modifier should be 0");

		Field charismaModField = SWNCharData.class.getDeclaredField("mCharismaModifier");
		charismaModField.setAccessible(true);
		assertEquals(0, charismaModField.getInt(charData), "Default charisma modifier should be 0");

		// Test changed attribute tracking
		Field changedAttrField = SWNCharData.class.getDeclaredField("mChangedAttribute");
		changedAttrField.setAccessible(true);
		assertEquals(AttributeEnum.NONE, changedAttrField.get(charData),
					 "Default changed attribute should be NONE");

		Field changedAttrOrigValField = SWNCharData.class.getDeclaredField("mChangedAttributeOriginalValue");
		changedAttrOrigValField.setAccessible(true);
		assertEquals(0, changedAttrOrigValField.getInt(charData),
					 "Default changed attribute original value should be 0");
	}

	@Test
	@DisplayName("Test field modification")
	void testFieldModification() throws Exception
	{
		// Create a new SWNCharData instance
		SWNCharData charData = new SWNCharData();

		// Use reflection to modify the fields
		Field nameField = SWNCharData.class.getDeclaredField("mName");
		nameField.setAccessible(true);
		nameField.set(charData, "Test Character");

		Field strengthField = SWNCharData.class.getDeclaredField("mStrength");
		strengthField.setAccessible(true);
		strengthField.setInt(charData, 14);

		Field strengthModField = SWNCharData.class.getDeclaredField("mStrengthModifier");
		strengthModField.setAccessible(true);
		strengthModField.setInt(charData, 1);

		Field changedAttrField = SWNCharData.class.getDeclaredField("mChangedAttribute");
		changedAttrField.setAccessible(true);
		changedAttrField.set(charData, AttributeEnum.STRENGTH);

		Field changedAttrOrigValField = SWNCharData.class.getDeclaredField("mChangedAttributeOriginalValue");
		changedAttrOrigValField.setAccessible(true);
		changedAttrOrigValField.setInt(charData, 10);

		// Verify the modifications
		assertEquals("Test Character", nameField.get(charData), "Name should be modified");
		assertEquals(14, strengthField.getInt(charData), "Strength should be modified");
		assertEquals(1, strengthModField.getInt(charData), "Strength modifier should be modified");
		assertEquals(AttributeEnum.STRENGTH, changedAttrField.get(charData), "Changed attribute should be modified");
		assertEquals(10, changedAttrOrigValField.getInt(charData), "Changed attribute original value should be modified");
	}
}