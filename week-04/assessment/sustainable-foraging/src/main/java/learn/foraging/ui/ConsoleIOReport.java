package learn.foraging.ui;

import learn.foraging.domain.Result;
import learn.foraging.models.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ConsoleIOReport {

    public static Result<List<String>> reportKilogramsOfEachItemCollected(Result<Map<Item, Double>> itemsKgCollectedResult) {
        Result<List<String>> result = new Result<>();

        if (!itemsKgCollectedResult.isSuccess()) {
            itemsKgCollectedResult.getErrorMessages().forEach(result::addErrorMessage);
            return result;
        }

        result.setPayload(new ArrayList<>());
        result.getPayload().add(String.format("| %4s| %9s| %14s| %9s |", "ID#", "Category", "Item Name", "Kilograms"));

        itemsKgCollectedResult.getPayload().entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getId()))
                .forEach(e -> result.getPayload().add(String.format("| %4s| %9s| %14s| %7.2fkg |",
                        e.getKey().getId(),
                        e.getKey().getCategory().getName()
                                .substring(0, Math.min(e.getKey().getCategory().getName().length(), 9)),
                        e.getKey().getName()
                                .substring(0, Math.min(e.getKey().getName().length(), 14)),
                        e.getValue())));

        return result;
    }

    public static Result<List<String>> reportTotalValueOfEachCategoryCollected(Result<Map<String, BigDecimal>> valueOfCategoriesCollectedResult) {

        Result<List<String>> result = new Result<>();

        if (!valueOfCategoriesCollectedResult.isSuccess()) {
            valueOfCategoriesCollectedResult.getErrorMessages().forEach(result::addErrorMessage);
            return result;
        }

        result.setPayload(new ArrayList<>());
        result.getPayload().add(String.format("| %33s| %13s |", "Category", "Total Value"));
        valueOfCategoriesCollectedResult.getPayload().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> result.getPayload().add(String.format("| %33s| %13s |",
                        e.getKey(),
                        String.format("$%s", e.getValue().setScale(2, RoundingMode.HALF_EVEN)))));

        return result;
    }

}
