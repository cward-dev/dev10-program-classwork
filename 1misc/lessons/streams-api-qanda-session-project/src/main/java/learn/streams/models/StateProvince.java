package learn.streams.models;

import java.util.Objects;

public class StateProvince {
    private String name;
    private String abbreviation;

    public StateProvince(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateProvince that = (StateProvince) o;
        return name.equals(that.name) &&
                abbreviation.equals(that.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, abbreviation);
    }

    @Override
    public String toString() {
        return "StateProvince{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                '}';
    }
}
