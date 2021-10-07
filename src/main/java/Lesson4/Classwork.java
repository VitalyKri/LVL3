package Lesson4;

public class Classwork {


    public static void main(String[] args) {

       Thread t1 = new Thread(() -> {
           System.out.println("");
       });
    }


}

final class StaticCounterThreadSafe{
    private static Integer val;

    public  static int getVal() {
        synchronized (val){
            return val;
        }
    }

    public synchronized void incriment(){
        val++;
    }
}