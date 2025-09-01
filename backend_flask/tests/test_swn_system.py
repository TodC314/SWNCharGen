#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import unittest
from swn_char.swn_system import SWNSystem


class TestSWNSystem(unittest.TestCase):
	def test_get_changed_attribute_value(self) -> None:
		system = SWNSystem()
		self.assertEqual(14, system.get_changed_attribute_value())

	def test_calculate_attribute_range(self) -> None:
		system = SWNSystem()
		for _ in range(100):
			val = system.calculate_attribute()
			self.assertGreaterEqual(val, 3)
			self.assertLessEqual(val, 18)

	def test_calculate_modifier_table(self) -> None:
		system = SWNSystem()
		cases = {
			3: -2,
			4: -1, 5: -1, 6: -1, 7: -1,
			8: 0, 9: 0, 10: 0, 11: 0, 12: 0, 13: 0,
			14: 1, 15: 1, 16: 1, 17: 1,
			18: 2,
		}
		for k, v in cases.items():
			self.assertEqual(v, system.calculate_modifier(k))

	def test_calculate_modifier_zero(self) -> None:
		system = SWNSystem()
		self.assertEqual(0, system.calculate_modifier(0))

	def test_calculate_modifier_invalid_raises(self) -> None:
		system = SWNSystem()
		for invalid in (-1, 2, 19, 20):
			with self.assertRaises(ValueError) as ctx:
				system.calculate_modifier(invalid)
			self.assertIn(f"Invalid attribute value: {invalid}", str(ctx.exception))

	def test_attribute_distribution_middle_more_common(self) -> None:
		system = SWNSystem()
		num = 2000
		counts = {i: 0 for i in range(19)}
		for _ in range(num):
			val = system.calculate_attribute()
			counts[val] += 1
		low = sum(counts[i] for i in range(3, 8))
		mid = sum(counts[i] for i in range(8, 14))
		high = sum(counts[i] for i in range(14, 19))
		self.assertGreater(mid, low)
		self.assertGreater(mid, high)
