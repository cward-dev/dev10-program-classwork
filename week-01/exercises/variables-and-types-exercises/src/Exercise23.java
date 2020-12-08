public class Exercise23 {

    public static void main(String[] args) {

        String colors = "red orange yellow green blue indigo violet";
        System.out.println(colors.substring(0, 3));

        // 1. Use the substring method to print each color on its own line.
        // "red" is already complete.

        // Expected Output:
        // red
        // orange
        // yellow
        // green
        // blue
        // indigo
        // violet

        System.out.println(colors.substring(4, 10));
        System.out.println(colors.substring(11, 17));
        System.out.println(colors.substring(18, 23));
        System.out.println(colors.substring(24, 28));
        System.out.println(colors.substring(29, 35));
        System.out.println(colors.substring(36, 42));

    }
}
