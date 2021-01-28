package learn.cupcake;

import learn.cupcake.data.Repository;
import learn.cupcake.models.Entry;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        /*

        Cupcake

        https://trends.google.com/trends/explore?date=all&q=%2Fm%2F03p1r4

        Numbers represent search interest relative to the highest point on the chart for the given region and time.
        A value of 100 is the peak popularity for the term. A value of 50 means that the term is half as popular.
        A score of 0 means there was not enough data for this term.

        1) Use manual looping to answer the following questions:

        a) Display the rankings for 2010
        b) Which month/year has the highest ranking?
        c) Which month was the first month to have a ranking of 50 or greater?
        d) Which year has the highest average ranking?

        2) Use the Streams API to answer the same questions.

         */

        Repository repository = new Repository("./data/google-trends-data.csv");

        List<Entry> entries = repository.getEntries();
        System.out.println(entries.size());

        // Looping - a) Display the rankings for 2010
        for (Entry entry : entries ) {
            if (entry.getYearMonth().getYear() == 2010) {
                System.out.println(entry);
            }
        }

        // Streams - a) Display the rankings for 2010
        entries.stream()
                .filter(entry -> entry.getYearMonth().getYear() == 2010)
                .forEach(System.out::println);

        // Looping - b) Which month/year has the highest ranking?
        int maxRanking = 0;
        for (Entry entry : entries) {
            if (maxRanking < entry.getScore()) {
                maxRanking = entry.getScore();
            }
        }

        // Streams - b) Which month/year has the highest ranking?
        int maxValue = entries.stream()
                .collect(Collectors.summarizingInt(Entry::getScore))
                .getMax();
        System.out.println("Max Value: " + maxValue);

        // Looping - c) Which month was the first month to have a ranking of 50 or greater?
        Entry firstEntryFiftyPlus = null;
        for (Entry entry : entries) {
            if (entry.getScore() >= 50) {
                firstEntryFiftyPlus = entry;
                break;
            }
        }
        System.out.println(firstEntryFiftyPlus);

        // Streams - c) Which month was the first month to have a ranking of 50 or greater?
        Optional<Entry> firstAtLeast50 = entries.stream()
                .filter(entry -> entry.getScore() >= 50)
                .findFirst();
        System.out.println(firstAtLeast50);

        // Looping - d) Which year has the highest average ranking?
//        int[] yearNum = new int[17]; // 0 = 2004, 16 = 2020
//        int[] yearTotal = new int [17];
//        double[] yearAverage = new double[17];
//        for (Entry entry : entries) {
//            yearNum[entry.getYearMonth().getYear() - 2004] += 1;
//            yearTotal[entry.getYearMonth().getYear() - 2004] += entry.getScore();
//        }
//        for (int i = 0; i < yearNum.length; i++) {
//            yearAverage[i] = yearTotal[i] / yearNum[i];
//        }
//        int maxYear = 0;
//        for (int i=0; i < yearAverage.length; i++) {
//
//        }

        // Streams - d) Which year has the highest average ranking?
        double averageRanking = entries.stream()
            .collect(Collectors.summarizingInt(Entry::getScore))
                .getAverage();
//        averageRanking.getYearMonth().getYear()
//        .filter(entry -> entry.getYearMonth().getYear())
    }
}
