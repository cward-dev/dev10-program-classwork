# Solar Farm Assessment

## Requirements

### High Level Requirements
* The user is a solar farm administrator.
  * Add a solar panel to the farm.
  * Update a solar panel.
  * Remove a solar panel.
  * Display all solar panels in a section.
    
### Panels
* The application is only required to track solar panels. The concept of sections is not a separate class. It is a field in the solar panel class.
* Data 
  * Section: name that identifies where the panel is installed.
  * Row: the row number in the section where the panel is installed.
  * Column: the column number in the section where the panel is installed.
  * Year Installed
  * Material: multicrystalline silicon, monocrystalline silicon, amorphous silicon, cadmium telluride, or copper indium gallium selenide.
  * Is Tracking: determines if the panel is installed with sun-tracking hardware.
  * You may find it useful to add a unique identifier to each panel, though it's not required. Panels can also be uniquely identified by section, row, and column.
* Rules
  * Section is required and cannot be blank.
  * Row is a positive number less than or equal to 250.
  * Column is a positive number less than or equal to 250.
  * Year Installed must be in the past.
  * Material is required and can only be one of the five materials listed.
  * Is Tracking is required.
  * The combined values of Section, Row, and Column may not be duplicated.

### Technical Requirements
* Start with a three-layer architecture. If you have a compelling reason to vary from the architecture, share it with your instructor and they will make a decision.
* Data must be stored in a delimited file. Stopping and starting the application should not change the underlying data. The application picks up where it left off.
* Repositories should throw a custom exception, never file-specific exceptions.
* Repository and service classes must be fully tested with both negative and positive cases. Do not use your "production" data file to test your repository.
* Solar panel material should be a Java enum with five values. Since solar technology is changing quickly, an enum may be a risky choice. The requirement is included specifically to practice with enums.

## Plan
* Total Time (Estimate): 10 hours, 25 minutes
  * Total Time (Actual) : 13 hours, 55 minutes

### Data Folder (in root folder)
* Time (Estimate): 5 minutes
  * Time (Actual) : 5 minutes
* solar-panels.csv // actual data file
* solar-panels-seed.csv // seed data file that will be copied over for data layer tests
* solar-panels-test.csv // text data file that will be populated from seed file for data layer tests

### Models
* Time (Estimate): 45 minutes
  * Time (Actual) : 15 minutes
* Panel
  * private int panelId; // will not be set by user, will be equal to the highest existing id + 1
  * private String section; // required and cannot be blank
  * private int row; // positive number <= 250
  * private int column; // positive number <= 250
  * private int yearInstalled; // must be in the past
    * To verify, will compare to int currentYear = Year.now(ZoneId.of("America/Chicago")).getValue(); // gets the current year in CST
  * private boolean isTracking;
* PanelMaterial
  * Values
    * MULTICRYSTALLINE_SILICON("multicrystalline silicon", "c-Si")
    * MONOCRYSTALLINE_SILICON("monocrystalline silicon", "mono-Si")
    * AMORPHOUS_SILICON("amorphous silicon", "a-Si")
    * CADMIUM_TELLURIDE("cadmium telluride", "CdTe")
    * COPPER_INDIUM_GALLIUM_SELENIDE("copper indium gallium selenide", "CIGS")
  * public final String name;
  * public final String abbreviation;
  * PanelMaterial(String name, String abbreviation)
  * declare getters and setters for name and abbreviation

### Data Layer
* Time (Estimate): 2 hours
  * Time (Actual) : 1 hour
* PanelRepository (interface)
  * Will write to and read from "./data/solar-panels.csv"
  * Will implement CRUD to manipulate persistent data
    * C - Panel add(Panel panel)
    * R - List\<Panel> findAll(), Panel findById(int panelId), List\<Panel> findBySection(String section), List\<Panel> findByMaterial(PanelMaterial material)
    * U - boolean update(Panel panel)
    * D - boolean deleteById(int panelId)
* PanelFileRepository (implements PanelRepository)
  * Will also require the following private helper methods:
    * String serialize(Panel panel) and Panel deserialize(String value)
    * void writeAll() // repopulates the data file
    * Strategy: use findAll() as the starting point for all other methods, returning an ArrayList\<Panel> that will be updated and written back to the file with writeAll()
* DataAccessException (extends Exception)
  * Custom exception that notifies us when we encounter an error accessing the data file

### Domain Layer
* Time (Estimate): 1 hour, 30 minutes
  * Time (Actual) : 1 hour
