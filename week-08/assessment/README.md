# M08 Assessment: React Field Agent

## Goals

* Start a React front-end for the Field Agent HTTP service.

## Set Up
 
* Use the Field Agent HTTP Service from Module 7 as your back-end data service.
* Create a new React application with Create React App named field-agent-ui.

## High-Level Requirements

Implement a full CRUD UI for agents.
* Display all agents.
* Add an agent.
* Update an agent.
* Delete an agent.

## Features

### Navigation

* When the UI first renders, decide what the user should see. It's okay to start with the Display All Agents view (and an option to add an agent). Long term, the Field Agent UI will manage more than agents. It will manage agencies, missions, locations, and aliases. If time allows, consider adding robust navigation.

### Display All Agents

* Display all agents in a short and easy-to-read format. This view is a summary. Don't include all agent properties.
* Decide if you want to display in a tabular format or something else. Each agent UI "record" should include a UI element (button, link, etc) to trigger an edit or delete action for the agent. (Optionally, include a way to expand all agent data in a read-only format.)

### Add An Agent

* Should be available as a menu option, button, or link in navigation or the Display All Agents view.
* Enter agent data into an HTML form. Form submission creates a new agent and navigates to the Display All Agents view. Invalid data is rejected. (Feel free to use HTML 5 form validation.) User may cancel and return to the Display All Agents view.
* Don't worry about related data: aliases, missions, and agencies. Though you may need to pass empty arrays for the HTTP service to accept the related properties.

### Update An Agent

* On arrival, the HTML form should be pre-populated with the existing agent. Edit data in the form. Form submission updates the agent and navigates to the Display All Agents view. Invalid data is rejected. User may cancel and return to the Display All Agents view.
* If time allows, show related data: aliases, missions, and agencies, but don't start with them.

### Delete An Agent

* On arrival, display an agent summary and allow the user to delete or cancel. Both options return to the Display All Agents view.

## Technical Requirements

