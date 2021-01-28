package learn.games.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HangmanGame {

    private String targetWord;
    private List<String> guessedWords = new ArrayList<>();
    private List<Character> guessedLetters = new ArrayList<>();
    private List<Character> targetWordLetters = new ArrayList<>();
    private int HangmanId;

    public List<String> getGuessedWords() {
        return guessedWords;
    }

    public void setGuessedWords(ArrayList<String> guessedWords) {
        this.guessedWords = guessedWords;
    }

    public int getHangmanId() {
        return HangmanId;
    }

    public void setHangmanId(int hangmanId) {
        HangmanId = hangmanId;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord() {
        List<String> words = List.of(
                "pumpernickel"
//                "boxcar",
//                "buffalo",
//                "strengths",
//                "staff",
//                "zombie",
//                "wheezy",
//                "witchcraft",
//                "swivel",
//                "squawk",
//                "stronghold",
//                "kiwifruit",
//                "klutz",
//                "onomatopoeia",
//                "beekeeper",
//                "zigzagging",
//                "espionage"
        );

        int randomSelection = (int)(Math.floor(Math.random() * words.size()));
        String targetWord = words.get(randomSelection);
        this.targetWord = targetWord;

        for(Character c: targetWord.toCharArray()) {
            this.targetWordLetters.add(c);
        }
    }

    public void setTargetWord(String targetWord) { //overloaded method
        this.targetWord = targetWord;

        for(Character c: this.targetWord.toCharArray()) {
            this.targetWordLetters.add(c);
        }
    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void addGuessedLetter(char letter) {
        this.guessedLetters.add(letter);
    }

    public List<Character> getTargetWordLetters() {
        return targetWordLetters;
    }
}