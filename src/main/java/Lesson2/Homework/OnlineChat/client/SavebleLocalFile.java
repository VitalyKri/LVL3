package Lesson2.Homework.OnlineChat.client;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface SavebleLocalFile {

    BufferedWriter writeToStream();
    void readingTheStream();

}
