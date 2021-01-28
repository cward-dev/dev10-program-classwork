package learn.cupcake.models;

import java.time.YearMonth;

public class Entry {
    private YearMonth yearMonth;
    private int score;

    public Entry(YearMonth yearMonth, int score) {
        this.yearMonth = yearMonth;
        this.score = score;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "yearMonth=" + yearMonth +
                ", score=" + score +
                '}';
    }
}
