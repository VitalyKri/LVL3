package Lesson1.Homework;

public class MainApp {

    public static void main(String[] args) {
        Question1<String> stringQuestion1 = new Question1<>(new String[]{"1","2","3","4"});
        stringQuestion1.swapElements(1,2);
        stringQuestion1.info();

        Question2<String> stringQuestion2 = new Question2<>(new String[]{"1","2","3","4"});
        System.out.println(stringQuestion2.getArrayAsList());
    }
}
