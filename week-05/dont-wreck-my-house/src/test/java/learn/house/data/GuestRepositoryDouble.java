package learn.house.data;

import learn.house.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    private List<Guest> guests = new ArrayList<>();

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
    public Guest add(Guest guest) throws DataException {
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
    public boolean update(Guest guest) throws DataException {
        for (int i = 0; i < guests.size(); i++) {
            if (guest.getId() == guests.get(i).getId()) {
                guests.set(i, guest);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        for (int i = 0; i < guests.size(); i++) {
            if (id == guests.get(i).getId()) {
                guests.remove(i);
                return true;
            }
        }

        return false;
    }
}
