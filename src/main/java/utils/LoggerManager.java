package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class LoggerManager {
    private Logger log;
    private static LoggerManager instance;
    private LoggerManager() {
        initialize();
    }

    public static LoggerManager getInstance() {
        return (instance == null) ? instance = new LoggerManager() : instance;
    }

    private void initialize() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File("log4j2.properties");
        context.setConfigLocation(file.toURI());
        log = LogManager.getLogger(LoggerManager.class);
    }

    private void logMessage(String type, String message) {
        switch (type) {
            case "debug" -> getInstance().log.debug(message);
            case "info" -> getInstance().log.info(message);
            case "warn" -> getInstance().log.warn(message);
            case "error" -> getInstance().log.error(message);
        }
    }

    public void debug(String message) {
        logMessage("debug", message);
    }

    public void info(String message) {
        logMessage("info", message);
    }

    public void warn(String message) {
        logMessage("warn", message);
    }

    public void error(String message) {
        logMessage("error", message);
    }
}
