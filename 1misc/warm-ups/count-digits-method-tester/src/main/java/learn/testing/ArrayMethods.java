package learn.testing;

public class ArrayMethods {

    public static int[] shiftLeft(int[] values) {
        if (values == null) {
            return null;
        }

        int[] result = new int[values.length];

        if (values.length == 0) {
            return result;
        }

        for (int i = 1; i < values.length; i++) {
            result[i - 1] = values[i];
        }
        result[result.length - 1] = values[0];


        return result;
    }

}
