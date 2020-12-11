public class Exercise02 {

    public static void main(String[] args) {

        String[] tenFavoriteFoods = new String[10]; // space for 10 favorite foods

        tenFavoriteFoods[5] = "squid ink";
        System.out.println(tenFavoriteFoods[5]);
        System.out.println(tenFavoriteFoods[6]);

        // 1. Set your 10 favorite foods. (It's okay to replace squid ink.)
        // 2. Print your top, 6th, and last favorite from tenFavoriteFoods.

        tenFavoriteFoods[0] = "street tacos";
        tenFavoriteFoods[1] = "steak";
        tenFavoriteFoods[2] = "pasta";
        tenFavoriteFoods[3] = "chili";
        tenFavoriteFoods[4] = "nachos";
        tenFavoriteFoods[5] = "fried chicken sammies";
        tenFavoriteFoods[6] = "jambalaya";
        tenFavoriteFoods[7] = "bratwurst";
        tenFavoriteFoods[8] = "burgers";
        tenFavoriteFoods[9] = "mac n cheese";

        System.out.println(tenFavoriteFoods[0]);
        System.out.println(tenFavoriteFoods[5]);
        System.out.println(tenFavoriteFoods[9]);
    }
}