* Use Create React App.
* Use fetch for async HTTP.
* You are not allowed to change the Field Agent HTTP Service or database (unless there's a confirmed bug and your instructor approves).
* Use a CSS framework.

## Approach

* Plan before you write code. The data model is provided by the HTTP service. It can't be changed. Consider how best to use that model to build components. Determine how components are related and how they will communicate with each other.
* Don't make assumptions. Ask questions.
* Start with Display All Agents in read-only mode (no edit or delete actions). Then move on to add, edit, or delete.

## Stretch Goals

* Add an alias CRUD UI.
* Add an agency CRUD UI.
* Show and edit relationships between agents and agencies.
* Advanced navigation
* Routing

# Questions your plan should answer...
 
### Basic Questions
* What are the requirements for the project?
  * Implement a full CRUD UI for agents. (as described in *High Level Requirements*)
  * Use Create React App.
  * Use fetch for async HTTP.
  * You are not allowed to change the Field Agent HTTP Service or database (unless there's a confirmed bug and your instructor approves).
  * Use a CSS framework.
* Are there any requirements that I need to get clarification on?
  * No, they seem clear.
* Do I have to do any research?
  * I may want to do some research on building robust navigation, but I have an idea for how this could be accomplished within what we have learned.
* Are there any unknowns? What do I need to do to get clarity?
  * No unknowns at this time.
* What are my primary tasks?
  1. Create a directory containing a server folder (Java API app) and a client folder (React app)
  2. Enable full CRUD for Agents in the React app through HTTP requests sent to the server
  3. Create forms that will be used to handle user inputs (add/update features)
  4. Establish validation of all user input fields
  5. Handle errors encountered in a way that is meaningful to the user
* How long do I estimate each of those tasks will take?
  1. 30 minutes
  2. 3 hours
  3. 2 hours
  4. 2 hours
  5. 2 hours
* Are there any dependencies between tasks? What order do I need to complete the tasks in?
  * I will want to approach the tasks in order. It's important to make sure that each stage is fully functional to ensure that I can track down any errors/bugs as they arise. (Error messages aren't always very descriptive)


### Components and Layout
* What components will I create initially?
  * App.js
  * AgentFetch.js
  * Agent.js
  * AgentAddForm.js
  * AgentUpdateForm.js
  * Errors.js
* What components might I try refactoring into?
  * I may want to refactor the list of displayed agents (from AgentFetch) into an `AgentList` component, but I am not sure if that will be necessary yet
  * I am sure I'll be struck with some other components to refactor into as well
* How will I organize my UI?
  * Initially, I will have a Form displayed at the top (by default the Add Agent form). This will contain user input fields for all agent fields.
* How will I display the list of agents? What information will I include in that list?
  * I will render a table (headers are the names of each field, rows are individual agent's values)
  * I may include agentId, but will be including all other fields other than agencies and aliases
    * firstName, middleName, lastName, dob, heightInInches
* How will the user start the process of adding an agent?
  * By default the `Add Agent` form will be at the top of the page, the user will fill out the form then the Add Agent button will become enabled (it will be disabled when required fields are empty)
  * The user will then click the `Add Agent` button, sending the HTTP POST request
* How will the user start the process of editing?
  * The user will click the `Edit` button next to an Agent row, which will convert the Add Agent form to an Update Agent form.
  * They will then fill it out and click the `Update Agent` button below the form, sending the HTTP PUT request
* How will the user delete an agent?
  * The user will click the `Delete` button next to an Agent row, sending the HTTP DELETE request for that Agent's agentId
* What form fields do I need to allow users to add/edit agents?
  * First Name
  * Middle Name
  * Last Name
  * Date of Birth
  * Height in Inches
* How can I use conditional rendering to keep my UI from getting confusing or cluttered?
  * I will have forms conditionally appear/disappear when requested (by default the Add form will be visible, but if `Update` is clicked on a row then it will conditionally be removed and replaced with the Update form)
* How can I display validation error messages in my UI so the user can understand what they need to change about their provided data?
  * I will have an Error Messages banner at the top of the page that is hidden by default. If any error messages are added (they will be managed with state) then the banner will be displayed
* What do the agent HTTP requests need to look like?
  * I need to use fetch requests, passing in the URL of the request and the init object (containing the method, headers, and body/agent)

# Tasks

### Elapsed Time

* TOTAL TIME:
  * Estimated: 14.25 hours
  * Actual: 16.75 hours

* Estimated (Without Stretch Goals): 8.75 hours
* Actual (Without Stretch Goals): 10 hours

### Task List

* [x] Set up Project Directory and Basic React App
  * Estimated Time: 1 hour
  * Actual Time: 0.75 hours
* [x] Set up AgentFetch.js
  * Estimated Time: 2 hours
  * Actual Time: 3 hours
* [x] Set up Agent.js
  * Estimated Time: 0.25 hours
  * Actual Time: 0.25 hours
* [x] Set up AgentAddForm.js
  * Estimated Time: 1.5 hours
  * Actual Time: 2 hours
* [x] Set up AgentUpdateForm.js
  * Estimated Time: 1 hour
  * Actual Time: 0.5 hours
* [x] Set up AgentDeleteForm.js
  * Estimated Time: 0.5 hours
  * Actual Time: 0.25 hours
* [x] Set up Errors.js
  * Estimated Time: 0.5 hours
  * Actual Time: 0.25 hours
* [x] Play with CSS to "prettify" the app
  * Estimated Time: 2 hours
  * Actual Time: 4 hours

## Stretch Goal Tasks

### Elapsed Time

* Estimated: 5.5 hours
* Actual: 5.75 hours

### Advanced Navigation Task List
* [x] Set up MenuSelection.js
  * Estimated Time: 2 hours
  * Actual Time: 1 hour
* [x] Refactor to have Agents menu button display the Agents features
  * Estimated Time: 1 hour
  * Actual Time: 2 hours

### Alias CRUD UI Task List
* [x] Set up AliasFetch.js
  * Estimated Time: 1 hour
  * Actual Time: 1.5 hours
* [x] Set up Alias.js
  * Estimated Time: 0.25 hours
  * Actual Time: 0.25 hours
* [x] Set up AliasAddForm.js
  * Estimated Time: 0.5 hours
  * Actual Time: 0.5 hours
* [x] Set up AliasUpdateForm.js
  * Estimated Time: 0.5 hours
  * Actual Time: 0.25 hours
* [x] Set up AliasDeleteForm.js
  * Estimated Time: 0.25 hours
  * Actual Time: 0.25 hours

```
Will need to figure out how to select an agent from a list to attach to an Alias (instead of having user input the agentId) // OPTED FOR NAVIGATING TO ALIAS PAGE DIRECTLY FROM AGENT LIST
```
