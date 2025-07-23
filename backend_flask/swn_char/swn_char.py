#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Character class module for the Stars Without Number Character Generator.

This module defines the SWNChar class, which represents a character in the
Stars Without Number role-playing game system. It provides methods for creating,
manipulating, and serializing character data.
"""

from dataclasses import asdict
from enum import Enum
from typing import Dict, Any

from swn_char.swn_char_data import SWNCharData
from swn_char.swn_system import SWNSystem, AttributeEnum, DetailEnum


def enum_to_dict(the_obj: Any) -> Any:
	"""Convert enums to their values in nested structures"""
	if isinstance(the_obj, Enum):
		return the_obj.value
	elif isinstance(the_obj, list):
		return [enum_to_dict(item) for item in the_obj]
	elif isinstance(the_obj, dict):
		return {key: enum_to_dict(value) for key, value in the_obj.items()}
	return the_obj


def dict_to_enum(the_data: Dict[str, Any], the_instance: SWNCharData) -> None:
	"""Convert dictionary values to enums based on type hints"""
	if not hasattr(the_instance, '__annotations__'):
		return

	result: Any = {}
	for key, value in the_data.items():
		if key not in the_instance.__annotations__:
			continue

		field_type: type = the_instance.__annotations__[key]
		if hasattr(field_type, '__origin__'): # Handle generic types like List, Dict
			field_type = field_type.__origin__

		if isinstance(value, dict):
			# If the field is a nested dataclass
			current_value = getattr(the_instance, key, None)
			if current_value is not None:
				dict_to_enum(value, current_value)
			else:
				setattr(the_instance, key, dict_to_enum(value, field_type()))
		elif issubclass(field_type, Enum) and value is not None:
			# Convert to enum if the field is an enum type
			setattr(the_instance, key, field_type(value))
		else:
			# Set the value directly for non-enum fields
			setattr(the_instance, key, value)


class SWNChar:
	"""
	Class representing a character in the Stars Without Number system.
	
	This class encapsulates all the data and behavior of a Stars Without Number
	character, including attributes, modifiers, and character details. It provides
	methods for creating, manipulating, and serializing character data.
	"""

	# instance variable for data - flag as internal
	_m_data: SWNCharData
	# instance variable for system defaults - flag as internal
	_m_system: SWNSystem

	def __init__(self: 'SWNChar', char_data: Dict[str, Any] = None):
		"""
		Initialize a new SWNChar instance.
		
		Args:
			char_data: Optional dictionary containing character data to initialize from.
				If provided, the character will be initialized from this data.
				If not provided, a new character with default values will be created.
		"""
		self._m_data = SWNCharData()
		self._m_system = SWNSystem()
		
		if char_data:
			self.from_dict(char_data)

	def set_detail(self: 'SWNChar', the_detail: DetailEnum, the_value: str) -> None:
		"""
		Set a character detail.
		
		Args:
			the_detail: The detail to set (e.g., NAME).
			the_value: The value to set for the detail.
			
		Raises:
			ValueError: If the detail is not valid.
		"""
		if DetailEnum.NAME == the_detail:
			self._m_data.mName = the_value
		else:
			raise ValueError(f"Invalid detail: {the_detail}")

	def roll_attributes(self: 'SWNChar') -> None:
		"""
		Generate random values for all attributes and update their modifiers.
		"""
		# Roll 3d6 for each attribute
		self._m_data.mStrength = self._m_system.calculate_attribute()
		self._m_data.mDexterity = self._m_system.calculate_attribute()
		self._m_data.mConstitution = self._m_system.calculate_attribute()
		self._m_data.mIntelligence = self._m_system.calculate_attribute()
		self._m_data.mWisdom = self._m_system.calculate_attribute()
		self._m_data.mCharisma = self._m_system.calculate_attribute()

		# Update modifiers based on attribute values
		self._m_data.mStrengthModifier = self._m_system.calculate_modifier(self._m_data.mStrength)
		self._m_data.mDexterityModifier = self._m_system.calculate_modifier(self._m_data.mDexterity)
		self._m_data.mConstitutionModifier = self._m_system.calculate_modifier(self._m_data.mConstitution)
		self._m_data.mIntelligenceModifier = self._m_system.calculate_modifier(self._m_data.mIntelligence)
		self._m_data.mWisdomModifier = self._m_system.calculate_modifier(self._m_data.mWisdom)
		self._m_data.mCharismaModifier = self._m_system.calculate_modifier(self._m_data.mCharisma)

	def _set_attribute(self: 'SWNChar', the_attribute: AttributeEnum, the_value: int) -> int:
		"""
		Internal method to set an attribute value and update its modifier.
		
		Args:
			the_attribute: The attribute to set.
			the_value: The value to set for the attribute.
			
		Returns:
			int: The old value of the attribute before it was changed.
		"""
		old_value: int = -1
		if AttributeEnum.STRENGTH == the_attribute:
			old_value = self._m_data.mStrength
			self._m_data.mStrength = the_value
			self._m_data.mStrengthModifier = self._m_system.calculate_modifier(self._m_data.mStrength)
		elif AttributeEnum.DEXTERITY == the_attribute:
			old_value = self._m_data.mDexterity
			self._m_data.mDexterity = the_value
			self._m_data.mDexterityModifier = self._m_system.calculate_modifier(self._m_data.mDexterity)
		elif AttributeEnum.CONSTITUTION == the_attribute:
			old_value = self._m_data.mConstitution
			self._m_data.mConstitution = the_value
			self._m_data.mConstitutionModifier = self._m_system.calculate_modifier(self._m_data.mConstitution)
		elif AttributeEnum.INTELLIGENCE == the_attribute:
			old_value = self._m_data.mIntelligence
			self._m_data.mIntelligence = the_value
			self._m_data.mIntelligenceModifier = self._m_system.calculate_modifier(self._m_data.mIntelligence)
		elif AttributeEnum.WISDOM == the_attribute:
			old_value = self._m_data.mWisdom
			self._m_data.mWisdom = the_value
			self._m_data.mWisdomModifier = self._m_system.calculate_modifier(self._m_data.mWisdom)
		elif AttributeEnum.CHARISMA == the_attribute:
			old_value = self._m_data.mCharisma
			self._m_data.mCharisma = the_value
			self._m_data.mCharismaModifier = self._m_system.calculate_modifier(self._m_data.mCharisma)
		return old_value

	def change_one_attribute(self: 'SWNChar', the_attribute: AttributeEnum):
		"""
		Change one attribute to a special value (14) and restore any previously changed attribute.
		
		This method changes the specified attribute to a special value (14) and
		restores any previously changed attribute to its original value. It also
		updates the modifiers for the affected attributes.
		
		Args:
			the_attribute: The attribute to change.
		"""
		if self._m_data.mChangedAttribute != AttributeEnum.NONE:
			self._set_attribute(self._m_data.mChangedAttribute, self._m_data.mChangedAttributeOriginalValue)
		self._m_data.mChangedAttributeOriginalValue = self._set_attribute(the_attribute, self._m_system.get_changed_attribute_value())
		self._m_data.mChangedAttribute = the_attribute

	def to_dict(self: 'SWNChar') -> Dict[str, Any]:
		"""
		 Convert the SWNChar object to a JSON string with camelCase property names
        for use in the frontend.

        Returns:
            str: A JSON string representation of the SWNCharData object
		"""
		return enum_to_dict(asdict(self._m_data))

	def from_dict(self: 'SWNChar', the_data: Dict[str, Any]) -> None:
		"""
		Load character data from a dictionary.
		
		Args:
			the_data: A dictionary containing character data
		"""
		dict_to_enum(the_data, self._m_data)
