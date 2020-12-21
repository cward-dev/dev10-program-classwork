package learn.gomoku.ui;

import learn.gomoku.game.Gomoku;

public class App {

    public static void main(String[] args) {
        boolean playAgain;
        do {
            SetUp setUp = new SetUp();
            Gomoku gomoku = setUp.run();

            Game game = new Game(gomoku);
            playAgain = game.run();

        } while (playAgain);
    }
}
