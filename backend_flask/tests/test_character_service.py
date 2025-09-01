#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import unittest
import os
import sys
from flask import Flask
# Ensure backend_flask root is on path
sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))
from app import create_app
from application.services.character_service import CharacterService
from application.models.character_store import CharacterStore
from swn_char.swn_char import SWNChar


class TestCharacterService(unittest.TestCase):
	def setUp(self) -> None:
		# reset store singleton to isolate tests
		CharacterStore._instance = None
		CharacterStore._characters = {}
		self.app: Flask = create_app()
		self.app.config.update(SECRET_KEY="testing", TESTING=True)
		self.ctx = self.app.app_context()
		self.ctx.push()
		# Push a request context so flask.session is available to service methods
		self.req_ctx = self.app.test_request_context('/')
		self.req_ctx.push()
		# set client
		self.client = self.app.test_client()

	def tearDown(self) -> None:
		# Pop request context first, then app context
		self.req_ctx.pop()
		self.ctx.pop()

	def _ensure_session(self) -> str:
		with self.client.session_transaction() as sess:
			sess['session_id'] = 'test-session-id'
		return 'test-session-id'

	def test_get_or_create_session_id(self) -> None:
		# First: creates
		with self.client as _:
			sid = CharacterService.get_or_create_session_id()
			self.assertTrue(isinstance(sid, str) and len(sid) > 0)
			# Second: returns same
			sid2 = CharacterService.get_or_create_session_id()
			self.assertEqual(sid, sid2)

	def test_create_new_character(self) -> None:
		with self.client as _:
			result = CharacterService.create_new_character()
			self.assertIn('mName', result)
			# Ensure stored
			sid = CharacterService.get_or_create_session_id()
			store = CharacterStore.get_instance()
			self.assertTrue(store.has_character(sid))

	def test_get_character_existing(self) -> None:
		with self.client as _:
			sid = CharacterService.get_or_create_session_id()
			store = CharacterStore.get_instance()
			store.store_character(sid, SWNChar())
			result = CharacterService.get_character()
			self.assertIn('mName', result)

	def test_get_character_non_existing(self) -> None:
		with self.client as _:
			result = CharacterService.get_character()
			self.assertIn('mName', result)

	def test_roll_attributes(self) -> None:
		with self.client as _:
			sid = CharacterService.get_or_create_session_id()
			store = CharacterStore.get_instance()
			char = SWNChar()
			store.store_character(sid, char)
			result = CharacterService.roll_attributes()
			self.assertNotEqual(0, result['mStrength'])

	def test_change_attribute_success(self) -> None:
		with self.client as _:
			sid = CharacterService.get_or_create_session_id()
			store = CharacterStore.get_instance()
			store.store_character(sid, SWNChar())
			result = CharacterService.change_attribute('strength')
			self.assertEqual(14, result['mStrength'])

	def test_change_attribute_no_character(self) -> None:
		with self.client as _:
			# No character stored but session exists
			CharacterService.get_or_create_session_id()
			with self.assertRaises(ValueError):
				CharacterService.change_attribute('strength')

	def test_set_detail_success(self) -> None:
		with self.client as _:
			sid = CharacterService.get_or_create_session_id()
			store = CharacterStore.get_instance()
			store.store_character(sid, SWNChar())
			result = CharacterService.set_detail('name', 'Test Character')
			self.assertEqual('Test Character', result['mName'])

	def test_set_detail_no_character(self) -> None:
		with self.client as _:
			CharacterService.get_or_create_session_id()
			with self.assertRaises(ValueError):
				CharacterService.set_detail('name', 'Test Character')

	def test_upload_character(self) -> None:
		with self.client as _:
			data = {
				"mName": "Uploaded Character",
				"mStrength": 10,
				"mDexterity": 12,
				"mConstitution": 14,
				"mIntelligence": 16,
				"mWisdom": 8,
				"mCharisma": 10,
				"mStrengthModifier": 0,
				"mDexterityModifier": 1,
				"mConstitutionModifier": 2,
				"mIntelligenceModifier": 3,
				"mWisdomModifier": -1,
				"mCharismaModifier": 0,
				"mChangedAttribute": "NONE",
				"mChangedAttributeOriginalValue": 0,
			}
			result = CharacterService.upload_character(data)
			self.assertEqual('Uploaded Character', result['mName'])
