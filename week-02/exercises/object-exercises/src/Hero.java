public class Hero {

    private String name;
    private Power[] powers;

    public Hero(String name, Power[] powers) {
        this.name = name;
        this.powers = powers;
    }

    // getters
    public String getName() {
        return name;
    }

    public Power[] getPowers() {
        return powers;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPowers(Power[] powers) {
        this.powers = powers;
    }

    public String toLine() {
        String result = getName() + ": ";

        String delimiter = "";
        for (Power p : getPowers()) {
            result += (delimiter);
            delimiter = ", ";
            result += p.getName();
        }

        return result;
    }

}