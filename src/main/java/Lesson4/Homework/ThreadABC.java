package Lesson4.Homework;

public class ThreadABC {
    static String awaySymbol = "C";

    public void soutA() {
        int count = 5;
        synchronized(this) {
            while(count > 0) {
                if (awaySymbol.equals("C")) {
                    System.out.println("A");
                    awaySymbol = "A";
                    --count;
                    this.notifyAll();
                } else {
                    try {
                        this.wait();
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }
                }
            }

        }
    }

    public void soutB() {
        int count = 5;
        synchronized(this) {
            while(count > 0) {
                if (awaySymbol.equals("A")) {
                    System.out.println("B");
                    awaySymbol = "B";
                    --count;
                    this.notifyAll();
                } else {
                    try {
                        this.wait();
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }
                }
            }

        }
    }

    public void soutC() {
        int count = 5;
        synchronized(this) {
            while(count > 0) {
                if (awaySymbol.equals("B")) {
                    System.out.println("C");
                    awaySymbol = "C";
                    --count;
                    this.notifyAll();
                } else {
                    try {
                        this.wait();
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        ThreadABC threadABC = new ThreadABC();
        (new Thread(() -> {
            threadABC.soutA();
        })).start();
        (new Thread(() -> {
            threadABC.soutB();
        })).start();
        (new Thread(() -> {
            threadABC.soutC();
        })).start();
    }
}
