public class Exercise11 {

    public static void main(String[] args) {
        // MANAGER FEATURE REQUESTS
        // You have three managers: A, B, and C.
        // Occasionally, they ask you to add features to your company software.
        // Use if/else statements to enforce the following rules:
        // - If all three ask for the feature, print "Feature in progress."
        // - If any two of the three ask, print "Adding feature to schedule."
        // - If only one of the three ask, print "Going to hold off for a bit."
        // - If none of the managers ask, print "Nothing to do..."

        boolean managerAAsked = false;
        boolean managerBAsked = true;
        boolean managerCAsked = false;
        int count = 0;

        // 1. Add decisions statements to cover all scenarios.
        // 2. Change manager variables to test all scenarios.

        if (managerAAsked) {
            count++;
        }
        if (managerBAsked) {
            count++;
        }
        if (managerCAsked) {
            count++;
        }

        if (count == 0) {
            System.out.println("Nothing to do...");
        } else if (count == 1) {
            System.out.println("Going to hold off for a bit.");
        } else if (count == 2) {
            System.out.println("Adding feature to schedule.");
        } else if (count == 3) {
            System.out.println("Feature in progress.");
        }
    }
}
