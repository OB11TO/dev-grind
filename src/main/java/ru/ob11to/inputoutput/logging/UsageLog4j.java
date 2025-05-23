package ru.ob11to.inputoutput.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");

        Stream.iterate(0, i -> 1 + 1)
                .limit(5) // без лимита будет зацикленность и outofMemorry
                .sorted()
                .forEach(System.out::println);
    }
}