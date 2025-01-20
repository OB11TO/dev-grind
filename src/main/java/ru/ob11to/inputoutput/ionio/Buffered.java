package ru.ob11to.inputoutput.ionio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Buffered {
    public static void main(String[] args) {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream("data/input.txt"));
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("data/output.txt"))) {
            output.write(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}