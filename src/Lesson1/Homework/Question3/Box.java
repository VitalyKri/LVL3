package Lesson1.Homework.Question3;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Box <T extends Fruit> {
    ArrayList<T> arrayList;

    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public Box(T... array) {
        this.arrayList = new ArrayList<>(Arrays.asList(array));
    }

    public void addFruit(T fruit){
        this.arrayList.add(fruit);
    }

    public void addFruit(Box<T> box){
        ArrayList<T> anotherBox = box.getArrayList();
        Iterator<T> iteratorBox = anotherBox.iterator();
        while (iteratorBox.hasNext()){
            this.arrayList.add(iteratorBox.next());
            iteratorBox.remove();
        }
    }

    public double getWeight() {

      return arrayList.get(0).getWeight() *  arrayList.size();

    }

    public boolean compare(Box anotherBox){
        return anotherBox.getWeight() == getWeight();
    }
}
