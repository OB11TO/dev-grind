package ru.ob11to.inputoutput.ionio.task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConsoleChat {

    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        boolean run = true;
        List<String> log = new ArrayList<>();
        List<String> answers = readPhrases();
        int answersLength = answers.size();
        Random random = new Random();
        boolean writeAnswer = true;
        Scanner in = new Scanner(System.in);
        while (run) {
            String phrase = in.nextLine();
            log.add(phrase);
            if (Objects.equals(phrase, OUT)) {
                run = false;
                continue;
            } else if (Objects.equals(phrase, STOP)) {
                writeAnswer = false;
            } else if (Objects.equals(phrase, CONTINUE)) {
                writeAnswer = true;
                continue;
            }
            if (writeAnswer) {
                String answer = answers.get(random.nextInt(answersLength));
                System.out.println("Robot: " + answer);
                log.add(answer);
            }
        }
        saveLog(log);
    }

    private List<String> readPhrases() {
        List<String> phrases = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(botAnswers))) {
            br.lines().forEach(phrases::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phrases;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8, true))) {
            log.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/chatLogs.txt", "./data/answers.txt");
        cc.run();
    }
}