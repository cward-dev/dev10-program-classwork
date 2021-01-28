package learn.games.ui;

import learn.games.models.MonteGame;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ThreeCardMonteController {

    static List<MonteGame> monteGames = new ArrayList<>();
    static int nextId = 0;

    @PostMapping("/monte")
    public String startGame() {
        MonteGame monteGame = new MonteGame();
        monteGames.add(monteGame);

        nextId++;
        monteGame.setGameId(nextId);
        monteGame.setMaxNumber(100);

        return "Your game id is [" + monteGame.getGameId() + "].";
    }

    @PutMapping("/monte/{gameId}/{guess}")
    public ResponseEntity<String> makeMove(@PathVariable int gameId, @PathVariable int guess){
        MonteGame current = monteGames.stream().filter(m -> m.getGameId() == gameId).findFirst().orElse(null);

        if (guess < 1 || guess > current.getMaxNumber()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        current.addGuess(guess);

        if (guess == current.getTargetNumber()) {
            ResponseEntity<String> response = new ResponseEntity<String>(String.format("%s is correct! You won in %s guesses!", guess,
                    current.getGuesses().size()), HttpStatus.CREATED);
            System.out.println(response.getBody());
            return response;
        }

        if (guess > current.getTargetNumber()) {
            ResponseEntity<String> response = new ResponseEntity<String>(String.format("%s is too high! Try a lower number.", guess), HttpStatus.CREATED);
            System.out.println(response.getBody());
            return response;
        }

        if (guess < current.getTargetNumber()) {
            ResponseEntity<String> response = new ResponseEntity<String>(String.format("%s is too low! Try a higher number.", guess), HttpStatus.CREATED);
            System.out.println(response.getBody());
            return response;
        }

        ResponseEntity<String> wrongGuess = new ResponseEntity<String>("Wrong",HttpStatus.CREATED);
        System.out.println(wrongGuess.getBody());
        return wrongGuess;
    }
}
