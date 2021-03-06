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

    // reader и whiter другое поколение чтения/записи буферизиванный
    public static void doBufferedReaderAndWriterDemo() {
        File file = new File("D:/Lesson/JAVA/Lesson/GB/JAVA/LVL3/src/main/java/Lesson3/file.txt");
        // Добавление в конце
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))){
            bw.write("My name is GeekBrainds");
            bw.append("My name is GeekBrainds");
            bw.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            // работает со структурой файла.
            String line;
            while ((line=br.readLine() )!= null){
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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

    // класс отвечающий за облегчение работы с данными, может конвертировать данные сразу
    public static void doDataStreamDemo() {
        // курсор ставиться на начало.
        try (DataOutputStream out = new DataOutputStream(new
                FileOutputStream("demo.txt"))) {
            out.writeInt(128);
            out.writeLong(128L);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (DataInputStream in = new DataInputStream(new
                FileInputStream("demo.txt"))) {
            System.out.println(in.readInt());
            System.out.println(in.readLong());
        } catch (Exception e) {
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
