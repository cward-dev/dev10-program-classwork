package learn.poker;

import learn.cards.Card;

public class TwoCardHand implements Comparable<TwoCardHand> {

    private final Card one;
    private final Card two;

    public TwoCardHand(Card one, Card two) {
        this.one = one;
        this.two = two;
    }

    public Card getOne() {
        return one;
    }

    public Card getTwo() {
        return two;
    }

    @Override
    public int compareTo(TwoCardHand o) {
        // 1. Complete the compareTo method.
        // If the current TwoCardHand(`this`) has a lower score than the TwoCardHand parameter, compareTo returns
        // an int less than 0.
        // If `this` has a higher score than the TwoCardHand parameter, compareTo returns an int greater than 0.
        // If `this` and the TwoCardHand parameter have the same score, compareTo returns 0.
        // See Exercise04.md for scoring rules.

        // Get your high and low card rank value
        int yourHighValue;
        int yourLowValue;
        if (one.getRank().getValue() > two.getRank().getValue()) {
            yourHighValue = one.getRank().getValue();
            yourLowValue = two.getRank().getValue();
        } else {
            yourHighValue = two.getRank().getValue();
            yourLowValue = one.getRank().getValue();
        }

        // Get opponent high and low card rank value
        int opponentHighValue;
        int opponentLowValue;
        if (o.one.getRank().getValue() > o.two.getRank().getValue()) {
            opponentHighValue = o.one.getRank().getValue();
            opponentLowValue = o.two.getRank().getValue();
        } else {
            opponentHighValue = o.two.getRank().getValue();
            opponentLowValue = o.one.getRank().getValue();
        }

        // Pairs logic
        if (yourHighValue == yourLowValue) {
            if (opponentHighValue == opponentLowValue) {
                return Integer.compare(yourHighValue, opponentHighValue);
            } else {
                return 1;
            }
        } else if (opponentHighValue == opponentLowValue) {
            return -1;
        }

        // High card is tied logic
        if (yourHighValue == opponentHighValue) {
            return Integer.compare(yourLowValue, opponentLowValue);
        }

        // Normal logic
        return Integer.compare(yourHighValue, opponentHighValue);
    }
}
