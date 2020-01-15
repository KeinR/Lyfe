package net.keinr.lyfe;

import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Random;

import net.keinr.util.Debug;

import static net.keinr.util.Ansi.RED;
import static net.keinr.util.Ansi.YELLOW;
import static net.keinr.util.Ansi.GREEN;
import static net.keinr.util.Ansi.RESET;

class Logger {
    private static final boolean DEBUG_ENABLED = true;

    private static final Debug dLog = new Debug("^net\\.keinr\\.lyfe\\.", "debug", DEBUG_ENABLED);
    private static final Random random = new Random();
    private static final PrintStream fLog;

    static {
        PrintStream ASN_fLog = null;
        try {
            ASN_fLog = new PrintStream(
                new BufferedOutputStream(
                    new FileOutputStream("lyfe.log", true)
                ),
                true
            );
        } catch (IOException e) {
            System.out.println("["+RED+"ERROR"+RESET+"] Was unable to set up log file");
            logTrace(e);
        }
        fLog = ASN_fLog;
    }

    static void logDebug(Object message) {
        dLog.logRouted(message, 1);
    }

    static void log(String message) {
        fLog.println("["+getTimestamp()+"] "+message);
    }

    static void logTrace(Throwable e) {
        System.err.println("\n"+getTimestamp()+"\n");
        e.printStackTrace();
    }

    static void logTraceMessage(String message, Throwable e) {
        final int errId = random.nextInt(1000000000);
        System.err.println("\n"+getTimestamp()+" <"+errId+">\n");
        e.printStackTrace();
        fLog.println("["+getTimestamp()+"] <"+errId+"> "+message);
    }

    static void error(String message, Throwable e) {
        System.out.println("["+RED+"ERROR"+RESET+"] "+message+": "+e);
        logTraceMessage(message, e);
        System.exit(1);
    }

    /** TODO consider renaming this to "die" so as to be more explicit as to it's function */
    static void error(String message) {
        System.out.println("["+RED+"ERROR"+RESET+"] "+message);
        log(message);
        System.exit(1);
    }

    static void warn(String message) {
        System.out.println("["+YELLOW+"WARNING"+RESET+"] "+message);
        log(message);
    }
    static void warn(String message, Throwable e) {
        System.out.println("["+YELLOW+"WARNING"+RESET+"] "+message);
        logTraceMessage(message, e);
    }

    /*ATM not used
    static void info(String message) {
        System.out.println("["+GREEN+"INFO"+RESET+"] "+message);
    }
    */

    private static String getTimestamp() {
        return "[" +
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
            .format(LocalDateTime.now())
            + "]";
    }
}
