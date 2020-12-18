import java.util.ArrayList;
import learn.SportsTeam;
public class Exercise14 {

    public static void main(String[] args) {
        ArrayList<SportsTeam> teams = new ArrayList<>();
        teams.add(new SportsTeam("Steelers", "Pittsburgh", "Ben Roethlisberger", 7));
        teams.add(new SportsTeam("Packers", "Green Bay", "Aaron Rodgers", 12));
        teams.add(new SportsTeam("Rams", "L.A.", "Jared Goff", 16));

        for (SportsTeam team : teams) {
            System.out.println(team);
        }

    }

}
