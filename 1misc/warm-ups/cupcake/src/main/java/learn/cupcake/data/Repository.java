package learn.cupcake.data;

import learn.cupcake.models.Entry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final String filePath;

    public Repository(String filePath) {
        this.filePath = filePath;
    }

    public List<Entry> getEntries() {
        ArrayList<Entry> entries = new ArrayList<>();
        CSVFormat format = CSVFormat.RFC4180
                .withHeader(
                        "Month",
                        "Cupcake")
                .withSkipHeaderRecord(true)
                .withIgnoreSurroundingSpaces(true);

        try (CSVParser parser = CSVParser.parse(new FileReader(filePath), format)) {
            for (CSVRecord csvRecord : parser) {
                Entry entry = new Entry(
                        YearMonth.parse(csvRecord.get(0)),
                        Integer.parseInt(csvRecord.get(1))
                );
                entries.add(entry);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }
}
