package Lesson1.Homework;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

public class Question1<T> {

    private T[] array;

    public Question1(T[] array) {
        this.array = array;
    }

    public void info(){
        System.out.println(Arrays.asList(array));
    }

    public void swapElements(int index1Element,int index2Element){
        if (index1Element<0 && index1Element>array.length || index2Element<0 && index2Element>array.length )
        {
            return;
        }
        T element;
        element = array[index1Element];
        array[index1Element] = array[index2Element];
        array[index2Element] = element;
    }
}
