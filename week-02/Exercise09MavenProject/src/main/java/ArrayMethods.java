public class ArrayMethods {

    public static int[] removeZero(int[] values) {
        if (values == null) {
            return null;
        }

        int count = 0;
        for (int value : values) {
            if (value != 0) {
                count++;
            }
        }

        int[] newValues = new int[count];
        int nextIndex = 0;

        for (int value : values) {
            if (value != 0) {
                newValues[nextIndex] = value;
                nextIndex++;
            }
        }

        return newValues;
    }

}
