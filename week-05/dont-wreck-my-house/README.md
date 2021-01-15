# Assessment: Don't Wreck My House Overview

## Glossary
* **Guest**
  * A customer. Someone who wants to book a place to stay. Guest data is provided via a zip download.
* **Host**
  * The accommodation provider. Someone who has a property to rent per night. Host data is provided.
* **Location**
  * A rental property. In Don't Wreck My House, Location and Host are combined. The application enforces a limit on one Location per Host, so we can think of a Host and Location as a single thing.
* **Reservation**
  * One or more days where a Guest has exclusive access to a Location (or Host). Reservation data is provided.
* **Administrator**
  * The application user. Guests and Hosts don't book their own Reservations. The Administrator does it for them.

## Requirements

### High Level Requirements
The application user is an accommodation administrator. They pair guests to hosts to make reservations. 
* The administrator may view existing reservations for a host.
* The administrator may create a reservation for a guest with a host.
* The administrator may edit existing reservations.
* The administrator may cancel a future reservation.
    
### Requirements
There are four required scenarios.

#### View Reservations for Host
Display all reservations for a host.
* The user may enter a value that uniquely identifies a host, or they can search for a host and pick one out of a list.
  * If the host is not found, display a message.
  * If the host has no reservations, display a message.
  * Show all reservations for that host.
  * Show useful information for each reservation: the guest, dates, totals, etc.
  * Sort reservations in a meaningful way.

#### Make a Reservation
Books accommodations for a guest at a host.
* The user may enter a value that uniquely identifies a guest, or they can search for a guest and pick one out of a list.
* The user may enter a value that uniquely identifies a host, or they can search for a host and pick one out of a list.
* Show all future reservations for that host, so the administrator can choose available dates.
* Enter a start and end date for the reservation.
* Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday or a weekend. If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
* On confirmation, save the reservation.

Validation
* Guest, host, and start and end dates are required.
* The guest and host must already exist in the "database". Guests and hosts cannot be created.
* The start date must come before the end date.
* The reservation may never overlap existing reservation dates.
* The start date must be in the future.

#### Edit a Reservation
Edits an existing reservation.
* Find a reservation.
* Start and end date can be edited. No other data can be edited.
* Recalculate the total, display a summary, and ask the user to confirm.

Validation
* Guest, host, and start and end dates are required.
* The guest and host must already exist in the "database". Guests and hosts cannot be created.
* The start date must come before the end date.
* The reservation may never overlap existing reservation dates.

#### Cancel a Reservation
Cancel a future reservation.
* Find a reservation.
* Only future reservations are shown.
* On success, display a message.
Validation
* You cannot cancel a reservation that's in the past.

### Technical Requirements
* Must be a Maven project.
* Spring dependency injection configured with either XML or annotations.
* All financial math must use `BigDecimal`.
* Dates must be `LocalDate`, never strings.
* All file data must be represented in models in the application.
* Reservation identifiers are unique per host, not unique across the entire application. Effectively, the combination of a reservation identifier and a host identifier is required to uniquely identify a reservation.

#### File Format
* The data file format is bad, but it's required. You may not change the file formats or the file delimiters. You must use the files provided. Guests are stored in their own comma-delimited file, `guests.csv`, with a header row. Hosts are stored in their own comma-delimited file, `hosts.csv`, with a header row.
* Reservations are stored across many files, one for each host. A host reservation file name has the format: `{host-identifier}.csv`.

#### Examples
* Reservations for host c6567347-6c57-4658-a2c7-50040eeeb80f are stored in `c6567347-6c57-4658-a2c7-50040eeeb80f.csv`
* Reservations for host 54508cfa-4f67-4de8-9437-6f27d65b0af0 are stored in `54508cfa-4f67-4de8-9437-6f27d65b0af0.csv`
* Reservations for host 6a3ef437-289c-40a9-b88a-dd70fad3fdbc are stored in `6a3ef437-289c-40a9-b88a-dd70fad3fdbc.csv`

