import java.util.Scanner;

public class Exercise10 {

    public static void main(String[] args) {
        // USPS
        // Below is an abbreviated version of the US Postal Service retail parcel rates:
        /*
        Lbs | Zones 1&2 | Zone 3
        ===============
        1      $7.50      $7.85
        2       8.25       8.70
        3       8.70       9.70
        4       9.20      10.55
        5      10.20      11.30
        */

        // 1. Collect the parcel lbs and zone (1, 2, or 3) from the user.
        // 2. Add `if`/`else if`/`else` logic to cover all rates.
        // Use whatever strategy you think is best. You can create compound conditions or nest if/else statements.
        // If a lbs/zone combo does not exist, print a warning message for the user.

        Scanner console = new Scanner(System.in);

        System.out.print("Zone (1-3): ");
        int zone = console.nextInt();
        System.out.print("Parcel Weight in Lbs (1-5): ");
        int parcelWeight = console.nextInt();
        float rate = 0.00f;

        if (zone == 1 || zone == 2) {
            if (parcelWeight == 1) {
                rate = 7.50f;
            } else if (parcelWeight == 2) {
                rate = 8.25f;
            } else if (parcelWeight == 3) {
                rate = 8.70f;
            } else if (parcelWeight == 4) {
                rate = 9.20f;
            } else if (parcelWeight == 5) {
                rate = 10.20f;
            }
        } else if (zone == 3) {
            if (parcelWeight == 1) {
                rate = 7.50f;
            } else if (parcelWeight == 2) {
                rate = 8.25f;
            } else if (parcelWeight == 3) {
                rate = 8.70f;
            } else if (parcelWeight == 4) {
                rate = 9.20f;
            } else if (parcelWeight == 5) {
                rate = 10.20f;
            }
        }

        if (zone > 3 || zone < 1 || parcelWeight > 5 || parcelWeight < 1) {
            System.out.println("Warning: That zone/weight combination does not exist. Please restart program.");
        } else {
            System.out.printf("Your rate: $%.2f", rate);
        }

        System.out.println();
    }
}