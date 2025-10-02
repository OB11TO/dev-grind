package ru.ob11to.architecture.patterns.visitor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String[] data = new String[]{"10", "25", "ошибка", "32", "нет"};

        Map<Boolean, List<ProcessingResult<Integer>>> preResultMap = Arrays.stream(data)
                .parallel()
                .map(Main::safeParse)
                .collect(Collectors.partitioningBy(
                        ProcessingResult::isSuccess
                ));
        List<ProcessingResult<Integer>> successResults = getProcessingResults(preResultMap);
        List<ProcessingResult<Integer>> failureExceptions = getProcessingExceptions(preResultMap);

        successResults.stream()
                .map(ProcessingResult::value)
                .forEach(System.out::println);

        failureExceptions.stream()
                .map(ProcessingResult::exception)
                .forEach(System.out::println);
    }

    private static List<ProcessingResult<Integer>> getProcessingExceptions(Map<Boolean, List<ProcessingResult<Integer>>> preResultMap) {
        return preResultMap.get(false);
    }

    private static List<ProcessingResult<Integer>> getProcessingResults(Map<Boolean, List<ProcessingResult<Integer>>> preResultMap) {
        return preResultMap.get(true);
    }

    private static ProcessingResult<Integer> safeParse(String s) {
        try {
            return ProcessingResult.success(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return ProcessingResult.failure(e);
        }
    }
}
