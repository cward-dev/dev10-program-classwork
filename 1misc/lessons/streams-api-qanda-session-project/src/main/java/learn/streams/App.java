package learn.streams;

import learn.streams.data.Repository;
import learn.streams.models.Person;
import learn.streams.models.StateProvince;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        Repository repository = new Repository();
        List<Person> people = repository.getPeople();

//        people.forEach(System.out::println);

        // TODO complete tasks...

        /*
         *
         * [ ] Filter
         * Who has a last name that starts with the letter "a"?
         * Who was born after the year 1990?
         * Who has lived in the state of California?
         *
         * [ ] Sort
         * Order by last name, then first name
         * Order by age
         * Order by the number of states that each person has lived in
         *
         * [ ] Transform
         * Create a list of full names
         * Create a list of the unique birth years
         * Create a list of the unique states
         *
         * [ ] Aggregate
         * How many people have lived in the state of New York?
         * How many people have lived in each state?
         * What are the top 10 birth years?
         *
         */

        printHeader("FILTER", true);

        printHeader("Who has a last name that starts with the letter \"a\"?", false);
            people.stream()
                    .filter(p -> p.getLastName().toLowerCase().charAt(0) == 'a')
                    .forEach(System.out::println);


        printHeader("Who was born after the year 1990?", false);
            people.stream()
                    .filter(p -> p.getBirthDate().getYear() > 1990)
                    .forEach(System.out::println);


        printHeader("Who has lived in the state of California?", false);
            people.stream()
                    .filter(p -> p.getStatesProvinces().contains(new StateProvince("California", "CA")))
                    .forEach(System.out::println);


        printHeader("SORT", true);

        printHeader("Order by last name, then first name", false);
                people.stream()
                        .sorted(Comparator.comparing(Person::getLastName)
                        .thenComparing(Person::getFirstName))
                        .forEach(System.out::println);

        printHeader("Order by age", false);
            people.stream()
                    .sorted(Comparator.comparing(Person::getBirthDate))
                    .forEach(System.out::println);

        printHeader("Order by the number of states that each person has lived in", false);
            people.stream()
                    .sorted(Comparator.comparingInt(a -> a.getStatesProvinces().size()))
                    .forEach(System.out::println);

        printHeader("TRANSFORM", true);

        printHeader("Create a list of full names", false);
            people.stream()
                    .map(p -> p.getFirstName() + " " + p.getLastName())
                    .forEach(System.out::println);

        printHeader("Create a list of the unique birth years", false);
            people.stream()
                    .map(p -> p.getBirthDate().getYear())
                    .distinct()
                    .forEach(System.out::println);

        printHeader("Create a list of the unique states", false);
            people.stream()
                    .flatMap(p -> p.getStatesProvinces().stream())
                    .distinct()
                    .sorted(Comparator.comparing(StateProvince::getName))
                    .forEach(s -> System.out.println(s.getName() + " (" + s.getAbbreviation() + ")"));

        printHeader("AGGREGATE", true);

        printHeader("How many people have lived in the state of New York?", false);
            long peopleLivedInNY = people.stream()
                    .filter(p -> p.getStatesProvinces().stream()
                            .anyMatch(s -> s.getAbbreviation().equalsIgnoreCase("NY")))
                    .count();
        System.out.println("New York: " + peopleLivedInNY);

        printHeader("How many people have lived in each state?", false);
            Map<String, Long> stateCount = people.stream()
                    .flatMap(p -> p.getStatesProvinces().stream())
                    .sorted(Comparator.comparing(StateProvince::getName))
                    .collect(Collectors.groupingBy(StateProvince::getName,
                            Collectors.counting()));

            for (String state : stateCount.keySet()) {
                System.out.println(state + ": " + stateCount.get(state));
            }

        printHeader("What are the top 10 birth years?", false);
            Map<Integer, Long> topTenBirthYears = people.stream()
                    .map(p -> p.getBirthDate().getYear())
                    .collect(Collectors.groupingBy(y -> y, Collectors.counting()));

            topTenBirthYears.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(System.out::println);
    }

    public static void printHeader(String message, boolean mainHeader) {
        System.out.println();
        if (mainHeader) System.out.println("=".repeat(message.length()));
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }
}
