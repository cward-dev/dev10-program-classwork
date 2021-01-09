# Assessment: Sustainable Foraging

## Features
### "Working" Features
* All of the following features were reported as "completed" by the previous development team, but early testers of the application have been complaining that some things aren’t working correctly or might actually be missing. Review the functionality of each of the following features and fix any issues that you find:
  * Add an Item.
  * View Items.
  * View Foragers.
  * Add a Forage.
  * View Forages by date.
> **Hint**: One feature is missing entirely and an important validation is missing. Work with your project manager (instructor) to decide when fixes are required. <

### Incomplete Features
* The following features are not functional, though there may be existing application components that can be used to implement them.
  * Add a Forager.
  * Create a report that displays the kilograms of each Item collected on one day.
  * Create a report that displays the total value of each Category collected on one day.

## Requirements

### Base Requirements

* Items
  * Name is required.
  * Name cannot be duplicated.
  * Category is required.
  * Dollars/Kg is required.
  * Dollars/Kg must be between $0 (inedible, poisonous) and $7500.
  * Item ID is a system-generated unique sequential integer.

* Foragers
  * First name is required.
  * Last name is required.
  * State is required.
  * The combination of first name, last name, and state cannot be duplicated.
  * Forager ID is a system-generated GUID (globally unique identifier).

