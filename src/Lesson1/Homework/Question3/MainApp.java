package Lesson1.Homework.Question3;

public class MainApp {
    public static void main(String[] args) {

        Box<Apple> appleBox= new Box<>(new Apple(),new Apple(),new Apple());
        Box<Apple> appleBox2= new Box<>(new Apple(),new Apple(),new Apple());
        Box<Orange> orangeBox= new Box<>(new Orange(),new Orange(),new Orange());
        System.out.println(appleBox.getWeight() +"кг.");
        System.out.println(orangeBox.getWeight() +"кг.");
        System.out.println(appleBox.compare(appleBox2));
        System.out.println(appleBox.compare(orangeBox));
        appleBox2.addFruit(new Apple());
        appleBox.addFruit(appleBox2);
        System.out.println(appleBox.getWeight());
        System.out.println(orangeBox.compare(appleBox));
    }
}
