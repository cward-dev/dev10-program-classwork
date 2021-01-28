package learn.streams.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<StateProvince> statesProvinces;

    public Person(String firstName, String lastName, LocalDate birthDate, StateProvince... statesProvinces) {
        this(firstName, lastName, birthDate, List.of(statesProvinces));
    }

    public Person(String firstName, String lastName, LocalDate birthDate, List<StateProvince> statesProvinces) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.statesProvinces = statesProvinces;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<StateProvince> getStatesProvinces() {
        return statesProvinces;
    }

    public void setStatesProvinces(List<StateProvince> statesProvinces) {
        this.statesProvinces = statesProvinces;
    }

    public StateProvince getCurrentStateProvince() {
        return (statesProvinces != null && statesProvinces.size() > 0) ? statesProvinces.get(0) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                birthDate.equals(person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", statesProvinces=" + statesProvinces +
                '}';
    }
}
