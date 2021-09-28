package Lesson1.Homework;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question2<T> {

    private T[] array;

    public Question2(T[] array) {
        this.array = array;
    }



    public List<T> getArrayAsList(){
        return Arrays.asList(this.array);
    }
}
