# Capsule Hotel Assessment Plan

### Objectives
1. Purpose: allow the user to manage the rooms (1-100) of a capsule hotel.
2. This includes the following options:
    1. Book a guest in an unoccupied capsule
    2. Check a guest out of an occupied capsule
    3. Display guest list for an indicated room and 5 rooms above/below it
    4. Exit the program with a prompt to ensure intention

### Pseudocode

#### main method
* declare Scanner console variable
* call introduction()
* declare int numOfCapsules variable = getNumOfCapsules(console)
* declare String[] capsules variable = new String[numOfCapsules]
* call menu(console, numOfCapsules)
    
#### void introduction()
* print start-up message
    * Welcome to Capsule-Capsule.
    * ===========================
    
#### int getNumOfCapsules(Scanner console)
* declare int numOfCapsules variable
* print prompt for number of available capsules
    * Enter the number of capsules available
        * Assign number entered to console to numOfCapsules variable
* assign console input to numOfCapsules
* print confirmation
    * There are **[numOfCapsules]** unoccupied capsules ready to be booked.
    
#### void menu(Scanner console, String[] capsules)                              // ### IC - for the method names try to follow Java naming conventions, 
                                                                                // ### i.e. start method name with a verb, e.g. displayMenu()
* declare int menuSelection variable
* declare boolean exitProgram = false;

* initiate a do-while loop to allow user to work through the menu while exitProgram == false
    * Print Main Menu
        * Guest Menu
        * ==========
        * 1 - Check in
        * 2 - Check out
        * 3 - View Guests
        * 4 - Exit
        * Choose an option [1-4]:
            * Assign number entered to console to menuSelection variable
    * initiate a switch statement for menuSelection
        * case 1 - call checkIn(console, capsules[])
        * case 2 - call checkOut(console, capsules[])
        * case 3 - call viewGuests(console, capsules[])
        * case 4 - assign exitProgram == call exit(console)
        * default - print error message
    
#### void checkIn(Scanner console, String[] capsules)
* declare String guestName
* declare int capsuleNumber
* assign both variables from the console with appropriate prompts
    * use do-while validation to check for valid entries                        // ### IC - what if the user's input for the capsule number is outside of the range?
        * guestName.isBlank()
        * capsules[capsuleNumber] == null
* update array with the new information
* print success message and confirmation

#### void checkOut(Scanner console, String[] capsules)
* declare int capsuleNumber
* assign variable from the console with appropriate prompt
    * use validation to check for valid entry
        * capsuleNumber - use if statement to ensure that capsule is occupied   // ### IC - what if ALL of the capsules unoccupied? 
* update array with the new information (capsules[capsuleNumber] = null)
* print success message and confirmation

#### void viewGuests(Scanner console, String[] capsules)
* declare int capsuleNumber
* assign variable from the console with appropriate prompt
    * use validation to check for valid entry
* initiate for loop to print capsule # and guest name (or [unoccupied]) for capsules[capsuleNumber] and 5 capules above and below it (11 capsules total)
    * initiate if statement to account for fringe capsules (<6 or >95)      // ### IC - not 95 specifically, right? 
        * if capsuleNumber < 6 then print capsules[0-10]
        * if capsuleNumber > 95 then print capsules[89-99]

#### boolean exit(Scanner console)
* declare String answer variable
* prompt user if they are sure they want to exit, all data will be lost [y/n]
    * if y then return true
    * if n then return false
