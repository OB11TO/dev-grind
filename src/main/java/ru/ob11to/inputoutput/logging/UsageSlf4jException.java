package ru.ob11to.inputoutput.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageSlf4jException {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        try {
            throw new Exception("Not supported code");
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
    }
}
