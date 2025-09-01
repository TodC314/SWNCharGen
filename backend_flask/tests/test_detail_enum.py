#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import unittest
from swn_char.swn_system import DetailEnum


class TestDetailEnum(unittest.TestCase):
	def test_enum_values(self) -> None:
		self.assertEqual(2, len(list(DetailEnum)))
		self.assertIsNotNone(DetailEnum.NAME)
		self.assertIsNotNone(DetailEnum.NONE)

	def test_enum_names(self) -> None:
		self.assertEqual("NAME", DetailEnum.NAME.name)
		self.assertEqual("NONE", DetailEnum.NONE.name)

	def test_value_of(self) -> None:
		self.assertEqual(DetailEnum.NAME, DetailEnum["NAME"]) 
		self.assertEqual(DetailEnum.NONE, DetailEnum["NONE"]) 

	def test_value_of_invalid(self) -> None:
		with self.assertRaises(KeyError):
			_ = DetailEnum["INVALID"]
