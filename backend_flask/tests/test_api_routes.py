#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

import io
import json
import unittest
import os
import sys
from flask import Flask
# Ensure backend_flask root is on path
sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))
from app import create_app


class TestApiRoutes(unittest.TestCase):
	def setUp(self) -> None:
		self.app: Flask = create_app()
		self.app.config.update(SECRET_KEY="testing", TESTING=True)
		self.client = self.app.test_client()

	def test_new_character_endpoint(self) -> None:
		resp = self.client.get('/api/new-character')
		self.assertEqual(200, resp.status_code)
		self.assertEqual('application/json', resp.content_type)
		data = resp.get_json()
		self.assertIn('mName', data)

	def test_get_character_endpoint(self) -> None:
		# ensure session has a character
		self.client.get('/api/new-character')
		resp = self.client.get('/api/character')
		self.assertEqual(200, resp.status_code)
		self.assertEqual('application/json', resp.content_type)
		data = resp.get_json()
		self.assertIn('mName', data)

	def test_roll_attributes_endpoint(self) -> None:
		self.client.get('/api/new-character')
		resp = self.client.get('/api/roll-attributes')
		self.assertEqual(200, resp.status_code)
		data = resp.get_json()
		self.assertNotEqual(0, data['mStrength'])

	def test_change_attribute_endpoint(self) -> None:
		self.client.get('/api/new-character')
		resp = self.client.post('/api/change-attribute', json={"attribute": "strength"})
		self.assertEqual(200, resp.status_code)
		data = resp.get_json()
		self.assertEqual(14, data['mStrength'])

	def test_set_detail_endpoint(self) -> None:
		self.client.get('/api/new-character')
		resp = self.client.post('/api/set-detail', json={"detail": "name", "value": "John Doe"})
		self.assertEqual(200, resp.status_code)
		data = resp.get_json()
		self.assertEqual("John Doe", data['mName'])

	def test_upload_character_endpoint(self) -> None:
		payload = {"mName": "Uploaded Character"}
		file_bytes = io.BytesIO(json.dumps(payload).encode('utf-8'))
		data = {"file": (file_bytes, 'character.json')}
		resp = self.client.post('/api/upload-character', data=data, content_type='multipart/form-data')
		self.assertEqual(200, resp.status_code)
		self.assertEqual('application/json', resp.content_type)
		self.assertEqual("Uploaded Character", resp.get_json()['mName'])

	def test_download_character_endpoint(self) -> None:
		self.client.get('/api/new-character')
		resp = self.client.get('/api/download-character')
		self.assertEqual(200, resp.status_code)
		self.assertEqual('application/json', resp.content_type)
		self.assertIn('attachment; filename=character.json', resp.headers.get('Content-Disposition', ''))

	def test_change_attribute_error_handling(self) -> None:
		# missing attribute param
		self.client.get('/api/new-character')
		resp = self.client.post('/api/change-attribute', json={})
		self.assertEqual(400, resp.status_code)
		self.assertIn('error', resp.get_json())

	def test_set_detail_error_handling(self) -> None:
		self.client.get('/api/new-character')
		resp = self.client.post('/api/set-detail', json={"detail": "name"})
		self.assertEqual(400, resp.status_code)
		self.assertIn('error', resp.get_json())
