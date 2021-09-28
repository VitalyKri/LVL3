package Lesson1;

import Lesson1.transmitter.Package;
import Lesson1.transmitter.Transmitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


public class Generic {
    public static void main(String[] args) {

    }

    public static void doGenericUsageDemoThree(String[] args) {

        Package<String> stringPackage = () -> "hello, world";
        Package<StringBuilder> integerPackage = () -> new StringBuilder("hello, world");

        Transmitter.convertToRow(new RandomPackage());
        Transmitter.convertToRow1(stringPackage);
    }

    public static void doGenericUsageDemoTwo(String[] args) {

        Package<String> stringPackage = () -> "hello, world";
        Package<Integer> integerPackage = () -> 1;

        Transmitter<Package<String>> stringTransmitter1 = new Transmitter<>(stringPackage);
        Transmitter<Package<Integer>> stringTransmitter2 = new Transmitter<>(integerPackage);
        // Это ошибка. интежер не наследует от Package
        //Transmitter<Integer> stringTransmitter2 = new Transmitter<>(integerPackage);
        Transmitter<RandomPackage> randomPackageTransmitter = new Transmitter<>(new RandomPackage());
        List<Package<String>> packages = Transmitter.convertToRow(stringPackage);
        List<Package<Integer>> packages1 = Transmitter.convertToRow(integerPackage);
    }
    static class RandomPackage implements Package<Random> {

        @Override
        public Random getValue() {
            return new Random();
        }
    }

    public static void doGenericUsageDemoOne(String[] args) {
        Container<Integer> intContainer1 = new Container<>(1);
        Container<Integer> intContainer2 = new Container<>(10);
        // Это не приметивы. а объекты. не приводятся Long в Integer;
        int sum = intContainer1.getValue() + intContainer2.getValue();
        System.out.println(sum);


    }

    public static void doGeneric(String[] args) {

    }
}
