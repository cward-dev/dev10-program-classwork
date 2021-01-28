import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FootballRepo {

    private final String filePath;

    public FootballRepo(String filePath) {
        this.filePath = filePath;
    }

    public List<Team> findAll() {
        List<Team> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.contains("---")) {
                    return result;
                }
                Team team = deserialize(line);
                result.add(team);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Team> findAllEliminated() {
        List<Team> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String fileLine;
            do {
                fileLine = reader.readLine();
            } while (!fileLine.contains("---"));

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Team team = deserialize(line);
                result.add(team);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Team deserialize(String line) {
        Team team = new Team();
        //Team (7-22)|Games Played 22-28|Wins 29-33|
        team.setId(Integer.parseInt(line.substring(0,5).trim()));
        team.setName(line.substring(7,22).trim());
        team.setGamesPlayed(Integer.parseInt(line.substring(23,28).trim()));
        team.setWins(Integer.parseInt(line.substring(29,32).trim()));
        team.setLosses(Integer.parseInt(line.substring(33,36).trim()));
        team.setDraws(Integer.parseInt(line.substring(37,42).trim()));
        team.setPointsFor(Integer.parseInt(line.substring(43,46).trim()));
        team.setPointsAgainst(Integer.parseInt(line.substring(50,55).trim()));
        team.setPts(Integer.parseInt(line.substring(56).trim()));
        team.setDifferential(team.calculatePointDifferential());

        return team;
    }
}
