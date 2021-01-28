import java.util.ArrayList;
import java.util.List;

import java.util.function.Predicate;

public class App {

    public static void main(String[] args) {
        List<Address> addresses = getAddresses();

        List<Address> addressesByCity = filter(addresses, a -> a.getCity().equalsIgnoreCase("Roanoke"));
        if (addressesByCity != null) System.out.println(addressesByCity.get(0).getCity());
        List<Address> addressesByState = filter(addresses, a -> a.getState().equals("VA"));
        if (addressesByState != null) System.out.println(addressesByCity.get(0).getState());
        List<Address> addressesByPostalCode = filter(addresses, a -> a.getPostalCode().equals("55555"));
        if (addressesByPostalCode != null) System.out.println(addressesByCity.get(0).getPostalCode());
        List<Address> addressesByStateAndStreetName = filter(addresses, a -> a.getState().equals("HI")
                && a.getStreet().contains("Junction"));
        if (addressesByStateAndStreetName != null) System.out.println(addressesByCity.get(0).getState() + " " + addressesByCity.get(0).getPostalCode());

    }

    private static List<Address> getAddresses() {
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address("1111 North Junction", "Roanoke", "VA", "55555"));
        addresses.add(new Address("1112 North Junction", "Roanoke", "VA", "55555"));
        addresses.add(new Address("1113 North Junction", "Roanoke", "VA", "55555"));
        addresses.add(new Address("1114 North Junction", "Roanoke", "VA", "55555"));
        addresses.add(new Address("1115 North Junction", "Roanoke", "VA", "55555"));
        return addresses;
    }

    static List<Address> filter(List<Address> source, Predicate<Address> criteria) {
        ArrayList<Address> result = new ArrayList<>();
        for (Address a : source) {
            if (criteria.test(a)) {
                result.add(a);
            }
        }
        return result;
    }
}
