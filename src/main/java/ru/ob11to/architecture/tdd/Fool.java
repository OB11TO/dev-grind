package ru.ob11to.architecture.tdd;

import java.util.Scanner;

public class Fool {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        System.out.println("Игра FizzBuzz.");
        var startAt = 1;
        var input = new Scanner(System.in);

        while (startAt < 100) {
            System.out.println(setup(startAt));
            startAt++;
            var answer = input.nextLine();
            if (!answer.equals(setup(startAt))) {
                System.out.println("Ошибка. Начинай снова.");
                startAt = 1;
                continue;
            }
            startAt++;
        }
    }

    protected static String setup(int startAt) {
        return (startAt % 3 == 0 && startAt % 5 == 0)
                ? "FizzBuzz"
                : startAt % 3 == 0
                ? "Fizz"
                : startAt % 5 == 0
                ? "Buzz"
                : String.valueOf(startAt);
    }
}
