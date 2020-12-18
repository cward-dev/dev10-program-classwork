import learn.Vehicle;
import learn.VehicleRepository;

import java.util.HashMap;
import java.util.Map.Entry;

public class Exercise06 {

    public static void main(String[] args) {
        HashMap<String, Vehicle> vehicleMap = VehicleRepository.getMap();

        // 1. Loop over each vehicle in `vehicleMap` and print vehicles with a Dodge make.
        // 2. Loop three times with three different techniques: .values(), .entrySet(), and .keySet().

        for (Vehicle vehicle : vehicleMap.values()) {
            if (vehicle.getMake().equals("Dodge")) {
                System.out.println(vehicle);
            }
        }
        System.out.println();
        for (Entry<String, Vehicle> entry : vehicleMap.entrySet()) {
            if (entry.getValue().getMake().equals("Dodge")) {
                System.out.println(entry.getValue());
            }
        }
        System.out.println();
        for (String vin : vehicleMap.keySet()) {
            if (vehicleMap.get(vin).getMake().equals("Dodge")) {
                System.out.println(vehicleMap.get(vin));
            }
        }
    }
}
