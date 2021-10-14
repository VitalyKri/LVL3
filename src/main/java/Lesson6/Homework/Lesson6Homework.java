package Lesson6.Homework;

import java.util.Arrays;

public class Lesson6Homework {

    public static int[] getFilteredArrayByOccurrenceFour(int[] inputArray) {
        int desiredNumber = 4;
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[inputArray.length - 1 - i] == desiredNumber) {
                return Arrays.stream(inputArray).skip(inputArray.length - i).toArray();
            }
        }
        throw new RuntimeException("В массиве отсутствует число 4");
    }

    public static boolean checkArrayWithOneAndFours(int[] inputArray) {

        boolean containsOne = false;
        boolean containsFour = false;
        boolean containsAnother = false;

        for (int i = 0; i < inputArray.length; i++) {
            containsOne = containsOne || inputArray[i] == 1;
            containsFour = containsFour || inputArray[i] == 4;
            containsAnother = containsAnother || (inputArray[i] != 1 && inputArray[i] != 4);
        }
        return !containsAnother && containsFour && containsOne;

    }


}
