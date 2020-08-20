package com.nate.util.logging;
import java.util.logging.Logger;

public class Log {

    private final String logfilePath;
    private final Logger logger;

    private Log(String path, Logger log) {
        logfilePath = path;
        logger = log;
    }

    public static void logMessage(String message) {


    }

}
