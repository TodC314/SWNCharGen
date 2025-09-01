#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import unittest
import uuid
from application.models.character_store import CharacterStore
from swn_char.swn_char import SWNChar


class TestCharacterStore(unittest.TestCase):
	def setUp(self) -> None:
		# Reset the singleton across tests by reassigning class attributes
		CharacterStore._instance = None
		CharacterStore._characters = {}

	def test_singleton_pattern(self) -> None:
		inst1 = CharacterStore.get_instance()
		inst2 = CharacterStore.get_instance()
		self.assertIs(inst1, inst2)
		self.assertIsNotNone(inst1)

	def test_store_and_retrieve_character(self) -> None:
		store = CharacterStore.get_instance()
		char = SWNChar()
		store.store_character("test-session-id", char)
		retrieved = store.get_character("test-session-id")
		self.assertIs(char, retrieved)

	def test_get_non_existent_character(self) -> None:
		store = CharacterStore.get_instance()
		non_id = str(uuid.uuid4())
		retrieved = store.get_character(non_id)
		self.assertIsNone(retrieved)

	def test_has_character(self) -> None:
		store = CharacterStore.get_instance()
		non_id = str(uuid.uuid4())
		self.assertFalse(store.has_character(non_id))
		char = SWNChar()
		store.store_character("session", char)
		self.assertTrue(store.has_character("session"))

	def test_store_multiple_characters(self) -> None:
		store = CharacterStore.get_instance()
		c1, c2, c3 = SWNChar(), SWNChar(), SWNChar()
		store.store_character("s1", c1)
		store.store_character("s2", c2)
		store.store_character("s3", c3)
		self.assertIs(c1, store.get_character("s1"))
		self.assertIs(c2, store.get_character("s2"))
		self.assertIs(c3, store.get_character("s3"))

	def test_overwrite_character(self) -> None:
		store = CharacterStore.get_instance()
		c1, c2 = SWNChar(), SWNChar()
		store.store_character("sid", c1)
		self.assertIs(c1, store.get_character("sid"))
		store.store_character("sid", c2)
		self.assertIs(c2, store.get_character("sid"))
		self.assertIsNot(c1, store.get_character("sid"))
