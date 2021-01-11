package learn.foraging.models;

import java.util.Objects;

public class Forager {

    private String id;
    private String firstName;
    private String lastName;
    private State state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forager forager = (Forager) o;
        return Objects.equals(this.getFirstName().trim().toLowerCase(), forager.getFirstName().trim().toLowerCase()) &&
                Objects.equals(this.getLastName().trim().toLowerCase(), forager.getLastName().trim().toLowerCase()) &&
                Objects.equals(this.getState(), forager.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, state);
    }
}
