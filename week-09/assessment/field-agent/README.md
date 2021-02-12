# M07 Assessment: Field Agent API

## Goals

* Start a Thymeleaf UI for the Field Agent project.
* Replicate the React Field Agent UI.

## Set Up

* Continue working with the Field Agent project from Module 7. The models, repositories, and services are reusable. Extend the project by adding controllers and Thymeleaf templates to solve the problem.

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
* Enter agent data into an HTML form. Form submission creates a new agent and navigates to the Display All Agents view. Invalid data is rejected. Use Thymeleaf/Spring extensions to display error information from the BindingResult. User may cancel and return to the Display All Agents view.
* Don't worry about related data: aliases, missions, and agencies.

### Update An Agent

* On arrival, the HTML form should be pre-populated with the existing agent. Edit data in the form. Form submission updates the agent and navigates to the Display All Agents view. Invalid data is rejected. User may cancel and return to the Display All Agents view.
* If time allows, show related data: aliases, missions, and agencies, but don't start with them.

### Delete An Agent

* On arrival, display an agent summary and allow the user to delete or cancel. Both options return to the Display All Agents view.

## Technical Requirements

* Use the Validation API. Validate manually in the service and automatically in the controller.
* Use Thymeleaf: th:text, th:each, th:href, etc.
* Use Thymeleaf/Spring extensions: th:field, th:errors, #fields.hasErrors, etc.
* You are not allowed to change repositories (unless there's a confirmed bug and your instructor approves).
* Use a CSS framework.

```
Technical Tip
* Don't remove the existing @RestControllers. Add at least one new @Controller. Consider organizing controllers with packages.
```

## Approach

* Plan before you write code. Don't change code that doesn't need to be changed. Tread lightly. Leave repositories alone, make cautious edits to models and services (validation), and add new controllers and Thymeleaf templates.
* Do your best to replicate the UI from React Field Agent. Steal HTML from React components and use it in Thymeleaf templates (careful with attributes like className).
* Don't make assumptions. Ask questions.
* Start with Display All Agents in read-only mode (no edit or delete actions). Then move on to add, edit, or delete.

## Stretch Goals
* Add an alias CRUD UI.
* Add an agency CRUD UI.
* Show and edit relationships between agents and agencies.
* Advanced navigation.

# Planning
  
## Basic Questions
* What are the requirements for the project?
  * Implement a full CRUD UI for agents.
    * Display all agents.
    * Add an agent.
    * Update an agent.
    * Delete an agent.
* Are there any requirements that I need to get clarification on?
  * Not at this time.
* Do I have to do any research?
  * I don't foresee any at this time, but that is subject to change (as always).
* Are there any unknowns? What do I need to do to get clarity?
  * N/A
* What are my primary tasks?
  * Use a slice approach to implement each feature one at a time, jumping into the browser and seeing how it plays between each one
  * I will start by setting up the AgentUIController methods for the desired feature, then jump over to my html templates and create those, using the browser to render them and make adjustments
* How long do I estimate each of those tasks will take?
  * Display all agents - 2 hours
  * Add an agent - 2 hours
  * Update an agent - 45 minutes
  * Delete an agent - 1 hour
* Are there any dependencies between tasks? What order do I need to complete the tasks in?
  * I need to set up the controller methods for the given feature before moving to the html template.
  * I need to complete the Agent CRUD MVP before I focus on Alias/advanced navigation
* What annotations will I add to the Agent model to leverage the Validation API?
  * Agent (class) @NotNull
  * firstName @NotNull, @NotBlank
  * middleName
  * lastName @NotNull, @NotBlank
  * dob @DateTimeFormat(pattern = "mm/dd/yyyy")
  * heightInInches @Min(value=36) @Max(value=96)
* What validations can I remove from the AgentService class?
  * I can remove all validation logic except for the dob validation checking if an agent is too young
* How will I use the Validation API to validate agents in the AgentService class?
  * I will bring in a ValidatorFactory, Validator, and Set<ConstraintViolation<Agent>>, checking if the Set contains error messages and adding them to a PanelResult
* Are there any validations in the AgentService class that I won't replace with Validation API validators?
  * I may keep the dob maximum date validation in the service layer, but if time allows I may look at using a custom validation


* What setup do I need to do to support server-side rendering?
  * I will need to design around page refreshes between each interaction. Different from my React approach, I will have to separate the forms to a separate page from the display lists
* How will a display the list of agents?
  * I will use a table to render the list of agents (using th:each to render each <tr>)
* What agent fields will I include in the list of agents?
  * firstName
  * middleName
  * lastName
  * dob
  * heightInInches
  * aliases button (brings user to list of aliases for that agent)
* What form fields do I need to add/edit agents?
  * agentId (hidden)
  * firstName
  * middleName
  * lastName
  * dob
  * heightInInches
* How will I display validation errors to the user?
  * I will display validation errors in a summary at the top of the form (and may implement field errors on the relevant input fields as well)
* How will I keep my Thymeleaf form code DRY?
  * I will use a single form for add/edit features. I will use conditional rendering to change the values that need to differ between the two pages
* How will I handle deleting an agent?
  * I will 
* What CSS framework will I use to style my pages?
  * Bootstrap
* What stretch goals might I implement?
  * Alias CRUD UI
  * Advanced Navigation

## Time Tracking

* MVP Estimated Time: 8.25 hours
* MVP Actual Time: 
  
* Stretch Estimated Time: 5.25 hours
* Stretch Actual Time: 

* Total Estimated Time: 13.5 hours
* Total Actual Time: 

## MVP Tasks

* [ ] Agent Model Annotations
  * Estimated Time: 0.5 hours
  * Actual Time:
* [ ] Update Agent Service to use ValidatorFactory instead of validate method
  * Estimated Time: 1 hour
  * Actual Time:
* [ ] Update AgentController to include BindingResult
  * Estimated Time: 1 hour
  * Actual Time:
* [ ] Create MainMenuController
  * Estimated Time: 0.5 hours
  * Actual Time:
* [ ] Create AgentUIController
  * Estimated Time: 1.5 hours
  * Actual Time:
* [ ] Create index.html template
  * Estimated Time: 0.75 hours
  * Actual Time:
* [ ] Create agent-page.html template
  * Estimated Time: 1 hour
  * Actual Time:
* [ ] Create agent-form.html template (add/update form)
  * Estimated Time: 1.5 hours
  * Actual Time:
* [ ] Create delete-agent.html template (delete confirmation)
  * Estimated Time: 0.5 hours
  * Actual Time:
  
```
Will likely need to make updates to service to make access to certain data easier in the UIController
```

## Stretch Goal (Alias CRUD UI):

### Plan
* Add Alias CRUD features to the UI.
* Structure will be similar to the React implementation, where a button on an Agent <tr> will take the user to the alias-page template, populated with the aliases for that agent (and full CRUD)

### Tasks
* [ ] Alias Model Annotations
  * Estimated Time: 0.5 hours
  * Actual Time:
* [ ] Update Alias Service to use ValidatorFactory instead of validate method
  * Estimated Time: 0.5 hours
  * Actual Time:
* [ ] Update AliasController to include BindingResult
  * Estimated Time: 0.5 hours
  * Actual Time:
* [ ] Create AliasUIController
  * Estimated Time: 0.75 hours
  * Actual Time:
* [ ] Create alias-page.html template
  * Estimated Time: 0.75 hours
  * Actual Time:
* [ ] Create alias-form.html template (add/update form)
  * Estimated Time: 1 hour
  * Actual Time:
* [ ] Create delete-alias.html template (delete confirmation)
  * Estimated Time: 0.5 hours
  * Actual Time:

## Stretch Goal (Advanced Navigation):

### Plan
* Make index.html template a simple landing page that can then direct to the agent-page.html template.
* From agent-page.html, users can select a specific agent to view aliases for, using a button to send them to the alias-page.html template

### Tasks
* [ ] Add "Welcome, agent." link (button) to the home page (onclick sends them to /agent to display all agents with the needed agentId)
  * Estimated Time: 0.25 hours
  * Actual Time:
* [ ] Add "Aliases" link (button) to the table row for each Agent in agent-page (onclick sends them to /alias/{agentId})
  * Estimated Time: 0.5 hours
  * Actual Time:


#### Research
* [ ] TBD
