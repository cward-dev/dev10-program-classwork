package learn.foraging.domain;

import learn.foraging.data.ForageRepository;
import learn.foraging.data.ForagerRepository;
import learn.foraging.data.ItemRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ForageRepository forageRepository;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    public ReportService(ForageRepository forageRepository) {
        this.forageRepository = forageRepository;
    }

    public Result<Map<Item, Double>> getKilogramsOfEachItemCollected(LocalDate date) {
        List<Forage> forages = forageRepository.findByDate(date);

        Result<Map<Item, Double>> result = new Result<>();

        if (forages == null || forages.size() == 0) {
            result.addErrorMessage(String.format("No forages are recorded for %s.", date.format(dateFormatter)));
            return result;
        }

        result.setPayload(forages.stream()
                .filter(f -> f.getDate().equals(date))
                .sorted(Comparator.comparing(f -> f.getItem().getId()))
                .collect(Collectors.groupingBy(Forage::getItem, Collectors.summingDouble(Forage::getKilograms))));

        return result;
    }

    public Result<Map<String, BigDecimal>> getTotalValueOfEachCategoryCollected(LocalDate date) {
        Result<Map<Item, Double>> itemsAndKgCollectedResult = getKilogramsOfEachItemCollected(date);

        Result<Map<String, BigDecimal>> result = new Result<>();

        if (!itemsAndKgCollectedResult.isSuccess()) {
            itemsAndKgCollectedResult.getErrorMessages().forEach(result::addErrorMessage);
            return result;
        }

        result.setPayload(itemsAndKgCollectedResult.getPayload().entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getKey().getCategory().getName(), Collectors.reducing(
                        BigDecimal.ZERO,
                        e -> e.getKey().getDollarPerKilogram().multiply(new BigDecimal(e.getValue())),
                        BigDecimal::add))));

        return result;
    }
}
