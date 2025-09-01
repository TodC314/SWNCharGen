#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import unittest
from swn_char.swn_system import AttributeEnum


class TestAttributeEnum(unittest.TestCase):
	def test_enum_values(self) -> None:
		# Verify number of values
		self.assertEqual(7, len(list(AttributeEnum)))
		# Verify each enum exists
		self.assertIsNotNone(AttributeEnum.STRENGTH)
		self.assertIsNotNone(AttributeEnum.DEXTERITY)
		self.assertIsNotNone(AttributeEnum.CONSTITUTION)
		self.assertIsNotNone(AttributeEnum.INTELLIGENCE)
		self.assertIsNotNone(AttributeEnum.WISDOM)
		self.assertIsNotNone(AttributeEnum.CHARISMA)
		self.assertIsNotNone(AttributeEnum.NONE)

	def test_enum_names(self) -> None:
		self.assertEqual("STRENGTH", AttributeEnum.STRENGTH.name)
		self.assertEqual("DEXTERITY", AttributeEnum.DEXTERITY.name)
		self.assertEqual("CONSTITUTION", AttributeEnum.CONSTITUTION.name)
		self.assertEqual("INTELLIGENCE", AttributeEnum.INTELLIGENCE.name)
		self.assertEqual("WISDOM", AttributeEnum.WISDOM.name)
		self.assertEqual("CHARISMA", AttributeEnum.CHARISMA.name)
		self.assertEqual("NONE", AttributeEnum.NONE.name)

	def test_value_of(self) -> None:
		self.assertEqual(AttributeEnum.STRENGTH, AttributeEnum["STRENGTH"]) 
		self.assertEqual(AttributeEnum.DEXTERITY, AttributeEnum["DEXTERITY"]) 
		self.assertEqual(AttributeEnum.CONSTITUTION, AttributeEnum["CONSTITUTION"]) 
		self.assertEqual(AttributeEnum.INTELLIGENCE, AttributeEnum["INTELLIGENCE"]) 
		self.assertEqual(AttributeEnum.WISDOM, AttributeEnum["WISDOM"]) 
		self.assertEqual(AttributeEnum.CHARISMA, AttributeEnum["CHARISMA"]) 
		self.assertEqual(AttributeEnum.NONE, AttributeEnum["NONE"]) 

	def test_value_of_invalid(self) -> None:
		with self.assertRaises(KeyError):
			_ = AttributeEnum["INVALID"]
