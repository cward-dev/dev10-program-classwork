package learn.games.models;

import java.util.ArrayList;
import java.util.List;

public class MonteGame {

    private int gameId;
    private int targetNumber;
    private int maxNumber;
    private List<Integer> guesses = new ArrayList<>();

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
        this.targetNumber = (int)Math.floor(Math.random() * maxNumber);
        this.targetNumber++;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public List<Integer> getGuesses() {
        return guesses;
    }

    public void addGuess(int guess) {
        this.guesses.add(guess);
    }
}