## Testing
* All data components must be thoroughly tested. Tests must always run successfully regardless of the data starting point, so it's important to establish known good state. Never test with "production" data.
* All domain components must be thoroughly tested using test doubles. Do not use file repositories for domain testing.
* User interface testing is not required.

## Approach
* Plan before you write code. This is a large project. It will be difficult to achieve success without a plan.
* Ask questions. The specification isn't perfect. You must ask questions to clarify. Don't make assumptions when things aren't clear.
* Test as you go. If you wait to test until the end, three things happen:
  1. Your tests aren't as good.
  2. The last bit of development becomes a slog. No one loves 100% testing. Spread the testing out over several days.
  3. You gain false confidence because you don't see domain failures that are prevented in the UI. Remember, if we throw away the UI, the domain should still prevent all invalid transactions.
  
## Deliverables
* A schedule of concrete tasks. Tasks should contain time estimates and should never be more than 4 hours.
* Class diagram (informal is okay)
* Sequence diagrams or flow charts (optional, but encouraged)
* A Java Maven project that contains everything needed to run without error
* Test suite

## Stretch Goals
* Make reservation identifiers unique across the entire application.
* Allow the user to create, edit, and delete guests.
* Allow the user to create, edit, and delete hosts.
* Display reservations for a guest.
* Display all reservations for a state, city, or zip code.
* Implement another set of repositories that store data in JSON format. In this approach, you don't want multiple files for reservations. Consider how you might migrate existing data to the JSON format safely.

# Project Plan

