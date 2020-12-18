package learn;

public class SportsTeam {

    private final String name;
    private final String location;
    private String quarterbackName;
    private int quarterbackNumber;

    public SportsTeam(String name, String location, String quarterbackName, int quarterbackNumber) {
        this.name = name;
        this.location = location;
        this.quarterbackName = quarterbackName;
        this.quarterbackNumber = quarterbackNumber;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getQuarterbackName() {
        return quarterbackName;
    }

    public int getQuarterbackNumber() {
        return quarterbackNumber;
    }

    public void setQuarterbackName(String quarterbackName) {
        this.quarterbackName = quarterbackName;
    }

    public void setQuarterbackNumber(int quarterbackNumber) {
        this.quarterbackNumber = quarterbackNumber;
    }

    @Override
    public String toString() {
        return location + " " + name + ". Quarterback: " + quarterbackName + ", #" + quarterbackNumber ;
    }
}
