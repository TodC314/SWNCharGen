
#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Character storage module for the Stars Without Number Character Generator.

This module provides a singleton store for character data, allowing characters
to be stored and retrieved by session ID.
"""

from typing import Dict
from swn_char.swn_char import SWNChar

class CharacterStore:
    """
    Singleton store for character data.
    
    This class provides a central repository for storing and retrieving
    character data by session ID. It ensures that only one instance of the
    store exists throughout the application.
    """
    _instance = None
    _characters: Dict[str, SWNChar] = {}

    @classmethod
    def get_instance(cls):
        """
        Get the singleton instance of the CharacterStore.
        
        Returns:
            CharacterStore: The singleton instance of the CharacterStore.
        """
        if cls._instance is None:
            cls._instance = cls()
        return cls._instance

    def get_character(self, session_id: str) -> SWNChar:
        """
        Get a character by session ID.
        
        Args:
            session_id: The session ID associated with the character.
            
        Returns:
            SWNChar: The character associated with the session ID, or None if not found.
        """
        return self._characters.get(session_id)

    def store_character(self, session_id: str, character: SWNChar) -> None:
        """
        Store a character by session ID.
        
        Args:
            session_id: The session ID to associate with the character.
            character: The character to store.
        """
        self._characters[session_id] = character

    def has_character(self, session_id: str) -> bool:
        """
        Check if a character exists for the given session ID.
        
        Args:
            session_id: The session ID to check.
            
        Returns:
            bool: True if a character exists for the session ID, False otherwise.
        """
        return session_id in self._characters
