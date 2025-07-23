#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
System rules module for the Stars Without Number Character Generator.

This module defines classes and constants related to the Stars Without Number
system rules, including enums for attributes and details, attribute modifiers,
and methods for calculating attributes and modifiers.
"""

import random
from enum import Enum
from typing import Dict


class DetailEnum(Enum):
	"""
	Enum for character details.
	
	This class defines constants for the different types of character details
	that can be set or retrieved.
	
	Attributes:
		NAME: The character's name.
		NONE: Represents no detail.
	"""
	NAME = "NAME"
	NONE = "NONE"


class AttributeEnum(Enum):
	"""
	Enum for character attributes.
	
	This class defines constants for the six attributes in the Stars Without
	Number system.
	
	Attributes:
		STRENGTH: The Strength attribute.
		DEXTERITY: The Dexterity attribute.
		CONSTITUTION: The Constitution attribute.
		INTELLIGENCE: The Intelligence attribute.
		WISDOM: The Wisdom attribute.
		CHARISMA: The Charisma attribute.
		NONE: Represents no attribute.
	"""
	STRENGTH = "STRENGTH"
	DEXTERITY = "DEXTERITY"
	CONSTITUTION = "CONSTITUTION"
	INTELLIGENCE = "INTELLIGENCE"
	WISDOM = "WISDOM"
	CHARISMA = "CHARISMA"
	NONE = "NONE"


# Dictionary mapping attribute values to modifiers
attribute_modifiers: Dict[int, int] = {
	3: -2,
	4: -1, 5: -1, 6: -1, 7: -1,
	8: 0, 9: 0, 10: 0, 11: 0, 12: 0, 13: 0,
	14: 1, 15: 1, 16: 1, 17: 1,
	18: 2
}


class SWNSystem:
	"""
	Class for Stars Without Number system rules.
	
	This class provides methods for calculating attributes and modifiers
	according to the Stars Without Number system rules.
	"""
	def __init__(self: 'SWNSystem'):
		"""
		Initialize a new SWNSystem instance.
		"""
		pass

	@staticmethod
	def calculate_attribute() -> int:
		"""
		Calculate a random attribute value by rolling 3d6.
		
		Returns:
			int: The sum of three six-sided dice rolls (3-18).
		"""
		return sum(random.randint(1, 6) for _ in range(3))

	@staticmethod
	def calculate_modifier(the_attribute_value: int) -> int:
		"""
		Calculate the modifier for an attribute value.
		
		Args:
			the_attribute_value: The attribute value to calculate the modifier for.
			
		Returns:
			int: The modifier for the attribute value.
			
		Raises:
			ValueError: If the attribute value is invalid.
		"""
		if the_attribute_value in attribute_modifiers:
			return attribute_modifiers[the_attribute_value]
		elif 0 == the_attribute_value:
			# unset attribute value
			return 0
		else:
			raise ValueError(f"Invalid attribute value: {the_attribute_value}")

	@staticmethod
	def get_changed_attribute_value() -> int:
		"""
		Get the value to use for a changed attribute.
		
		Returns:
			int: The value to use for a changed attribute (14).
		"""
		return 14
