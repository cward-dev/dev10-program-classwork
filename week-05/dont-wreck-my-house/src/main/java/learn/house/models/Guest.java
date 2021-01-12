package learn.house.models;

import java.util.Objects;

public class Guest {

    private int id;
    private String firstName;
    private String lastName;
    String email;
    String phone;
    State state;

    public Guest() {
    }

    public Guest(int id, String firstName, String lastName, String email, String phone, State state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Guest guest = (Guest) o;
        return Objects.equals(email.trim().toLowerCase(), guest.email.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
