package learn.poker;

import learn.cards.Card;
import learn.cards.Rank;
import learn.cards.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoCardHandTest {

    // 1. Instantiate hands with the appropriate suit and rank for each test.
    // 2. Run the tests and confirm they pass. Do NOT edit existing tests.
    // 3. Add a couple more tests to confirm everything is working as intended.

    @Test
    void twoQueensShouldBeatTwo10s() {
        // TODO: instantiate Cards and TwoCardHands with appropriate arguments
        TwoCardHand queenQueen = new TwoCardHand(new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.QUEEN, Suit.HEARTS));
        TwoCardHand tenTen = new TwoCardHand(new Card(Rank.TEN, Suit.SPADES), new Card(Rank.TEN, Suit.CLUBS));
        assertTrue(queenQueen.compareTo(tenTen) > 0);
    }

    @Test
    void two9sShouldBeatJack10() {
        // TODO: instantiate Cards and TwoCardHands with appropriate arguments
        TwoCardHand nineNine = new TwoCardHand(new Card(Rank.NINE, Suit.CLUBS), new Card(Rank.NINE, Suit.DIAMONDS));
        TwoCardHand jackTen = new TwoCardHand(new Card(Rank.JACK, Suit.CLUBS), new Card(Rank.TEN, Suit.CLUBS));
        assertTrue(nineNine.compareTo(jackTen) > 0);
    }

    @Test
    void queen4ShouldBeatJack10() {
        // TODO: instantiate Cards and TwoCardHands with appropriate arguments
        TwoCardHand queenFour = new TwoCardHand(new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.FOUR, Suit.CLUBS));
        TwoCardHand jackTen = new TwoCardHand(new Card(Rank.JACK, Suit.CLUBS), new Card(Rank.TEN, Suit.CLUBS));
        assertTrue(queenFour.compareTo(jackTen) > 0);
    }

    @Test
    void queen5ShouldBeatQueen3() {
        // TODO: instantiate Cards and TwoCardHands with appropriate arguments
        TwoCardHand queenFive = new TwoCardHand(new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.FIVE, Suit.CLUBS));
        TwoCardHand queenThree = new TwoCardHand(new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.THREE, Suit.CLUBS));
        assertTrue(queenFive.compareTo(queenThree) > 0);
    }

    @Test
    void two5sShouldTieTwo5s() {
        // TODO: instantiate Cards and TwoCardHands with appropriate arguments
        TwoCardHand firstFiveFive = new TwoCardHand(new Card(Rank.FIVE, Suit.DIAMONDS), new Card(Rank.FIVE, Suit.CLUBS));
        TwoCardHand secondFiveFive = new TwoCardHand(new Card(Rank.FIVE, Suit.SPADES), new Card(Rank.FIVE, Suit.HEARTS));
        assertEquals(0, firstFiveFive.compareTo(secondFiveFive));
    }

    @Test
    void jack4ShouldTieJack4() {
        // TODO: instantiate Cards and TwoCardHands with appropriate arguments
        TwoCardHand firstJackFour = new TwoCardHand(new Card(Rank.JACK, Suit.CLUBS), new Card(Rank.FOUR, Suit.SPADES));
        TwoCardHand secondJackFour = new TwoCardHand(new Card(Rank.JACK, Suit.DIAMONDS), new Card(Rank.FOUR, Suit.HEARTS));
        assertEquals(0, firstJackFour.compareTo(secondJackFour));
    }

}