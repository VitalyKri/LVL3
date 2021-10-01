package Lesson3;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        ByteArrayOutputStream out = writeToByteArrayStream("Hello world");
        readToByteArrayStream(out.toByteArray());
    }
    static ByteArrayOutputStream writeToByteArrayStream(String message){
       // переменная доступна только в блоке try  от 00000000 до 11111111
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()){
            bout.write(message.getBytes());
            return bout;
        } catch (IOException e) {
           throw new RuntimeException(e);
        }

    }

    static void readToByteArrayStream(byte[] bytes){
        // переменная доступна только в блоке try  от 00000000 до 11111111
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)){
            int val;
            while ((val = bis.read())!=-1){
                System.out.print(val);
            }
            System.out.println();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void topic1(String message){
        ByteArrayOutputStream out = writeToByteArrayStream("Hello world");
        out.toByteArray();

    }
}
