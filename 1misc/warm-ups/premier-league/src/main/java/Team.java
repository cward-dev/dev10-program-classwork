public class Team {

    int id;
    String name;
    int gamesPlayed;
    int wins;
    int losses;
    int draws;
    int pointsFor;
    int pointsAgainst;
    int pts;
    int differential;

    public int getId() {
        return id;
    }

    public int getDifferential() {
        return differential;
    }

    public void setDifferential(int differential) {
        this.differential = differential;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(int pointsFor) {
        this.pointsFor = pointsFor;
    }

    public int getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(int pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int calculatePointDifferential() {
        return Math.abs(pointsFor - pointsAgainst);
    }
}
