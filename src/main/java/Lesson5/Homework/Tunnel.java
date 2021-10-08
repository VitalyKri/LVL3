package Lesson5.Homework;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    Semaphore semaphore = new Semaphore(2);
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {

                System.out.println(c.getName() + " готовится к этапу(ждет): " +
                        description);
                long start = System.currentTimeMillis();
                semaphore.acquire();
                long delta = (System.currentTimeMillis() - start);
                System.out.println(c.getName() + " начал этап через "+delta +"мс.: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(c.getName() + " закончил этап: " +
                        description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
