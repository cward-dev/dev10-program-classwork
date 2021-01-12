package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.State;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    private List<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(new Guest(1, "Sullivan", "Lomas",
                "slomas0@mediafire.com", "(702) 7768761", State.NEVADA));
        guests.add(new Guest(2, "Olympie", "Gecks",
                "ogecks1@dagondesign.com", "(202) 2528316", State.WASHINGTON_DC));
        guests.add(new Guest(3, "Tremain", "Carncross",
                "tcarncross2@japanpost.jp", "(313) 2245034", State.MICHIGAN));
        guests.add(new Guest(4, "Leonidas", "Gueny",
                "lgueny3@example.com", "(412) 6493981", State.PENNSYLVANIA));
        guests.add(new Guest(5, "Berta", "Seppey",
                "bseppey4@yahoo.com", "(202) 2668098", State.WASHINGTON_DC));
    }

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findById(int id) {
        return guests.stream()
                .filter(guest -> guest.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findByEmail(String email) {
        return guests.stream()
                .filter(guest -> guest.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest add(Guest guest) {
        guest.setId(getNextId());
        guests.add(guest);
        return guest;
    }

    private int getNextId() {
        return guests.stream()
                .mapToInt(Guest::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public boolean update(Guest guest) {
        for (int i = 0; i < guests.size(); i++) {
            if (guest.getId() == guests.get(i).getId()) {
                guests.set(i, guest);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) {
        for (int i = 0; i < guests.size(); i++) {
            if (id == guests.get(i).getId()) {
                guests.remove(i);
                return true;
            }
        }

        return false;
    }
}
