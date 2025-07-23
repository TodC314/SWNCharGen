#  SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/
# 
#  Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing.
#  https://sine-nomine-publishing.myshopify.com/
#  Used in accordance with his Discord message of limiting content to the free version of the rules.
#  Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationship are the copyright of Kevin Crawford, Sine Nominee Publishing.

"""
Configuration module for the Stars Without Number Character Generator.

This module defines configuration classes for different environments
(development, production) that can be used to configure the Flask application.
"""

class Config:
    """
    Base configuration class.
    
    This class defines common configuration settings that are shared across
    all environments.
    
    Attributes:
        SECRET_KEY: Secret key for session encryption and CSRF protection.
        DEBUG: Flag to enable/disable debug mode.
    """
    # TODO: read SECRET_KEY from configuration file
    SECRET_KEY = 'your-secret-key-here'  # Change this in production
    DEBUG = False

class DevelopmentConfig(Config):
    """
    Development environment configuration.
    
    This class extends the base Config class and overrides settings
    specific to the development environment.
    
    Attributes:
        DEBUG: Flag to enable debug mode in development.
    """
    DEBUG = True

class ProductionConfig(Config):
    """
    Production environment configuration.
    
    This class extends the base Config class and can be used to define
    settings specific to the production environment.
    """
    # Add production-specific settings
    pass
