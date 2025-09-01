#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Unit tests for the SWNChar class.

This module contains unit tests for the SWNChar class, testing its methods
for creating, manipulating, and serializing character data.
"""

import unittest
from typing import Dict, List, Callable
from swn_char.swn_char import SWNChar
from swn_char.swn_char_data import SWNCharData
from swn_char.swn_system import AttributeEnum


class TestSWNChar(unittest.TestCase):
	"""
	Test case for the SWNChar class.
	
	This class contains unit tests for the SWNChar class, testing its methods
	for creating, manipulating, and serializing character data.
	"""

	def test_set_attributes(self: 'TestSWNChar') -> None:
		"""
		Test setting attributes and changing one attribute.
		
		This test verifies that:
		1. A new character has initial attribute values of 0
		2. After rolling attributes, all attributes have non-zero values
		3. After changing one attribute, it has the expected value and modifier
		"""
		# Create a character
		character: SWNChar = SWNChar()

		# Initial values should be 0
		self.assertEqual(character._m_data.mStrength, 0)
		self.assertEqual(character._m_data.mStrengthModifier, 0)

		character.roll_attributes()

		self.assertNotEqual(character._m_data.mStrength, 0)
		self.assertNotEqual(character._m_data.mDexterity, 0)
		self.assertNotEqual(character._m_data.mConstitution, 0)
		self.assertNotEqual(character._m_data.mIntelligence, 0)
		self.assertNotEqual(character._m_data.mWisdom, 0)
		self.assertNotEqual(character._m_data.mCharisma, 0)

		# test changing one attribute to 14
		character.change_one_attribute(AttributeEnum.STRENGTH)

		# Check that only specified attributes were changed
		self.assertEqual(character._m_data.mStrength, 14)
		self.assertEqual(character._m_data.mStrengthModifier, 1)

	def test_to_dict(self: 'TestSWNChar') -> None:
		"""Test that the to_dict method returns the expected keys and values."""
		# Create a character with known values
		character: SWNChar = SWNChar()
		character.roll_attributes()
		# Get the dictionary representation
		char_dict: Dict[str, any] = character.to_dict()
		# Check that the dictionary has the expected keys and values
		self.assertEqual(char_dict["mStrength"], character._m_data.mStrength)
		self.assertEqual(char_dict["mDexterity"], character._m_data.mDexterity)
		self.assertEqual(char_dict["mConstitution"], character._m_data.mConstitution)
		self.assertEqual(char_dict["mIntelligence"], character._m_data.mIntelligence)
		self.assertEqual(char_dict["mWisdom"], character._m_data.mWisdom)
		self.assertEqual(char_dict["mCharisma"], character._m_data.mCharisma)
		self.assertEqual(char_dict["mStrengthModifier"], character._m_data.mStrengthModifier)
		self.assertEqual(char_dict["mDexterityModifier"], character._m_data.mDexterityModifier)
		self.assertEqual(char_dict["mConstitutionModifier"], character._m_data.mConstitutionModifier)
		self.assertEqual(char_dict["mIntelligenceModifier"], character._m_data.mIntelligenceModifier)
		self.assertEqual(char_dict["mWisdomModifier"], character._m_data.mWisdomModifier)
		self.assertEqual(char_dict["mCharismaModifier"], character._m_data.mCharismaModifier)

	def test_new_character(self: 'TestSWNChar') -> None:
		"""
		Test creating a new character.
		
		This test verifies that:
		1. A new character has initial attribute values of 0
		2. After rolling attributes, all attributes are between 3 and 18 inclusive
		"""
		character: SWNChar = SWNChar()
		# check that each attribute is zero
		self.assertEqual(character._m_data.mStrength, 0)
		self.assertEqual(character._m_data.mDexterity, 0)
		self.assertEqual(character._m_data.mConstitution, 0)
		self.assertEqual(character._m_data.mIntelligence, 0)
		self.assertEqual(character._m_data.mWisdom, 0)
		self.assertEqual(character._m_data.mCharisma, 0)
		# check that each attribute after rolling is between 3 and 18 inclusive
		character.roll_attributes()
		attributes: List[Callable[[SWNChar], int]] = [
			lambda data: data.mStrength,
			lambda data: data.mDexterity,
			lambda data: data.mConstitution,
			lambda data: data.mIntelligence,
			lambda data: data.mWisdom,
			lambda data: data.mCharisma,
		]
		attr_getter: Callable[[SWNCharData], int]
		for attr_getter in attributes:
			value: int = attr_getter(character._m_data)
			self.assertGreaterEqual(value, 3)
			self.assertLessEqual(value, 18)

	def test_from_dict(self: 'TestSWNChar') -> None:
		"""Test that the from_dict method correctly loads character data from a dictionary."""
		# Create a dictionary with character data
		char_data = {
			"mName": "Test Character",
			"mStrength": 14,
			"mDexterity": 12,
			"mConstitution": 10,
			"mIntelligence": 16,
			"mWisdom": 8,
			"mCharisma": 6,
			"mStrengthModifier": 1,
			"mDexterityModifier": 0,
			"mConstitutionModifier": 0,
			"mIntelligenceModifier": 2,
			"mWisdomModifier": -1,
			"mCharismaModifier": -2,
			"mChangedAttribute": "STRENGTH",
			"mChangedAttributeOriginalValue": 10
		}
		
		# Create a character and load the data
		character = SWNChar()
		character.from_dict(char_data)
		
		# Check that the character data was loaded correctly
		self.assertEqual(character._m_data.mName, "Test Character")
		self.assertEqual(character._m_data.mStrength, 14)
		self.assertEqual(character._m_data.mDexterity, 12)
		self.assertEqual(character._m_data.mConstitution, 10)
		self.assertEqual(character._m_data.mIntelligence, 16)
		self.assertEqual(character._m_data.mWisdom, 8)
		self.assertEqual(character._m_data.mCharisma, 6)
		self.assertEqual(character._m_data.mStrengthModifier, 1)
		self.assertEqual(character._m_data.mDexterityModifier, 0)
		self.assertEqual(character._m_data.mConstitutionModifier, 0)
		self.assertEqual(character._m_data.mIntelligenceModifier, 2)
		self.assertEqual(character._m_data.mWisdomModifier, -1)
		self.assertEqual(character._m_data.mCharismaModifier, -2)
		self.assertEqual(character._m_data.mChangedAttribute, AttributeEnum.STRENGTH)
		self.assertEqual(character._m_data.mChangedAttributeOriginalValue, 10)
		
		# Test creating a character directly from a dictionary
		character2 = SWNChar(char_data)
		self.assertEqual(character2._m_data.mName, "Test Character")
		self.assertEqual(character2._m_data.mStrength, 14)

if __name__ == "__main__":
	unittest.main()
