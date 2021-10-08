package Lesson5.Homework;

import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private static CyclicBarrier cyclicWait;
    private int place = 0;
    public String getName() {
        return name;
    }

    public static void setCyclicWait(CyclicBarrier cyclicWait ) {
        Car.cyclicWait = cyclicWait;
    }


    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));

            System.out.println(this.name + " готов");
            cyclicWait.await();
            cyclicWait.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            race.finish(this);
            cyclicWait.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
