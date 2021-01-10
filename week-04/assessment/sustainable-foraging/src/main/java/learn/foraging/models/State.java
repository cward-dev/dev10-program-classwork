package learn.foraging.models;

import java.util.HashMap;
import java.util.Map;

public enum State {

    ALABAMA("ALABAMA", "AL"),
    ALASKA("ALASKA", "AK"),
    ARIZONA("ARIZONA", "AZ"),
    ARKANSAS("ARKANSAS", "AR"),
    CALIFORNIA("CALIFORNIA", "CA"),
    COLORADO("COLORADO", "CO"),
    CONNECTICUT("CONNECTICUT", "CT"),
    DELAWARE("DELAWARE", "DE"),
    FLORIDA("FLORIDA", "FL"),
    GEORGIA("GEORGIA", "GA"),
    HAWAII("HAWAII", "HI"),
    IDAHO("IDAHO", "ID"),
    ILLINOIS("ILLINOIS", "IL"),
    INDIANA("INDIANA", "IN"),
    IOWA("IOWA", "IA"),
    KANSAS("KANSAS", "KS"),
    KENTUCKY("KENTUCKY", "KY"),
    LOUISIANA("LOUISIANA", "LA"),
    MAINE("MAINE", "ME"),
    MARYLAND("MARYLAND", "MD"),
    MASSACHUSETTS("MASSACHUSETTS", "MA"),
    MICHIGAN("MICHIGAN", "MI"),
    MINNESOTA("MINNESOTA", "MN"),
    MISSISSIPPI("MISSISSIPPI", "MS"),
    MISSOURI("MISSOURI", "MO"),
    MONTANA("MONTANA", "MT"),
    NEBRASKA("NEBRASKA", "NE"),
    NEVADA("NEVADA", "NV"),
    NEW_HAMPSHIRE("NEW_HAMPSHIRE", "NH"),
    NEW_JERSEY("NEW_JERSEY", "NJ"),
    NEW_MEXICO("NEW_MEXICO", "NM"),
    NEW_YORK("NEW_YORK", "NY"),
    NORTH_CAROLINA("NORTH CAROLINA", "NC"),
    NORTH_DAKOTA("NORTH DAKOTA", "ND"),
    OHIO("OHIO", "OH"),
    OKLAHOMA("OKLAHOMA", "OK"),
    OREGON("OREGON", "OR"),
    PENNSYLVANIA("PENNSYLVANIA", "PA"),
    RHODE_ISLAND("RHODE ISLAND", "RI"),
    SOUTH_CAROLINA("SOUTH CAROLINA", "SC"),
    SOUTH_DAKOTA("SOUTH DAKOTA", "SD"),
    TENNESSEE("TENNESSEE", "TN"),
    TEXAS("TEXAS", "TX"),
    UTAH("UTAH", "UT"),
    VERMONT("VERMONT", "VT"),
    VIRGINIA("VIRGINIA", "VA"),
    WASHINGTON("WASHINGTON", "WA"),
    WASHINGTON_DC("WASHINGTON DC", "DC"),
    WEST_VIRGINIA("WEST VIRGINIA", "WV"),
    WISCONSIN("WISCONSIN", "WI"),
    WYOMING("WYOMING", "WY");

    private final String name;
    private final String abbreviation;

    State(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static State getStateFromAbbreviation(String abbreviation) {
        Map<String, State> stateKey = new HashMap<>();

        for (State s : values()) stateKey.put(s.abbreviation, s);

        return stateKey.get(abbreviation.toUpperCase());
    }

    public static State getStateFromName(String stateName) {
        Map<String, State> stateKey = new HashMap<>();

        for (State s : values()) stateKey.put(s.name, s);

        return stateKey.get(stateName.toUpperCase());
    }
}
