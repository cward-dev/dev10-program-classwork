import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {

        FootballRepo repo = new FootballRepo("./data/football.dat");

        int lowestPoints = repo.findAll().stream()
                .map(Team::getPts)
                .min(Comparator.comparing(i -> i))
                .orElse(0);

        List<Team> teams = repo.findAll().stream()
                .sorted(Comparator.comparing(Team::getPts).reversed())
                .filter(t -> t.getPts() == lowestPoints)
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());

        System.out.println("Teams with lowest number of points above cutoff:");
        teams.forEach(t -> System.out.println(t.getName()));

        System.out.println();

        List<Team> allTeams = repo.findAll();
        allTeams.addAll(repo.findAllEliminated());

        Team lowestDiffTeam = allTeams.stream()
                .min(Comparator.comparing(Team::getDifferential))
                .orElse(null);

        System.out.println("Team with the lowest point differential:");
        System.out.println(lowestDiffTeam.getName());

    }



}
