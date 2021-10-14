package Lesson6.Homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class Lesson6HomeworkTest {

    @ParameterizedTest
    @MethodSource("valueForFilteringArrayByOccurrenceFour")
    void shouldReturnCorrectFilteredArrayByOccurrenceFour(int[] expected,int[] actual) {
        int[] returnedArray = Lesson6Homework.getFilteredArrayByOccurrenceFour(actual);
        boolean arraysEquals =Arrays.equals(expected,
                returnedArray);
        Assertions.assertTrue(arraysEquals,"extectedArray =" + Arrays.toString(expected)+
                "\n actualArray =" + Arrays.toString(actual)+
                "\n returnedArray =" + Arrays.toString(returnedArray));
    }

    private static Stream<Arguments> valueForFilteringArrayByOccurrenceFour(){
        return Stream.of(
                Arguments.of(new int[]{5},new int[]{1, 2, 3, 4,5}),
                Arguments.of(new int[]{1,2,3,4,5},new int[]{1, 2, 3, 4,5}),
                Arguments.of(new int[]{5},new int[]{1,2,3,5}),
                Arguments.of(new int[]{5,6},new int[]{1,2,3,4,5,6,1,2,3,4,5,6})
        );
    }

    @ParameterizedTest
    @MethodSource("valuesForCheckArrayWithOnesAndFours")
    void shouldCorrectCheckArrayWithOnesAndFours(boolean expected, int[] actual ){
        Assertions.assertEquals(expected,Lesson6Homework.checkArrayWithOneAndFours(actual),"actualArray = "+Arrays.toString(actual)+
                "\n return value =" + (!expected));
    }

    private static Stream<Arguments> valuesForCheckArrayWithOnesAndFours() {
        return Stream.of(
                Arguments.of(true,new int[]{1,4,1,4,4,1}),
                Arguments.of(false,new int[]{1,1,1,1,1,1}),
                Arguments.of(false,new int[]{4,4,4,4,4}),
                Arguments.of(false,new int[]{}),
                Arguments.of(false,new int[]{1,2,3,4,5})
        );
    }


}
