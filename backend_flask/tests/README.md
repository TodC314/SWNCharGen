# Testing Strategy for SWN Character Generator Backend (Flask / Python)

This document outlines the testing strategy for the SWN Character Generator Flask backend located under `backend_flask/tests`.

## Overview

The testing approach for this backend follows these principles:
- Unit tests for individual components (services, models)
- Integration-style tests for API routes using Flask's test client
- Isolate components where needed by resetting singletons and controlling session state
- Test both positive (success) and negative (failure) paths
- Clear docstrings and comments in test modules

## Test Structure

The tests are organized by component type:

### Application Tests

Located in `backend_flask/tests/test_api_routes.py`

These tests verify:
- The Flask application factory `create_app` produces a working app
- API endpoints return the expected responses and content types
- Basic end-to-end flows through the HTTP layer
- CharacterStore behavior across requests within a session

### Controller Tests

Located in `backend_flask/tests/test_api_routes.py` (Flask routes act as controllers)

These tests verify:
- All REST endpoints respond correctly
- Request parameters are properly processed
- Response status codes and bodies are as expected
- Error handling for invalid/missing input
- File upload and download behavior

### Service Tests

Located in `backend_flask/tests/test_character_service.py`

These tests verify:
- Service methods work correctly in isolation
- Session management via `flask.session` functions properly
- Character creation, retrieval, modification, and upload work as expected
- Error cases are handled appropriately
- Character data is persisted in the in-memory store for the session

### Model Tests

The model tests are split across multiple test classes/files:

#### SWNCharTest

Located in `backend_flask/tests/test_swn_char.py`

These tests verify:
- Model constructors and initial values
- Data conversion methods (to_dict/from_dict)
- Business logic within models (rolling, changing attributes)

#### AttributeEnumTest

Located conceptually alongside model tests (enum usage verified within tests)

These tests verify:
- Enum values and conversions used by the model and services

#### CharacterStoreTest

Located conceptually in store-related tests (covered by service tests)

These tests verify:
- Singleton pattern expectations
- Character storage and retrieval by session id

#### DetailEnumTest

Handled within route/service tests where details are set

These tests verify:
- Detail handling and validation via service methods

#### SWNCharDataTest

Covered by `test_swn_char.py`

These tests verify:
- Data structure integrity
- JSON-style serialization/deserialization via dicts

#### SWNSystemTest

If present, would validate system calculations (modifiers, ranges)

These tests verify:
- Attribute modifier calculation and system constants

## Testing Techniques

### unittest

We use Python's built-in `unittest` framework:
- `unittest.TestCase` for test cases
- `setUp`/`tearDown` for per-test lifecycle
- Standard assertions like `assertEqual`, `assertIn`, `assertRaises`

### Mocking

We avoid heavy mocking and instead use Flask contexts and an in-memory store. Where needed, Python techniques are used:
- Direct state control/reset of the `CharacterStore` singleton in `setUp`
- Context managers to manage Flask app and request contexts

### Flask test client

For controller/route tests, we use Flask's test client without starting a real server:
- `app.test_client()` for creating requests
- Validating status codes, headers, and JSON payloads

### Integration Testing

Integration-style tests are achieved via the Flask test client and real service/model interactions within the app context.

## Test Coverage

The tests cover:
- Application factory behavior and app context availability
- API endpoints and routing
- Service methods and session/business logic
- Model behavior and data conversion
- Error handling and edge cases
- Session management via cookies
- Character creation, modification, and persistence within a session
