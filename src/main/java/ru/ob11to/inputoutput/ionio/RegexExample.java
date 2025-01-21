package ru.ob11to.inputoutput.ionio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class RegexExample {
    public static void main(String[] args) {
//        extracted();
//        extracted1();
//        extracted2();
//        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        Pattern pattern = Pattern.compile("\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b");
        String text = "24.04.1987 11.11.111111 99.99.99991 99.99.9999 99999999 0000.00.00";
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println("Найдено совпадение: " + matcher.group());
        }
    }

    private static void extracted2() {
        String string = "123+=-456:/789";
        String[] result = string.split("\\D+");
        System.out.println(Arrays.toString(result));
    }

    private static void extracted1() {
        Pattern pattern = Pattern.compile("11");
        String text = "111111";
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println("Найдено совпадение. iStart: " + matcher.start()
                    + " iEnd: " + matcher.end());
            System.out.println("Найдено совпадение: " + matcher.group());
        }
    }

    private static void extracted() {
        Pattern pattern = Pattern.compile("Я учусь на Job4j");

        String textOne = "Я учусь на Job4j";
        Matcher matcherOne = pattern.matcher(textOne);
        boolean isPresentOne = matcherOne.matches();
        System.out.println(isPresentOne);

        String textTwo = "Я учусь на курсе Job4j";
        Matcher matcherTwo = pattern.matcher(textTwo);
        boolean isPresentTwo = matcherTwo.matches();
        System.out.println(isPresentTwo);

        Pattern pattern2 = Pattern.compile("Job4j");
//        Pattern pattern2 = Pattern.compile("Job4j", Pattern.CASE_INSENSITIVE);  не важен регистр

        String textOne2 = "Job4j";
        Matcher matcherOne2 = pattern2.matcher(textOne2);
        boolean isPresentOne2 = matcherOne2.matches();
        System.out.println(isPresentOne2);

        String textTwo2 = "job4j";
        Matcher matcherTwo2 = pattern2.matcher(textTwo2);
        boolean isPresentTwo2 = matcherTwo2.matches();
        System.out.println(isPresentTwo2);
    }
}