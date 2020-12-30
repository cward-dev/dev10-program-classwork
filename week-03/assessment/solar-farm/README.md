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

***

