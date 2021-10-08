package Lesson5.Homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
    private ArrayList<Stage> stages;
    private int countFinish = 0;

    public void finish(Car car) {
        synchronized (this) {
            countFinish++;
            System.out.println("Машина " + car.getName() + " финишировала - " + countFinish);
            if (countFinish == 1) {
                System.out.println(car.getName() + " - Победитель!!! УРА!!!");
            }
        }

    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
