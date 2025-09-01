#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import unittest
from swn_char.swn_char import SWNChar
from swn_char.swn_system import DetailEnum, AttributeEnum, SWNSystem


class TestSWNCharExtras(unittest.TestCase):
	def test_default_constructor_values(self) -> None:
		char = SWNChar()
		d = char.to_dict()
		self.assertEqual("Default Name", d.get('mName'))
		self.assertEqual(0, d.get('mStrength'))
		self.assertEqual(0, d.get('mDexterity'))
		self.assertEqual(0, d.get('mConstitution'))
		self.assertEqual(0, d.get('mIntelligence'))
		self.assertEqual(0, d.get('mWisdom'))
		self.assertEqual(0, d.get('mCharisma'))
		self.assertEqual(0, d.get('mStrengthModifier'))
		self.assertEqual(0, d.get('mDexterityModifier'))
		self.assertEqual(0, d.get('mConstitutionModifier'))
		self.assertEqual(0, d.get('mIntelligenceModifier'))
		self.assertEqual(0, d.get('mWisdomModifier'))
		self.assertEqual(0, d.get('mCharismaModifier'))
		self.assertEqual("NONE", d.get('mChangedAttribute'))
		self.assertEqual(0, d.get('mChangedAttributeOriginalValue'))

	def test_set_detail_and_invalid(self) -> None:
		char = SWNChar()
		char.set_detail(DetailEnum.NAME, "Test Character")
		self.assertEqual("Test Character", char.to_dict()["mName"])
		with self.assertRaises(ValueError):
			char.set_detail(DetailEnum.NONE, "Value")

	def test_roll_attributes_and_modifiers(self) -> None:
		char = SWNChar()
		char.roll_attributes()
		d = char.to_dict()
		vals = [d['mStrength'], d['mDexterity'], d['mConstitution'], d['mIntelligence'], d['mWisdom'], d['mCharisma']]
		for v in vals:
			self.assertTrue(3 <= v <= 18)
		sys = SWNSystem()
		self.assertEqual(sys.calculate_modifier(d['mStrength']), d['mStrengthModifier'])
		self.assertEqual(sys.calculate_modifier(d['mDexterity']), d['mDexterityModifier'])
		self.assertEqual(sys.calculate_modifier(d['mConstitution']), d['mConstitutionModifier'])
		self.assertEqual(sys.calculate_modifier(d['mIntelligence']), d['mIntelligenceModifier'])
		self.assertEqual(sys.calculate_modifier(d['mWisdom']), d['mWisdomModifier'])
		self.assertEqual(sys.calculate_modifier(d['mCharisma']), d['mCharismaModifier'])

	def test_change_one_attribute_and_restore(self) -> None:
		char = SWNChar()
		char.roll_attributes()
		initial_strength = char.to_dict()['mStrength']
		char.change_one_attribute(AttributeEnum.STRENGTH)
		d = char.to_dict()
		self.assertEqual(14, d['mStrength'])
		self.assertEqual(1, d['mStrengthModifier'])
		self.assertEqual("STRENGTH", d['mChangedAttribute'])
		self.assertEqual(initial_strength, d['mChangedAttributeOriginalValue'])
		# change to another attribute restores previous
		char.change_one_attribute(AttributeEnum.DEXTERITY)
		d2 = char.to_dict()
		self.assertEqual(initial_strength, d2['mStrength'])
		self.assertEqual(14, d2['mDexterity'])
		self.assertEqual(1, d2['mDexterityModifier'])
		self.assertEqual("DEXTERITY", d2['mChangedAttribute'])