## Expected Workflow
- [x] Day 1 (Monday): Planning and prepare, begin setting up models
- [x] Day 2 (Tuesday): Finish models, begin data layer development
- [x] Day 3 (Wednesday): Progress presentation, finish data layer, begin domain layer development
- [ ] Day 4 (Thursday): Finish domain layer, go through testing and ensure that tests are comprehensive
- [ ] Day 5 (Friday): Progress presentation, begin ui layer development
- [ ] Day 6 (Saturday): Finish ui layer, let the bug hunt begin (don't forget targeted tests!)
- [ ] Day 7 (Sunday): Polish

## Tasks

// Note: My estimated times seem very optimistic. We will see how they go (and try to give ourselves plenty of time to play catch up as we go)

### Total Time to Complete
  * Estimated Time: 28 hours (before stretch goals)
  * Actual Time:

### Models
- [x] Create `State` enumerator
  * Values - one for each of the 50 states, plus Washington DC
  * Fields
    * `String name`
    * `String abbreviation`
  * Methods
    * `getters`
    * `public static getStateFromAbbreviation(String abbreviation)`
    * `public static getStateFromName(String name)`
  * Time to Complete
    * Estimated Time: 30 minutes
    * Actual Time: 30 minutes
  

- [x] Create `Guest` model
  * Fields
    * `int id`
    * `String firstName`
    * `String lastName`
    * `String email`
    * `String phone`
    * `State state`
  * Methods
    * `getters`
    * `setters`
    * `@Override equals / hashCode`
  * Time to Complete
    * Estimated Time: 1 hour
    * Actual Time: 15 minutes
  

- [x] Create `Host` model
  * Fields
    * `String id`
    * `String lastName`
    * `String email`
    * `String phone`
    * `String address`
    * `String city`
    * `State state`
    * `int postalCode`
    * `BigDecimal standardRate`
    * `BigDecimal weekendRate`
  * Methods
    * `getters`
    * `setters`
    * `@Override equals / hashCode`
  * Time to Complete
    * Estimated Time: 1 hour
    * Actual Time: 5 minutes
  

- [x] Create `Reservation` model
  * Fields
    * `String id`
    * `LocalDate startDate`
    * `LocalDate endDate`
    * `Host host`
    * `Guest guest`
    * `BigDecimal total`
  * Methods
    * `getters`
    * `setters`
    * `@Override equals / hashCode`
  * Time to Complete
    * Estimated Time: 1 hour // Wow, **really** overestimated the models
    * Actual Time: 5 minutes

### Data Layer
- [x] Create `DataException` class (extends `Exception`) --- provides us with a general exception in accessing/using data files, so that we may display a critical error to the user should we encounter it
  * Time to Complete
    * Estimated Time: 5 minutes
    * Actual Time: 2 minutes 
  

- [x] Create `GuestRepository`, `HostRepository`, and `ReservationRepository` interfaces
  * Time to Complete
    * Estimated Time: 45 minutes
    * Actual Time: 30 minutes
  

- [x] Create `GuestFileRepository` class and tests (implements GuestRepository)
    * Fields
      * `private static final String HEADER = "guest_id,first_name,last_name,email,phone,state"`
      * `private final String filePath` --- constructor arg
      * `private final String DELIMITER = ","`
      * `private final String DELIMITER_REPLACEMENT = "@@@"`
  * Methods
    * `findAll`
    * `findById`
    * `findByEmail`
    * `add`
    * `update`
    * `delete`
    * Private helpers: 
      * `writeAll`
      * `serialize`
      * `deserialize`
      * `clean`
      * `restore`
  * Time to Complete
    * Estimated Time: 2 hours
    * Actual Time: 1.5 hours
  

- [x] Create `HostFileRepository` class and tests (implements HostRepository)
  * Fields
    * `private static final String HEADER = "guest_id,first_name,last_name,email,phone,state"`
    * `private final String filePath` --- constructor arg
    * `private final String DELIMITER = ","`
    * `private final String DELIMITER_REPLACEMENT = "@@@"`
  * Methods
    * `findAll`
    * `findById`
    * `findByEmail`
    * `add`
    * `update`
    * `delete`
    * Private helpers:
      * `writeAll`
      * `serialize`
      * `deserialize`
      * `clean`
      * `restore`
  * Time to Complete
    * Estimated Time: 2 hours
    * Actual Time: 1 hour
  

- [x] Create `ReservationFileRepository` class and tests (implements ReservationRepository)
  * Dependencies: `GuestRepository`, `HostRepository` --- attaches the Guest and Host for the reservation based on Id
  * Fields
    * `private static final String HEADER = "id,start_date,end_date,guest_id,total"`
    * `private final String directory` --- constructor arg
    * `private final String DELIMITER = ","`
    * `private final String DELIMITER_REPLACEMENT = "@@@"`
    * `private final GuestRepository guestRepository` --- constructor arg
    * `private final HostRepository hostRepository` --- constructor arg
  * Methods
    * `findByHost`
    * `add`
    * `update`
    * `delete`
    * Private helpers:
      * `writeAll`
      * `serialize`
      * `deserialize`
      * `clean`
      * `restore`
  * Time to Complete
    * Estimated Time: 4 hours
    * Actual Time: 1.5 hours
  
### Domain Layer
- [x] Create `GuestService` class and tests
  * Dependencies: `GuestFileRepository` --- acts as the gatekeeper for all requests to access GuestFileRepository, validates data
  * Fields
    * `private final GuestRepository guestRepository` --- constructor arg
  * Methods
    * `findAll`
    * `findById`
    * `findByEmail`
    * `add`
    * `update`
    * `delete`
    * Private helpers:
      * `validate`
      * `checkForDuplicate`
  * Time to Complete
    * Estimated Time: 1.5 hours
    * Actual Time: 2 hours


- [x] Create `HostService` class and tests
  * Dependencies: `HostFileRepository` --- acts as the gatekeeper for all requests to access HostFileRepository, validates data
  * Fields
    * `private final HostRepository hostRepository` --- constructor arg
  * Methods
    * `findAll`
    * `findById`
    * `findByEmail`
    * `add`
    * `update`
    * `delete`
    * Private helpers:
      * `validate`
      * `checkForDuplicate`
  * Time to Complete
    * Estimated Time: 1.5 hours
    * Actual Time: 1.5 hours


- [x] Create `ReservationService` class and tests
  * Dependencies: `ReservationRepository`, `GuestRepository`, `HostRepository`  --- acts as the gatekeeper for all requests to access ReservationFileRepository, validates data
  * Fields
    * `private final ReservationRepository reservationRepository` --- constructor arg
    * `private final GuestRepository guestRepository` --- constructor arg
    * `private final HostRepository hostRepository` --- constructor arg
    * `findByHost`
    * `findById` --- will take both Host host and int id as arguments
    * `add`
    * `update`
    * `delete`
  * Methods
    * `findAll` --- will implement towards the end if time allows (for stretch goals)
    * `findById` --- will implement towards the end if time allows (for stretch goals)
    * `findByHost`
    * `add`
    * `update`
    * `delete`
    * Private helpers:
      * `validate`
      * `checkForOverlap`
  * Time to Complete
    * Estimated Time: 3 hours
    * Actual Time: 5 hours

  
- [x] Create `Result<T>` class (extends `Response`) --- allows us to create instances of Result with different types
  * Fields
    * `private T payload`
    * `getter`
    * `setter`
    * `private ArrayList<String> messages = new ArrayList<>()`
    * `public boolean isSuccess` --- `(return messages.size() == 0)`
    * `public List<String> getErrorMessages` --- `(return new ArrayList<>(messages))`
    * `public void addErrorMessage(String message)` --- `(messages.add(message))`
  * Time to Complete
    * Estimated Time: 0.75 hours
    * Actual Time: 10 minutes

### UI Layer
- [x] Create `Controller` class
  * Dependencies: `ControllerGuests`, `ControllerHosts`, `ControllerReservations`, `View` --- passes information between the view and domain layer
  * Fields
    * `private final ControllerGuests controllerGuests` --- constructor arg
    * `private final ControllerHosts controllerHosts` --- constructor arg
    * `private final ControllerReservations controllerReservations` --- constructor arg
    * `private final View view` --- constructor arg
  * Methods
    * `run` --- the application will be run from this method 
    * Private methods:
      * `runMainMenu` --- will run the main menu to select between submenus until EXIT is selected
  * Time to Complete
    * Estimated Time: 2 hours
    * Actual Time: 45 minutes

- [x] Create `ControllerGuests` class
  * Dependencies: `GuestService`, `HostService`, `ReservationService`, `View` --- passes information between the view and domain layer
  * Fields
    * `private final GuestService guestService` --- constructor arg
    * `private final HostService guestService` --- constructor arg
    * `private final ReservationService reservationService` --- constructor arg
    * `private final View view` --- constructor arg
  * Methods
    * `run` --- the application will be run from this method
    * Private methods:
      * `runAppLoop` --- will run methods based on menu selections in a loop until EXIT is selected
      * `viewReservationsByHost`
      * `makeReservation`
      * `editReservation`
      * `cancelReservation`
      * // If time allows, will add more viewing, creating, updating, and deleting methods for Guest and Host objects
      * Private helpers:
        * `getGuest`
        * `getHost`
        * `getReservation`
  * Time to Complete
    * Estimated Time: 2 hours
    * Actual Time: 6 hours  

- [x] Create `ConsoleIO` class --- provides us with methods for displaying and getting information using the console
  * Fields
    * `private static final String INVALID_(CASE)` --- will give [Invalid] message for each type of invalid input
    * `private final Scanner scanner`
    * `private DateTimeFormatter formatter` --- "MM/dd/yyyy"
  * Methods
    * `print`
    * `println`
    * `printf`
    * `readString`
    * `readRequiredString`
    * `readInt`
    * `readInt` --- Overloaded, with min and max parameters
    * `readBoolean`
    * `readState`
    * `readLocalDate`
    * `readBigDecimal`
  * Time to Complete
    * Estimated Time: 2 hours
    * Actual Time: 2 hours

- [x] Create `MainMenuOption` enumerator --- provides discrete values for the main menu options
  * Values
    * `EXIT`
    * `RESERVATIONS`
    * `GUESTS`
    * `HOSTS`
  * Fields
    * `private int value` --- constructor arg
    * `private String message` --- constructor arg
    * `private boolean hidden` --- constructor arg
  * Methods
    * `public static MainMenuOption fromValue(int value)` --- (get MainMenuOption.Value from int value)
    * `getters`
  * Time to Complete
    * Estimated Time: 1 hours
    * Actual Time: 1 hour

- [x] Create `ReservationMenuOption` enumerator --- provides discrete values for the main menu options
  * Values
    * `EXIT`
    * `MAKE_RESERVATION`
    * `EDIT_RESERVATION`
    * `CANCEL_RESERVATION`
    * `VIEW_RESERVATIONS_FOR_HOST`
    * `VIEW_RESERVATIONS_FOR_INACTIVE_HOST`
  * Fields
    * `private int value` --- constructor arg
    * `private String message` --- constructor arg
    * `private boolean hidden` --- constructor arg
  * Methods
    * `public static ReservationMenuOption fromValue(int value)` --- (get ReservationMenuOption.Value from int value)
    * `getters`
  * Time to Complete
    * Estimated Time: 1 hours
    * Actual Time: 30 minutes

- [x] Create `GuestMenuOption` enumerator --- provides discrete values for the main menu options
  * Values
    * `EXIT`
    * `ADD_GUEST`
    * `EDIT_GUEST`
    * `VIEW_GUESTS_BY_LAST_NAME`
    * `VIEW_GUESTS_BY_STATE`
    * `INACTIVATE_GUEST`
    * `REACTIVATE_GUEST`
  * Fields
    * `private int value` --- constructor arg
    * `private String message` --- constructor arg
    * `private boolean hidden` --- constructor arg
  * Methods
    * `public static GuestMenuOption fromValue(int value)` --- (get GuestMenuOption.Value from int value)
    * `getters`
  * Time to Complete
    * Estimated Time: 1 hours
    * Actual Time: 30 minutes

- [x] Create `HostMenuOption` enumerator --- provides discrete values for the main menu options
  * Values
    * `EXIT`
    * `ADD_HOST`
    * `EDIT_HOST`
    * `VIEW_HOSTS_BY_LAST_NAME`
    * `VIEW_HOSTS_BY_STATE`
    * `INACTIVATE_HOST`
    * `REACTIVATE_HOST`
  * Fields
    * `private int value` --- constructor arg
    * `private String message` --- constructor arg
    * `private boolean hidden` --- constructor arg
  * Methods
    * `public static HostMenuOption fromValue(int value)` --- (get HostMenuOption.Value from int value)
    * `getters`
  * Time to Complete
    * Estimated Time: 1 hours
    * Actual Time: 30 minutes

- [x] Create `View` classes
  * Dependencies: `ConsoleIO` --- uses methods from the ConsoleIO to display and receive information using the console. View class never directly interacts with the console.
  * Fields
    * `private final ConsoleIO io` --- constructor arg
  * Methods
    * `public MainMenuOption selectMainMenuOption`
    * `public LocalDate getReservationStartDate` // will likely overload for update feature
    * `public LocalDate getReservationEndDate(LocalDate startDate)` // will likely overload for update feature
    * `public Category chooseState`
    * `public String getHostEmail`
    * `public String getGuestEmail`
    * `private String getEmail`
    * `public int getReservationId`
    * `public Reservation makeReservation`
    * `public Reservation updateReservation(Reservation reservation)`
    * `public Reservation cancelReservation(Reservation reservation)`
    * `public void enterToContinue`
    * `public displayHeader`
    * `displayException`
    * `displayStatus` // will likely overload for List<String> and just String
    * `displayReservations`
    * // If time allows, will add more viewing, creating, updating, and deleting methods for Guest and Host objects
  * Time to Complete
    * Estimated Time: 3 hours
    * Actual Time: 6 hours

### Inject Spring Dependency
- [x] Use Annotations to perform Spring dependency injection
* Time to Complete
  * Estimated Time: 1 hour
  * Actual Time: 30 minutes

## Additional Tasks

### Bugs found
- [ ] tbd
  * Issue: 
  * Solution: 

### Research Required
- [x] Increment a variable in a stream to display a numbered List
  * Solution: Use an AtomicInteger (believe this is because we need a static variable?)