package ru.ob11to.memory.task241;

public class ExampleT {
    public static void main(String[] args) {
        int x = 0;
        Object obj = new Object();
        ExampleT example = new ExampleT();
        example.action(obj);
    }

    public void action(Object parameter) {
        String str = parameter.toString();
        System.out.println(str);
    }
}