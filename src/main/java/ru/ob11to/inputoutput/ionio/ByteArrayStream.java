package ru.ob11to.inputoutput.ionio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteArrayStream {

    public static void main(String[] args) {
        byte[] bytes = new byte[] {'J', 'a', 'v', 'a'};
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        int data;
        while ((data = stream.read()) != -1) {
            System.out.print((char) data);
        }

        System.out.println();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] bytes2 = "Message".getBytes();
        outStream.writeBytes(bytes2);
        System.out.println(outStream);
        try (FileOutputStream fileOutput = new FileOutputStream("data/message.txt")) {
            outStream.writeTo(fileOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
