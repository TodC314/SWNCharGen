#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
#
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Main entry point for the Stars Without Number Character Generator Flask application.

This module initializes the Flask application, configures it, enables CORS,
and registers the API routes.
"""

from flask import Flask
import flask
from flask_cors import CORS
from application.routes.character_routes import character_bp
from config.config import DevelopmentConfig

def create_app(config_object=DevelopmentConfig) -> flask.app:
    """
    Create and configure the Flask application.
    
    Args:
        config_object: Configuration class to use for the application.
                      Defaults to DevelopmentConfig.
    
    Returns:
        The configured Flask application.
    """
    my_app: flask.app = Flask(__name__)
    my_app.config.from_object(config_object)
    
    # Enable CORS
    CORS(my_app)
    
    # Register blueprints
    my_app.register_blueprint(character_bp, url_prefix='/api')
    
    return my_app

app:flask.app = create_app()

if __name__ == '__main__':
    app.run()
