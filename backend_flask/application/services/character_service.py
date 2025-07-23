#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Character service module for the Stars Without Number Character Generator.

This module provides services for creating, retrieving, and manipulating
character data. It acts as an intermediary between the API routes and the
character store, handling the business logic for character operations.
"""

from typing import Dict, Any
from flask import session
import uuid
from swn_char.swn_char import SWNChar
from swn_char.swn_system import AttributeEnum, DetailEnum
from application.models.character_store import CharacterStore

class CharacterService:
    """
    Service class for character operations.
    
    This class provides static methods for creating, retrieving, and manipulating
    character data. It uses the CharacterStore to persist character data between
    requests.
    """
    @staticmethod
    def get_or_create_session_id() -> str:
        """
        Get the current session ID or create a new one if it doesn't exist.
        
        This method ensures that each user has a unique session ID that can be
        used to associate them with their character data.
        
        Returns:
            str: The session ID.
        """
        if 'session_id' not in session:
            session['session_id'] = str(uuid.uuid4())
        return session['session_id']
        
    @staticmethod
    def upload_character(character_data: Dict[str, Any]) -> Dict[str, Any]:
        """
        Upload a character from JSON data.
        
        Args:
            character_data: A dictionary containing character data
            
        Returns:
            The uploaded character data as a dictionary
        """
        session_id = CharacterService.get_or_create_session_id()
        # Create a new character from the uploaded data
        character = SWNChar(character_data)
        # Store the character
        CharacterStore.get_instance().store_character(session_id, character)
        return character.to_dict()

    @staticmethod
    def create_new_character() -> Dict[str, Any]:
        """
        Create a new character with default values.
        
        This method creates a new character with default values and stores it
        in the character store associated with the current session.
        
        Returns:
            Dict[str, Any]: The newly created character data as a dictionary.
        """
        session_id = CharacterService.get_or_create_session_id()
        character = SWNChar()
        CharacterStore.get_instance().store_character(session_id, character)
        return character.to_dict()

    @staticmethod
    def get_character() -> Dict[str, Any]:
        """
        Get the character associated with the current session.
        
        This method retrieves the character associated with the current session.
        If no character exists, a new one is created.
        
        Returns:
            Dict[str, Any]: The character data as a dictionary.
        """
        session_id = CharacterService.get_or_create_session_id()
        store = CharacterStore.get_instance()
        
        if not store.has_character(session_id):
            character = SWNChar()
            store.store_character(session_id, character)
        else:
            character = store.get_character(session_id)
            
        return character.to_dict()

    @staticmethod
    def roll_attributes() -> Dict[str, Any]:
        """
        Roll attributes for the character associated with the current session.
        
        This method generates random values for all attributes of the character
        associated with the current session. If no character exists, a new one
        is created.
        
        Returns:
            Dict[str, Any]: The updated character data as a dictionary.
        """
        session_id = CharacterService.get_or_create_session_id()
        store = CharacterStore.get_instance()
        
        if not store.has_character(session_id):
            character = SWNChar()
            store.store_character(session_id, character)
        else:
            character = store.get_character(session_id)
            
        character.roll_attributes()
        return character.to_dict()

    @staticmethod
    def change_attribute(attribute_name: str) -> Dict[str, Any]:
        """
        Change a specific attribute of the character associated with the current session.
        
        This method changes the value of a specific attribute of the character
        associated with the current session.
        
        Args:
            attribute_name: The name of the attribute to change.
            
        Returns:
            Dict[str, Any]: The updated character data as a dictionary.
            
        Raises:
            ValueError: If no character is found for the current session.
        """
        session_id = session.get('session_id')
        store = CharacterStore.get_instance()
        
        if not session_id or not store.has_character(session_id):
            raise ValueError("No character found")
            
        character = store.get_character(session_id)
        attribute_enum = getattr(AttributeEnum, attribute_name.upper())
        character.change_one_attribute(attribute_enum)
        return character.to_dict()

    @staticmethod
    def set_detail(detail_name: str, detail_value: str) -> Dict[str, Any]:
        """
        Set a detail of the character associated with the current session.
        
        This method sets a specific detail (like name) of the character
        associated with the current session.
        
        Args:
            detail_name: The name of the detail to set.
            detail_value: The value to set for the detail.
            
        Returns:
            Dict[str, Any]: The updated character data as a dictionary.
            
        Raises:
            ValueError: If no character is found for the current session.
        """
        session_id = session.get('session_id')
        store = CharacterStore.get_instance()
        
        if not session_id or not store.has_character(session_id):
            raise ValueError("No character found")
            
        character = store.get_character(session_id)
        detail_enum = getattr(DetailEnum, detail_name.upper())
        character.set_detail(detail_enum, detail_value)
        return character.to_dict()
