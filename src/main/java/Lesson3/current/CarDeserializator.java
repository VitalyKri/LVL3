package Lesson3.current;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CarDeserializator {
    static Car perform(byte[] bytes){
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)){
            ObjectInputStream objout = new ObjectInputStream(bis);

            return (Car) objout.readObject();
        } catch (IOException|ClassNotFoundException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Нет данных");
    }
}
