# Testing Strategy for SWN Character Generator Backend (Spring)

This document outlines the testing strategy for the SWN Character Generator backend Spring Boot application.

## Overview

The testing approach for this application follows these principles:
- Unit tests for individual components (services, models)
- Integration tests for the application as a whole
- Mock external dependencies to isolate the component being tested
- Test both positive (success) and negative (failure) testing
- Comprehensive JavaDoc documentation for all test classes and methods

## Test Structure

The tests are organized by component type:

### Application Tests

Located in `org.kuroneko.swn_char_gen.backend_spring.BackendSpringApplicationTests`

These tests verify:
- The Spring application context loads correctly
- Required beans are properly autowired
- Basic application functionality works end-to-end
- API endpoints return the expected responses
- CharacterStore singleton behavior

### Controller Tests

Located in `org.kuroneko.swn_char_gen.backend_spring.controllers.ApiControllerTest`

These tests verify:
- All REST endpoints respond correctly
- Request parameters are properly processed
- Response status codes and bodies are as expected
- Error handling works correctly
- File upload and download functionality

### Service Tests

Located in `org.kuroneko.swn_char_gen.backend_spring.services.CharacterServiceTest`

These tests verify:
- Service methods work correctly in isolation
- Session management functions properly
- Character creation, retrieval, and modification work as expected
- Error cases are handled appropriately
- Character data persistence

### Model Tests

The model tests are split across multiple test classes:

#### SWNCharTest

Located in `org.kuroneko.swn_char_gen.backend_spring.models.SWNCharTest`

These tests verify:
- Model constructors work correctly
- Data conversion methods function properly
- Business logic within models operates as expected
- Attribute rolling and modification

#### AttributeEnumTest

Located in `org.kuroneko.swn_char_gen.backend_spring.models.AttributeEnumTest`

These tests verify:
- Enum values are correctly defined
- Conversion between string and enum values works properly
- Invalid attribute handling

#### CharacterStoreTest

Located in `org.kuroneko.swn_char_gen.backend_spring.models.CharacterStoreTest`

These tests verify:
- Singleton pattern implementation
- Character storage and retrieval
- Session management

#### DetailEnumTest

Located in `org.kuroneko.swn_char_gen.backend_spring.models.DetailEnumTest`

These tests verify:
- Enum values are correctly defined
- Conversion between string and enum values works properly
- Invalid detail handling

#### SWNCharDataTest

Located in `org.kuroneko.swn_char_gen.backend_spring.models.SWNCharDataTest`

These tests verify:
- Data structure integrity
- JSON serialization and deserialization
- Data validation

#### SWNSystemTest

Located in `org.kuroneko.swn_char_gen.backend_spring.models.SWNSystemTest`

These tests verify:
- Game system rules implementation
- Attribute modifier calculation
- System constants and values

## Testing Techniques

### JUnit 5

We use JUnit 5 as our testing framework:
- `@Test` for marking test methods
- `@DisplayName` for providing descriptive test names
- `@BeforeEach` for setup code before each test
- `@ExtendWith` for integrating with Mockito

### Mocking

We use Mockito for mocking dependencies:
- `@Mock` for creating mock objects
- `@InjectMocks` for injecting mocks into the class under test
- `Mockito.spy()` for partial mocking
- `MockedStatic` with try-with-resources for mocking static methods
- `when()` and `doReturn()` for stubbing method calls
- `verify()` for verifying method calls
- `assertThrows()` for testing exceptions

### MockMvc

For controller tests, we use Spring's MockMvc to test endpoints without starting a full server:
- `MockMvcRequestBuilders` for creating requests
- `MockMvcResultMatchers` for verifying responses
- `standaloneSetup()` for testing controllers in isolation
- `webAppContextSetup()` for testing with the Spring application context

### Integration Testing

For integration tests, we use:
- `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` to start the server
- MockMvc with `webAppContextSetup()` for testing with the application context
- Mocked dependencies to control test behavior

## Test Coverage

The tests cover:
- Application context loading
- API endpoints and controllers
- Service methods and business logic
- Model behavior and data conversion
- Error handling and edge cases
- Session management
- Character creation, modification, and persistence
