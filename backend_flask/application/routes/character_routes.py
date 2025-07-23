#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
API routes for character operations in the Stars Without Number Character Generator.

This module defines the API endpoints for creating, retrieving, and manipulating
character data. It uses the CharacterService to handle the business logic for
these operations.
"""

from flask import Blueprint, jsonify, request, Response
import json
from application.services.character_service import CharacterService

character_bp = Blueprint('character', __name__)

@character_bp.route('/new-character', methods=['GET'])
def new_character():
    """
    Create a new character.
    
    This endpoint creates a new character with default values and returns it.
    
    Returns:
        JSON: The newly created character data.
        
    Raises:
        400: If an error occurs during character creation.
    """
    try:
        return jsonify(CharacterService.create_new_character()), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@character_bp.route('/character', methods=['GET'])
def get_character():
    """
    Get the current character.
    
    This endpoint retrieves the character associated with the current session.
    If no character exists, a new one is created.
    
    Returns:
        JSON: The character data.
        
    Raises:
        400: If an error occurs during character retrieval.
    """
    try:
        return jsonify(CharacterService.get_character()), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@character_bp.route('/roll-attributes', methods=['GET'])
def roll_attributes():
    """
    Roll attributes for the current character.
    
    This endpoint generates random values for all attributes of the character
    associated with the current session.
    
    Returns:
        JSON: The updated character data.
        
    Raises:
        400: If an error occurs during attribute rolling.
    """
    try:
        return jsonify(CharacterService.roll_attributes()), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@character_bp.route('/change-attribute', methods=['POST'])
def change_attribute():
    """
    Change a specific attribute of the current character.
    
    This endpoint changes the value of a specific attribute of the character
    associated with the current session.
    
    Request Body:
        attribute (str): The name of the attribute to change.
    
    Returns:
        JSON: The updated character data.
        
    Raises:
        400: If the attribute parameter is missing or an error occurs.
    """
    try:
        attribute = request.get_json().get('attribute')
        if not attribute:
            return jsonify({"error": "Attribute parameter is required"}), 400
        return jsonify(CharacterService.change_attribute(attribute)), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@character_bp.route('/set-detail', methods=['POST'])
def set_detail():
    """
    Set a detail of the current character.
    
    This endpoint sets a specific detail (like name) of the character
    associated with the current session.
    
    Request Body:
        detail (str): The name of the detail to set.
        value (str): The value to set for the detail.
    
    Returns:
        JSON: The updated character data.
        
    Raises:
        400: If detail or value parameters are missing or an error occurs.
    """
    try:
        detail = request.get_json().get('detail')
        value = request.get_json().get('value')
        if not detail or not value:
            return jsonify({"error": "Detail and value parameters are required"}), 400
        return jsonify(CharacterService.set_detail(detail, value)), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@character_bp.route('/upload-character', methods=['POST'])
def upload_character():
    """
    Upload a character from a JSON file.
    
    This endpoint allows uploading a character from a JSON file. The file
    must be a valid JSON file containing character data.
    
    Request:
        file: A JSON file containing character data.
    
    Returns:
        JSON: The uploaded character data.
        
    Raises:
        400: If the file is missing, empty, not a JSON file, has invalid JSON format,
             or an error occurs during upload.
    """
    try:
        # Check if the request has a file
        if 'file' not in request.files:
            return jsonify({"error": "No file part"}), 400
            
        file = request.files['file']
        
        # Check if the file is empty
        if file.filename == '':
            return jsonify({"error": "No selected file"}), 400
            
        # Check if the file is a JSON file
        if not file.filename.endswith('.json'):
            return jsonify({"error": "File must be a JSON file"}), 400
            
        # Read the file and parse the JSON
        try:
            character_data = json.loads(file.read().decode('utf-8'))
        except json.JSONDecodeError:
            return jsonify({"error": "Invalid JSON format"}), 400
            
        # Upload the character
        return jsonify(CharacterService.upload_character(character_data)), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@character_bp.route('/download-character', methods=['GET'])
def download_character():
    """
    Download the current character as a JSON file.
    
    This endpoint retrieves the character associated with the current session
    and returns it as a downloadable JSON file.
    
    Returns:
        Response: A response with the character data as a downloadable JSON file.
        
    Raises:
        400: If an error occurs during character retrieval or download.
    """
    try:
        # Get character data
        character_data = CharacterService.get_character()
        
        # Pretty print the JSON
        pretty_json = json.dumps(character_data, indent=4, sort_keys=True)
        
        # Create response with appropriate headers for file download
        response = Response(
            pretty_json,
            mimetype='application/json',
            headers={
                'Content-Disposition': 'attachment; filename=character.json'
            }
        )
        return response
    except Exception as e:
        return jsonify({"error": str(e)}), 400
