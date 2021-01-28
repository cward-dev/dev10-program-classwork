package learn.streams.data;

import com.github.javafaker.Faker;
import learn.streams.models.Person;
import learn.streams.models.StateProvince;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Repository {
    private final Random random = new Random(100);
    private final Faker faker = new Faker(random);

    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();
        Map<String, StateProvince> statesProvincesMap = getStatesProvinces();

        // Generate 100 people...
        for (int i = 0; i < 100; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            LocalDate birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // use random to select 1-5 states for each person
            List<StateProvince> statesProvinces = new ArrayList<>();
            int numberOfStatesProvinces = random.nextInt(5) + 1;
            for (int stateNumber = 0; stateNumber < numberOfStatesProvinces; stateNumber++) {
                String stateAbbreviation = faker.address().stateAbbr();
                statesProvinces.add(statesProvincesMap.get(stateAbbreviation));
            }

            Person person = new Person(firstName, lastName, birthDate, statesProvinces);

            people.add(person);
        }

        return people;
    }

    private Map<String, StateProvince> getStatesProvinces() {
        Map<String, StateProvince> statesProvinces = new HashMap<>();

        statesProvinces.put("AL", new StateProvince("Alabama", "AL"));
        statesProvinces.put("AK", new StateProvince("Alaska", "AK"));
        statesProvinces.put("AZ", new StateProvince("Arizona", "AZ"));
        statesProvinces.put("AR", new StateProvince("Arkansas", "AR"));
        statesProvinces.put("CA", new StateProvince("California", "CA"));
        statesProvinces.put("CO", new StateProvince("Colorado", "CO"));
        statesProvinces.put("CT", new StateProvince("Connecticut", "CT"));
        statesProvinces.put("DE", new StateProvince("Delaware", "DE"));
        statesProvinces.put("FL", new StateProvince("Florida", "FL"));
        statesProvinces.put("GA", new StateProvince("Georgia", "GA"));
        statesProvinces.put("HI", new StateProvince("Hawaii", "HI"));
        statesProvinces.put("ID", new StateProvince("Idaho", "ID"));
        statesProvinces.put("IL", new StateProvince("Illinois", "IL"));
        statesProvinces.put("IN", new StateProvince("Indiana", "IN"));
        statesProvinces.put("IA", new StateProvince("Iowa", "IA"));
        statesProvinces.put("KS", new StateProvince("Kansas", "KS"));
        statesProvinces.put("KY", new StateProvince("Kentucky", "KY"));
        statesProvinces.put("LA", new StateProvince("Louisiana", "LA"));
        statesProvinces.put("ME", new StateProvince("Maine", "ME"));
        statesProvinces.put("MD", new StateProvince("Maryland", "MD"));
        statesProvinces.put("MA", new StateProvince("Massachusetts", "MA"));
        statesProvinces.put("MI", new StateProvince("Michigan", "MI"));
        statesProvinces.put("MN", new StateProvince("Minnesota", "MN"));
        statesProvinces.put("MS", new StateProvince("Mississippi", "MS"));
        statesProvinces.put("MO", new StateProvince("Missouri", "MO"));
        statesProvinces.put("MT", new StateProvince("Montana", "MT"));
        statesProvinces.put("NE", new StateProvince("Nebraska", "NE"));
        statesProvinces.put("NV", new StateProvince("Nevada", "NV"));
        statesProvinces.put("NH", new StateProvince("New Hampshire", "NH"));
        statesProvinces.put("NJ", new StateProvince("New Jersey", "NJ"));
        statesProvinces.put("NM", new StateProvince("New Mexico", "NM"));
        statesProvinces.put("NY", new StateProvince("New York", "NY"));
        statesProvinces.put("NC", new StateProvince("North Carolina", "NC"));
        statesProvinces.put("ND", new StateProvince("North Dakota", "ND"));
        statesProvinces.put("OH", new StateProvince("Ohio", "OH"));
        statesProvinces.put("OK", new StateProvince("Oklahoma", "OK"));
        statesProvinces.put("OR", new StateProvince("Oregon", "OR"));
        statesProvinces.put("PA", new StateProvince("Pennsylvania", "PA"));
        statesProvinces.put("RI", new StateProvince("Rhode Island", "RI"));
        statesProvinces.put("SC", new StateProvince("South Carolina", "SC"));
        statesProvinces.put("SD", new StateProvince("South Dakota", "SD"));
        statesProvinces.put("TN", new StateProvince("Tennessee", "TN"));
        statesProvinces.put("TX", new StateProvince("Texas", "TX"));
        statesProvinces.put("UT", new StateProvince("Utah", "UT"));
        statesProvinces.put("VT", new StateProvince("Vermont", "VT"));
        statesProvinces.put("VA", new StateProvince("Virginia", "VA"));
        statesProvinces.put("WA", new StateProvince("Washington", "WA"));
        statesProvinces.put("WV", new StateProvince("West Virginia", "WV"));
        statesProvinces.put("WI", new StateProvince("Wisconsin", "WI"));
        statesProvinces.put("WY", new StateProvince("Wyoming", "WY"));

        return statesProvinces;
    }
}
