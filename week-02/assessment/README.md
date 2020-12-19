# Gomoku Assessment

## Requirements

### Basic Requirements
* Can set up two players.
* For a human player, collect their name. (A random player's name is randomly generated.)
* For each stone placement, use the player's name to ask questions.
* Since the random player doesn't require input, the UI should display stone placement and the results of placement. (Random player placement may fail since they don't know what they're doing.)
* Re-prompt on failed placement. The game must not proceed until a player has made a valid move.
* Display the final result of the game.
* Give the option to play again.

### Technical Requirements
* All rules are modeled inside the Gomoku class. You may not modify Gomoku, Player, HumanPlayer, RandomPlayer, Stone, or Result.
* Add your code to the project provided. Be sure to use sensible class names, method names, and packages.
* At least one class beyond the App class is required.
* Stones use 0-based rows and columns.

### Approach
* Plan before you write code.
* Take considerable time to understand the project code. Use existing tests to see how objects are constructed and used.
* It may be helpful to break the game into two phases: set up and gameplay.
* Write one method that accepts the game state and draws the board. Never draw the board in more than one place.

### Stretch Goals
* Create a third Player implementation and add it to your list of player options. Can you build a player that makes good choices?
* Black has an advantage as the starting player. Competitive Gomoku uses various opening rules to improve fairness. Implement one of those rules. For example, the Swap2 should be achievable using only the Gomoku's place and swap methods. In the starting screen, give the option to choose an opening rule.

## Plan
### Main method


        SetUp setUp = new SetUp();
        setUp.run();
  
        Game game = new Game();
        game.run(setUp)
        
### SetUp class
* Print welcome message
    

        sout
        Welcome to Gomoku
        =================
* Get 2 Players (Select 1 for a human player, 2 for a random player)


        sout
        Player [N] is:
        1. Human
        2. Random Player
        Select [1-2]: [user input]
* If human [1], take user input to create a HumanPlayer object with input String as name


        sout Player [N], enter your name: [user input]

        HumanPlayer playerOne = new HumanPlayer([user input]);
* If random [2], create a RandomPlayer object and print their name


        HumanPlayer playerTwo = new RandomPlayer();

        sout Player [N], enter your name: [playerTwo.getName()]
* Create new Gomoku object


        Gomoku gomoku = new Gomoku(playerOne, playerTwo);
* Print randomizing message (who will go first is being randomized)


        sout (Randomizing)
* Print who goes first (isBlack)


        sout [gomoku.getCurrent().getName()] goes first.


### Game class
// TODO Need to split into separate methods in line with Single Responsibility Principle
* Import Gomoku class
* Pass in gomoku object from Gomoku class
* Declare next stone variable
    

        Stone nextStone;
* Print whose turn it is
    

        [player name]'s Turn
* If HumanPlayer then get row and column input for move


        sout
        Enter a row: [user input]
        Enter a column: [user input]
        gomoku.place(new Stone([user input row], [user input column], *isBlack*)) // TODO Not sure how I will check for isBlack on human player's turn yet
* If RandomPlayer then display same message but autofill the randomly generated row and column for move 
        

        gomoku.place(player.generateMove())
        Enter a row: [nextStone.getRow()]
        Enter a column: [nextStone.getColumn()]
* Run Gomoku.place() method to update the board state
* If the move is valid, call printBoard() for the new board state, otherwise display error message and get move again
* printBoard() method
  * Overall strategy: nested for loop
  * Print index+1 for each row/column along axis
    * Print 01-15 above each column
    * Print 01-15 to the left of each row (will be performed during outer for loop)
  * Loop through gomoku.board[][] and print all row/column chars in appropriate order
    * If empty element then print an underscore [_]
    * Otherwise print the appropriate character [X/O]
* endGame() method
  * If gomoku.isOver() then run endGame()
    * Prints out who the winner is.
    * Prompts if the user wants to play again [y/n]. If y then reset the board and start over

## Tasks
* [x] App class (just a main method to call and pass objects to other classes)
  * Estimate: 15 minutes
  * Actual: 15 minutes
  * [x] boolean playAgain to handle looping through game until user does not want to play again
* [x] SetUp class - will have a run() method that returns Gomoku object
  * Estimate: 1 hour
  * Actual: 45 minutes
  * [x] run() method
    * return new Gomoku(playerOne, playerTwo)
  * [x] createPlayer() method
    * Create playerOne and playerTwo
* [x] Board class - will require the Gomoku.WIDTH generic class field but not an instance, not truly dependent?
  * Estimate: 30 minutes
  * Actual: 1 hour
  * [x] getBoard() getter
  * [x] updateBoard() method
  * [x] printBoard() method
* [x] Game class - has a dependency on the Gomoku class - will require a Gomoku gomoku object for constructor
  * Returns boolean to App.java for playAgain
  * Estimate: 1 hours
  * Actual: 2 hours
  * [x] Fields: Scanner console, Gomoku gomoku, Board board(with Gomoku.WIDTH for both parameters)
  * [x] run() method
  * [x] takeTurn() method
  * [x] endGame() method(will print out winner and ask if user wants to play again, returning a boolean)
  
  
## Stretch Goals
* [x] Standard
* [ ] Pro
* [ ] Long Pro
* [ ] Swap
* [ ] Swap 2