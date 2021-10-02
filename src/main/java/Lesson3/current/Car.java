package Lesson3.current;

import java.io.Serializable;

// интерфейс присоединить сериализирующийся
public class Car implements Serializable {
    int power;
    int speed;

    public Car(int power, int speed) {
        this.power = power;
        this.speed = speed;
    }

    public static void main(String[] args) {
        Car car = new Car(1,2);
        byte[] bytes = Serializator.perform(car);
        System.out.println("data transmitting");
        Car receivedCar = CarDeserializator.perform(bytes);
        System.out.println("Refs equils =" +(car == receivedCar));
        System.out.println("Alter:" +receivedCar);

    }

    @Override
    public String toString() {
        return "Car{"+
                "power=" + power +
                ", speed=" + speed +
                '}';
    }
}
