package Lesson3;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.sql.SQLOutput;

public class FileTopic {
    public static void main(String[] args) {
      topic1();
        topicDoFileStreamDemo();
        doDataStreamDemo();
    }
    // путь к файлу несуществующему. Работа с файловой системой.
    public static void topic1() {
        File file = new File("/пример/f.txt");// не содержит самого файла, отображает потенциальный файл
        // в файловой системе.
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
    }

    // есть ограничение в конструкторе size
    public static void doBufferedStreamDemo() {
        // курсор ставиться на начало.
        ByteArrayOutputStream bout = writeToByteArrayStream("hello, world");
        try (BufferedOutputStream bufout = new BufferedOutputStream(bout)) {
            bufout.write("hello, world!!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // запись
        // читает.
        try (BufferedInputStream dis = new BufferedInputStream(new ByteArrayInputStream(bout.toByteArray()))) {
            int val;
            // возвращает целиком

            while ((val = dis.read())!= -1){
                System.out.print((char) val);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // класс отвечающий за облегчение работы с данными, может конвертировать данные
    public static void doDataStreamDemo() {
        // курсор ставиться на начало.
        ByteArrayOutputStream bout = writeToByteArrayStream("hello, world");
        try (DataOutputStream dos = new DataOutputStream(bout)) {
            dos.writeUTF("hello, world!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // запись
        // читает.
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bout.toByteArray()))) {
            int val;
            // возвращает целиком

            System.out.print( dis.readUTF());
            while ((val = dis.read())!= -1){
                System.out.print((char) val);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // работа с файлом
    public static void topicDoFileStreamDemo() {
        // курсор ставиться на начало.
        File file = new File("D:/Lesson/JAVA/Lesson/GB/JAVA/LVL3/src/main/java/Lesson3/file.txt");
        try (FileInputStream fis = new FileInputStream(file)) {
          int val;
          while ((val = fis.read())!= -1){
              System.out.print((char) val);
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // запись
        // курсор ставиться на начало append ставить в конец
        try (FileOutputStream fos = new FileOutputStream(file,true)) {
            fos.write("helfd".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
