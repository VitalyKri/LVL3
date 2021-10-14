package Lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MathOperationTest {

    @Test
    void shouldSumSuccessfillyWhenToValidDigitsPassed(){
        Assertions.assertEquals(3,MathOperation.sum(1,3),()-> "Calculation was not correct.");
        Assertions.fail();
    }

    @ParameterizedTest
    @MethodSource("valuesForSuccessfulSumOperationProvider")
    void testSumSuccessfillyCalculationWhenTwoValidDigitsPassed(double expected, double arg1,double arg2){
        Assertions.assertEquals(expected,MathOperation.sum(arg1,arg2),()-> "Calculation was not correct.");
    }

    // Аргумент сценарий, 1 проверка, 2 параметр1, 3 параметр2
    private static Stream<Arguments> valuesForSuccessfulSumOperationProvider(){
        return Stream.of(
                Arguments.of(5,2,3),
                Arguments.of(-1,-2,-3),
                Arguments.of(100,-2,-3)
        );
    }

    @Test
    void testSumSuccessfillyCalculationWhenTwoValidDigitsPassed(){}

    @Test
    // название должно соответствовать результату.
    void shouldThrowArithmeticExceptionWhenSecondArgumentZero(){
        Assertions.assertThrows(ArithmeticException.class,()-> MathOperation.divided(1,0));
    }


}
