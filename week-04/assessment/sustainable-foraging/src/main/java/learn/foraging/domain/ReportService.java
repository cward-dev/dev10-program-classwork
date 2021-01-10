package learn.foraging.domain;

import learn.foraging.models.Forage;
import learn.foraging.models.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    public List<String> reportKilogramsOfEachItemCollected(List<Forage> forages, LocalDate date) {
        Map<Item, Double> itemsKgCollected = getKilogramsOfEachItemCollected(forages, date);

        List<String> reportLines = new ArrayList<>();
        reportLines.add(String.format("| %4s| %9s| %14s| %9s |", "ID#", "Category", "Item Name", "Kilograms"));

        itemsKgCollected.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getId()))
                .forEach(e -> reportLines.add(String.format("| %4s| %9s| %14s| %7.2fkg |",
                        e.getKey().getId(),
                        e.getKey().getCategory().getName()
                                .substring(0, Math.min(e.getKey().getCategory().getName().length(), 9)),
                        e.getKey().getName()
                                .substring(0, Math.min(e.getKey().getName().length(), 14)),
                        e.getValue())));

        return reportLines;
    }

    public List<String> reportTotalValueOfEachCategoryCollected(List<Forage> forages, LocalDate date) {
        Map<String, BigDecimal> valueOfCategoriesCollected = getTotalValueOfEachCategoryCollected(forages, date);

        List<String> reportLines = new ArrayList<>();

        reportLines.add(String.format("| %33s| %13s |", "Category", "Total Value"));
        valueOfCategoriesCollected.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> reportLines.add(String.format("| %33s| %13s |",
                        e.getKey(),
                        String.format("$%s", e.getValue().setScale(2, RoundingMode.HALF_EVEN)))));

        return reportLines;
    }

    public Map<Item, Double> getKilogramsOfEachItemCollected(List<Forage> forages, LocalDate date) {
        return forages.stream()
                .filter(f -> f.getDate().equals(date))
                .sorted(Comparator.comparing(f -> f.getItem().getId()))
                .collect(Collectors.groupingBy(Forage::getItem, Collectors.summingDouble(Forage::getKilograms)));
    }

    public Map<String, BigDecimal> getTotalValueOfEachCategoryCollected(List<Forage> forages, LocalDate date) {
        return getKilogramsOfEachItemCollected(forages, date).entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getKey().getCategory().getName(), Collectors.reducing(
                        BigDecimal.ZERO,
                        e -> e.getKey().getDollarPerKilogram().multiply(new BigDecimal(e.getValue())),
                        BigDecimal::add)));
    }
}
