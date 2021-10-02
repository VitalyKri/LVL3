package Lesson3.current;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serializator {
    public static byte[] perform(Car car) {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()){
            ObjectOutputStream objout = new ObjectOutputStream(bout);

            objout.writeObject(car);
            return bout.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Нет данных");
    }
}
