import learn.Vehicle;
import learn.VehicleRepository;

import java.util.HashMap;

public class Exercise07 {

    public static void main(String[] args) {
        HashMap<String, Vehicle> vehicleMap = VehicleRepository.getMap();

        // 1. How many vehicles are Pink (ignore case)?
        // Expected: 54

        int countPink = 0;

        for (String vin : vehicleMap.keySet()) {
            Vehicle vehicle = vehicleMap.get(vin);
            if (vehicle.getColor().equalsIgnoreCase("pink")) {
                    countPink++;
            }
        }

        System.out.println(countPink);
    }
}
