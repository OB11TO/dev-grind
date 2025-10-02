package ru.ob11to.architecture.patterns.visitor;

public record ProcessingResult<T> (T value, Exception exception) {

    public static <T> ProcessingResult<T> success(T value) {
        return new ProcessingResult<>(value, null);
    }

    public static <T> ProcessingResult<T> failure(Exception exception) {
        return new ProcessingResult<>(null, exception);
    }
    public boolean isSuccess() {
        return exception == null;
    }
}
