# M07 Assessment: Field Agent API

### Goals
* Complete new features in the field-agent project. Use existing code as much as possible. It's important to understand what you have before you write new code.

### High Level Requirements
* Create full HTTP CRUD for security clearance.
* Create full HTTP CRUD for agent aliases.
* Implement global error handling.

### Set Up
1. Download Field Agent Project
2. Extract it.
3. Open `field-agent` with IntelliJ.
4. Build the project.

The project is a REST API that uses Spring Boot, Spring MVC, Spring JDBC, and Spring Testing. It's built on top of the field_agent schema.

### Features
Implement three new features in the numbered priority order.

1. Security Clearance
    * There is an existing `SecurityClearance` model, but no specific repository, service, or `@RestController`. Implement these classes to provide full HTTP access to security clearances.
    * API users should be able to:
        * Find all security clearances.
        * Find a security clearance by its identifier.
        * Add a security clearance. 
        * Update an existing security clearance.
        * Delete a security clearance. (This requires a strategy. It's probably not appropriate to delete agency_agent records that depend on a security clearance. Only allow deletion if a security clearance key isn't referenced.)
    * **Domain Rules**
        1. Security clearance name is required.
        2. Name cannot be duplicated.

2. Aliases
    * An agent has a one-to-many relationship with aliases. An alias is an assumed identity. It's a name and optional persona (description of the identity) under which the agent operates. Aliases aren't required. Some agents have many.
    * The `alias` table exists in the database, but there's no accompanying Java code. Implement the classes and methods required to support aliases in the REST API. Consider your options. Aliases are not independent. They are attached to an agent.
    * API users should be able to:
        * Fetch an individual agent with aliases attached.
        * Add an alias.
        * Update an alias.
        * Delete an alias. (No strategy required. An alias is never referenced elsewhere.)
    * **Domain Rules**
        1. Name is required.
        2. Persona is not required unless a name is duplicated. The persona differentiates between duplicate names.
    
3. Global Error Handling
    * The current code makes no attempt to manage data integrity issues. If we try to reference a record that doesn't exist or insert a duplicate key, we get a `RuntimeException`.
    * Use the `@ControllerAdvice` annotation to register an exception handler for all controllers. Catch and handle exceptions at two levels.
        1. Determine the most precise exception for data integrity failures and handle it with a specific data integrity message.
        2. For all other exceptions, create a general "sorry, not sorry" response that doesn't share exception details.
    
### Technical Requirements
* Do not change the database schema. Only Java changes are required.
* Use Spring to make your life easier. Specifically, use as much Spring Testing as possible to save steps.
* Test both data and domain layer components. The @MockBean isn't required for domain testing, but if you don't use it you must create a test double. Controller testing isn't strictly required, but try to test at least one HTTP endpoint with a mock web server. It might be interesting to trigger global exception handling.
* Tests must never run against the production database. They run against the test database.

### Approach
* Plan before you write code. In particular, map out the existing code and ensure that you understand it. If there's code that partially solves a problem, use it. You may not receive full credit if you reimplement something that already exists.
* Requirements are very high level. You must discuss with your instructor to clarify and refine. Don't make assumptions. Verify with your instructor. Ask questions.
* Work back-to-front or front-to-back. Test as you go. Don't jump around. If you work front-to-back, you'll need some sort of mocked service to validate your HTTP endpoints.
* Save HTTP requests in a `.http` file. Then execute them with REST Client to verify endpoints. Testing should get you most of the way, but it's still important to exercise the running application before you turn it in.

### Stretch Goals
* Implement full HTTP CRUD for `mission`.
* Implement full HTTP CRUD for the mission/agent many-to-many relationship.
* Implement a database logging strategy for global exceptions. (It's okay to alter database schema for this goal.) It's possible to disable detailed exception messages via configuration, so that's not the real value of global exception handling. The real value is the ability to log exceptions and track their behavior. 
* Create a database table, a repository, and possibly a service (maybe pass-through?) to log exceptions to the database.