* PanelService - dependencies : PanelRepository repository
  * Will handle all data from the PanelRepository and validate it to be passed to the View
  * List\<Panel> findBySection(String section)
  * Panel findById(int panelId)
  * Panel findByLocation(String section, int row, int column)
  * PanelResult update(Panel panel)
  * List\<Panel> updateRange(String section, int rowStart, int columnStart, int rowEnd, int columnEnd) // bulk update features
    * Not sure how I will implement this yet. Either will need to findBySection() then initialize an array and populate with Panels in given range, or may move this to the controller and have it send each capsule to be updated to the service individually (although this seems inefficient)
  * PanelResult add(Panel panel)
  * PanelResult delete(int panelId)
  * PanelResult validateInputs(Panel panel) // Used to validate that a Panel argument is a valid Panel
* PanelResult
  * private ArrayList\<String> messages
  * private Panel payload;
  * void addErrorMessage() // will include domain's custom error messages that are displayed to the user when needed
  * boolean isSuccess() // if messages.size() == 0 then returns true // used to determine if repository action was successful
  * getter and setter for Panel payload, only getter for String messages

### UI Layer
* Time (Estimate): 3 hours, 30 minutes
  * Time (Actual) : 8 hours
* Controller - dependencies: PanelService service, View view
  * run() // tries calling runMenu() and finally catches our DataAccessException that was thrown around, printing "Fatal Err: " + ex
  * runMenu() // get menu selection from view and allow continued selections until EXIT is selected
  * displayPanels() - get header from view, get panel section from view, get List\<Panel> from service, get display of panels from view
  * createPanel() - get header from view, get new Panel from view, send panel to service and get PanelResult from service, get displayResult from view
  * updatePanelById() - get header from view, get panelId from view, get Panel from service, get updated panel from view, get displayResult from view
  * updatePanelBySection() - get header from view, get section from view, get List\<Panel> from service, get updated fields from view, send updated panels to service and return PanelResult, get displayResult from View
  * updatePanel() - get header from view, get section from view, get List\<Panel> from view, get updated panel from view, check if panel is null, send updated panel to service and return PanelResult, get displayResult from view
  * deletePanel() - same flow as update, but using delete methods in view/service
* View
  * void printHeader()
  * MenuOption displayMenuAndSelect() - handles user selection of menu choices to be returned to the controller
  * void displayPanels(List\<Panel> panels) - prints out the List\<Panel> argument to the ui (if none then prints "No panels found.")
  * void displayResult(PanelResult result) - if success then prints "Success.", otherwise prints "Err: " + result messages
  * Panel makePanel() - gets new Panel fields from user input, then creates and returns that Panel
  * Panel update(List\<Panel> panels) - gets list of panels and gets user selection from that list to be passed to overloaded method
  * Panel update(Panel panel) - OVERLOADED - gets user inputs to edit the Panel argument and return the updated version
    * If any given field is given a blank value, will keep that field's previous value
  * Panel findPanel() - gets a panel by panelId from user, or gets a panel by location information (section, row, column)
  * read methods
    * readString(String prompt)
    * readRequiredString(String prompt) // will loop until a value is provided
    * readInt(String prompt)
    * readInt(String prompt, int min, int max) - OVERLOADED - // will loop until a value in range is provided
    * read PanelMaterial(String prompt)
* MenuOption (enum)
  * Values
    * EXIT("Exit")
    * DISPLAY_PANELS("Display Solar Panels")
    * CREATE_PANEL("Create Solar Panel")
    * UPDATE_PANEL("Update Solar Panel")
    * DELETE_PANEL("Delete Solar Panel")
  * private final String title;
  * MenuOption(String title)
  * public string getTitle()

### learn.sf.ui.App.java
* Time (Estimate): 5 minutes
  * Time (Actual) : 5 minutes
* main method
  * PanelFileRepository repository = new PanelFileRepository("./data/solar-panels.csv");
  * PanelService service = new PanelService(repository);
  * View view = new View();
  * Controller controller = new Controller(service, view);
  * controller.run();

### Tests
* Time (Estimate): 2 hours, 30 minutes
  * Time (Actual) : 3 hours, 30 minutes
* Data Layer
  * PanelFileRepositoryTest
    * Will use data from "solar-panels-seed.csv" and "solar-panels-test.csv" to test the PanelFileRepository without tampering with source data
  * PanelRepositoryDouble
    * Will mimic the outputs of a working PanelRepository object for use in domain testing
* Domain Layer
  * PanelServiceTest
    * Will utilize the PanelRepositoryDouble for a controlled testing environment