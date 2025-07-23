#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Character data module for the Stars Without Number Character Generator.

This module defines the SWNCharData dataclass, which stores the data for a
character in the Stars Without Number role-playing game system.
"""

from dataclasses import dataclass

from swn_char.swn_system import AttributeEnum


@dataclass
class SWNCharData:
	"""
	Dataclass for storing Stars Without Number character data.
	
	This class stores all the data for a Stars Without Number character,
	including character details, attributes, modifiers, and tracking for
	changed attributes.
	
	Attributes:
		mName: The character's name.
		mStrength: The character's Strength attribute.
		mDexterity: The character's Dexterity attribute.
		mConstitution: The character's Constitution attribute.
		mIntelligence: The character's Intelligence attribute.
		mWisdom: The character's Wisdom attribute.
		mCharisma: The character's Charisma attribute.
		mStrengthModifier: The modifier for the Strength attribute.
		mDexterityModifier: The modifier for the Dexterity attribute.
		mConstitutionModifier: The modifier for the Constitution attribute.
		mIntelligenceModifier: The modifier for the Intelligence attribute.
		mWisdomModifier: The modifier for the Wisdom attribute.
		mCharismaModifier: The modifier for the Charisma attribute.
		mChangedAttribute: The attribute that has been changed to a special value.
		mChangedAttributeOriginalValue: The original value of the changed attribute.
	"""
	# ####
	# Details section
	# ####
	mName: str = "Default Name"

	# ####
	# Attributes section
	# ####
	# Class variable declarations for the six attributes
	mStrength: int = 0
	mDexterity: int = 0
	mConstitution: int = 0
	mIntelligence: int = 0
	mWisdom: int = 0
	mCharisma: int = 0

	# Class variable declarations for the modifiers
	mStrengthModifier: int = 0
	mDexterityModifier: int = 0
	mConstitutionModifier: int = 0
	mIntelligenceModifier: int = 0
	mWisdomModifier: int = 0
	mCharismaModifier: int = 0

	# class variable to track which attribute is set to 14
	mChangedAttribute: AttributeEnum = AttributeEnum.NONE
	# variable to store original value of attribute set to 14
	mChangedAttributeOriginalValue: int = 0