* Forages
  * Item is required and must exist.
  * Forager is required and must exist.
  * Date is required and must not be in the future.
  * Kilograms must be a positive number not more than 250.
  * The combination of date, Item, and Forager cannot be duplicated. (Can't forage the same item on the same day. It should be tracked as one Forage.)
  * Forage ID is a system-generated GUID (globally unique identifier).

### Technical Requirements

* File Format
  * Foragers and Items are stored in comma-delimited files with a header. You may not change the delimiter or alter the header. If possible, prevent commas from being added to data. An extra comma will prevent the repositories from reading the record.
  * Forage data is stored in what we call an "unfortunate decision". Each day's data is stored in a separate file with the naming convention: yyyy-MM-dd.csv.
  * Examples
    * Forages for 15-July-2020 are stored in 2020-07-15.csv.
    * Forages for 31-Oct-2019 are stored in 2019-10-31.csv.
    * Forages for 1-Jan-2019 are stored in 2019-01-01.csv.
  * Forage files are comma-delimited with a header. You may not change any aspect of the Forage data files. It is an inconvenient file format, but it's what the client wanted.

* Testing
  * All new features must be thoroughly tested. You are not responsible for creating testing for existing features unless you find a bug. If you find a bug, please create a test to confirm the expected behavior and prevent regressions.

## Approach
* Plan before you write code.
* We expect that you will have questions. In fact, you should have questions. Please direct them to your Project Manager (your Instructor). Take time to understand the code before you write new code. Run the application. Trace its execution. Draw pictures.

## Stretch Goals
* If time allows, the client would love to get started on Version 2 features.
* They include:
  * Update an existing Item.
  * Delete an Item. (Careful with this one. If an Item is deleted, one of two things should happen: 1. All Forages that include that Item should also be deleted. Forages without a valid Item are not allowed. 2. Make it a "soft" delete. Add an Item status and hide all Items with a deleted status from search results.)
  * Update a Forager.
  * Delete a Forager. (See Item deletion.)
  * Update a Forage.
  * Delete a Forage.
  * Figure out a way to view Forages by Forager. This is a non-trivial change.
  * Add reports: total value per Forager, Item kilograms collected per Forager

# Assessment Planning

### How will I review the provided code?
- [ ] Completed
  * I will first run the application to get a feel for how the program is intended to be used at the user level
  * I will look at the data files to see how the data is stored
    * foragers.csv
    * items.txt
    * forage data files (./data/forage_data/yyyy-MM-dd.csv)
  * I will look at the data layer to see how the data is read from and written to the data files
    * I will run the appropriate tests to see how the completed methods work
  * I will look at the domain layer to see how the data is handled in the service
    * I will run the appropriate tests to see how the completed methods work
  * I will look at the ui layer to see how the data is passed from the controller between the view and service classes
  * I will run the application again to see the user experience with more context of how the code works

### How will I ensure that the provided code meets the requirements for each implemented feature?
- [ ] Completed
  * I will reference the tests to ensure that thorough tests are for each method relevant to an implemented feature
    * If relevant tests are not present, I will create tests to ensure that the methods function as intended
  * If any methods do not function as intended, I will debug them and rerun the tests

### How will I document issues or bugs as I discover them?
- [ ] Completed
  * At the end of this README, I will log all issues and bugs in a TODO section
    * The TODO will include all bugs found (and what was changed to fix them), features to be implemented (and strategies), and other tasks that arise

### To implement the new features, what packages, classes, and methods will I write?
- [ ] Completed
  * Once I review the provided code, I will have a fuller understanding of what new packages, classes, and methods will be needed
    * I will include these in the TODO section of this README

### What unit tests do I need to write to ensure that my code behaves as expected?
- [ ] Completed
  * For all methods that I alter/implement, I will need to write both positive and negative unit tests to ensure that they behave as expected
    * I will create unit tests as I alter/implement methods, keeping track of tests made in the TODO section of this README

### Do I have to do any research?
- [ ] Completed
  * I am not aware of any research that will need completed, but if any arises I will include it in the TODO section of this README

### Are there any unknowns? What do I need to do to get clarity?
- [ ] Completed
  * Once I review the provided code I will have a fuller understanding of what I do not know
    * I will add any needed research/other further steps to the TODO section of this README

### What are my primary tasks?
- [ ] Completed
  - [ ] Review and understand the provided code
  - [ ] Add Spring dependency injection (either XML or annotations)
  - [ ] Identify and fix any existing bugs
  - [ ] Implement missing features
  - [ ] Create reports (will use streams as primary tool)

### How long do I estimate each of those tasks will take?
- [ ] Completed
  * This will be covered in the TODO-Workflow section of this README

### Are there any dependencies between tasks? What order do I need to complete the tasks in?
- [ ] Completed
  * I will need to fully inspect and debug the existing code before I am ready to add missing features
  * I will need to add missing features / complete the CRUD features before I move on to generating reports
  * Before I generate reports, I will need to plan their code structure
    * I believe I will need to...
      1. Create a model for a Report
      2. Create a separate UI class for report generation to separate from other code

# Basic Structure of Application

### Models
* Forager
  * Fields - String id, String firstName, String lastName, String state
  * Constructor - empty
* Item
  * Fields - int id, String name, Category category (enum), BigDecimal dollarPerKilogram
  * Constructor - empty | int id, String name, Category category, BigDecimal dollarPerKilogram
* Forage
  * Fields - String id, LocalDate date, Forager forager, Item item, double kilograms
  * Constructor - empty
  * Depends on both Forager and Item

### Features
* View Items
  * Get Category from view
  * Get List<Item> by Category from itemService
  * Send List<Item> to view for display
* View Forages By Date
  * Get LocalDate from view
  * Get List<Forage> by LocalDate from forageService
  * Send List<Forage> to view for display
* Add a Forage
  * Get Forager from view
  * Get Item from view
  * Get LocalDate --> TODO Bug LocalDate validation cannot be future date
  * Get double (kilograms collected)
  * Send to service - forageService.add(forage)
* Add a Forager (incomplete)
* Add an Item
* Report: 
* Report: 

# TODO

### Workflow
- [x] Review the provided code
  * Estimated Time: 2 hours
  * Actual Time: 2 hours
  * Goal: Achieve an understanding the applications existing features and how they are implemented in the code
- [x] Add Spring dependency injection
  * Estimated Time: 1.5 hours
  * Actual Time: 30 minutes
  * Goal: Convert all manual dependency injection to Spring dependency injection (either XML or annotations)
- [x] Debug provided code
  * Estimated Time: 3 hours
  * Actual Time: 2 hours
  * Goal: Ensure that all provided methods function as intended
    * Will also create thorough tests for all altered/new methods
- [ ] Implement missing features
  * Estimated Time: 2.5 hours
  * Actual Time:
  * Goal: Add missing features to ensure the application is fully CRUD-enabled
    * Will also create thorough tests for all new methods
- [ ] Tackle stretch goals
  * Estimated Time: Depends on actual time of other tasks
  * Actual Time: 
  * Goal: Get a head-start of Version 2 features for our overly ambitious client
  
### Bugs Found
- [x] Delimiters are not cleaned in data fields
  * Issue 
    * Writing strings to the files that contain the delimiter (,) will cause an error in reading data
  * Solution
    * Added `clean` and `restore` methods to each FileRepository
    * Called those methods for the appropriate fields in each `serialize` (`clean`) and `deserialize` (`restore`) method
- [x] LocalDate input validation in `ConsoleIO::readLocalDate` --> affects `View::makeForage` and `View::getForageDate`
  * Issue
    * Should not allow future dates in user input validation, this is not caught until the service layer (throws an error message rather than allowing user to enter a valid date)
  * Solution
    * Updated `ConsoleIO::readLocalDate` to check that input date !isAfter LocalDate.now()
    * Updated `ConsoleIO String INVALID_DATE` call to include current date for reference.
- [x] Duplicate `Forage` entries can be made
  * Issue
    * Should not allow a forage entry that matches another entry's date, Item, and Forager - these should be tracked as one Forage
  * Solution
    * Added overrides for `Forage::equals` and `Forage::hashCode` to check for same date, Item, and Forager
    * Added `ForageService::checkForDuplicate`
- [x] `View::chooseItem` does not allow user to select again if invalid item id is entered, does not catch until the service layer (throws an error message rather tahn allowing use to enter a valid date)
  * Issue
    * If a user enters an id for an item that is not listed, they are not given another chance to enter a valid value
  * Solution
    * added a `while (true)` loop to allow inputs until a valid one is entered

### Research
- [ ] TBD
  * Solution:

### Additional Tasks
- [x] Replace manual dependency injection with Spring annotation DI
  * Solution:
    * Added Spring dependency to `pom.xml` file
    * Used same wiring techniques as shown in the `memories` example
- [ ] Add   