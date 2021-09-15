package com.phantomartist.email;

/**
 * Implement with your favourite logger.
 */
public class Logger {

    public static void logDebug(final String message) {
        System.out.println("<<DEBUG>>" + message);
    }

    public static void logInfo(final String message) {
        System.out.println(message);
    }

    public static void logError(final String message, final Exception e) {
        System.err.println(message);
        if (e != null) {
            e.printStackTrace();
        }
    }

    public static void logError(final String message) {
        logError(message, null);
    }
}
