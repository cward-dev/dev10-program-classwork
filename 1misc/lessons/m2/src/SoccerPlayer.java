public class SoccerPlayer {

    private final int number;
    private final String firstName;
    private final String lastName;
    private final String position;

    public SoccerPlayer(int number, String firstName, String lastName, String position) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object value) {
        // 1. check reference equality
        if (this == value) {
            return true;
        }
        // 2. Are they the same type?
        if (value == null || getClass() != value.getClass()) {
            return false;
        }

        // The types are the same. It's safe to cast value as a SoccerPlayer.
        SoccerPlayer other = (SoccerPlayer) value;
        // 3. check jersey number
        if (number != other.number) {
            return false;
        }
        // 4. check first name
        // Objects.equals is a utility method that checks equality, including null checks.
        // It makes our code a little clearer.
        if (!firstName.equals(other.firstName)) {
            return false;
        }

        // 5. check last name
        if (!lastName.equals(other.lastName)) {
            return false;
        }

        // 6. check position
        if (!position.equals(other.position)) {
            return false;
        }

        // 7. We made it! These objects are equal.
        return true;
    }

}