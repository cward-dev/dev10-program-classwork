package learn.games.ui;

import java.util.ArrayList;
import java.util.List;

import learn.games.models.HangmanGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HangmanController {

    static List<HangmanGame> games = new ArrayList<>();
    static int nextId = 0;

    @PostMapping("/hangman")
    public String startGame() {
        HangmanGame newGame = new HangmanGame();


        nextId++;
        newGame.setHangmanId(nextId);
        newGame.setTargetWord();

        games.add(newGame); // we forgot to add the game to the array...

        return String.format("Your game id is %s. Word: %s", newGame.getHangmanId(), " _ ".repeat(newGame.getTargetWord().length()));
    }

    @PutMapping("/hangman/{hangmanId}/{letter}")
    public ResponseEntity<String> makeGuess(@PathVariable int hangmanId, @PathVariable char letter){
        HangmanGame current = games.stream().filter(h -> h.getHangmanId() == hangmanId).findFirst().orElse(null);

        if (current == null) {
            return new ResponseEntity<String>("Null object", HttpStatus.NOT_FOUND);
        }

        boolean alreadyGuessed = current.getGuessedLetters().stream()
                .anyMatch(c -> c == letter);

        if(alreadyGuessed){
            return ResponseEntity.badRequest().build();
        }

        current.addGuessedLetter(letter);

        List<Character> targetLetters = current.getTargetWordLetters();

        boolean foundMatch = false;
        String displayedWord = "";

        for (Character ct : targetLetters) {

            foundMatch = false;
            for (Character cg : current.getGuessedLetters()) {
                if (cg == ct) {
                    foundMatch = true;
                }
            }

            if (foundMatch) {
                displayedWord = displayedWord + " " + ct + " ";
            } else {
                displayedWord = displayedWord + " _ ";
            }
        }
        /*if (current.getGuessedLetters() == current.getTargetWordLetters())
        {
            return new ResponseEntity<String>(displayedWord + "%n Congrats you win!", HttpStatus.CREATED);
        }*/

        return new ResponseEntity<String>(displayedWord, HttpStatus.CREATED);

    }

}
