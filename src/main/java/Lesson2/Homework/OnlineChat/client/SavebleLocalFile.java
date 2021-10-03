package Lesson2.Homework.OnlineChat.client;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public interface SavebleLocalFile {

    BufferedOutputStream writeToStream();
    void readingTheStream(BufferedInputStream bis);

}
