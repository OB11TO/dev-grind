package ru.ob11to.inputoutput.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTest {

    public static final Logger LOG = LoggerFactory.getLogger(LoggingTest.class.getSimpleName());

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
    }
}
