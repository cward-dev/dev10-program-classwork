import learn.Vehicle;
import learn.VehicleRepository;

import java.util.HashMap;

public class Exercise05 {

    public static void main(String[] args) {
        HashMap<String, Vehicle> vehicleMap = VehicleRepository.getMap();

        // 1. Instantiate a new HashMap<String, Vehicle>.
        // 2. Add two vehicles to the new map.
        // 3. Add items from the new map to `vehicleMap` using the `putAll` method.
        // 4. Confirm the vehicles were added by retrieving on with its VIN and printing it to the console.
        HashMap<String, Vehicle> newVehicleMap = new HashMap<>();

        newVehicleMap.put("12345678909876543", new Vehicle());
        newVehicleMap.put("09876543212345678", new Vehicle());

        System.out.println(newVehicleMap.get("12345678909876543"));
        System.out.println(newVehicleMap.get("09876543212345678"));
    }
}
