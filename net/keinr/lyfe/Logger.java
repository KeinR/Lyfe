package net.keinr.lyfe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static net.keinr.util.Ansi.RED;
import static net.keinr.util.Ansi.YELLOW;
import static net.keinr.util.Ansi.GREEN;
import static net.keinr.util.Ansi.RESET;

class Logger {
    private static final Random random = new Random();

    static void log(String message) {

    }

    static int logTrace(Throwable t) {
        final int errId = random.nextInt(1000000000);
        System.err.println("\n"+getTimestamp()+" <"+errId+">\n");
        e.printStackTrace();
        return errId;
    }

    static void logTraceMessage(String message, Throwable t) {
        final int errId = random.nextInt(1000000000);
        System.err.println("\n"+getTimestamp()+" <"+errId+">\n");
        e.printStackTrace();
    }

    static void error(String message, Throwable e) {
        System.out.println("["+RED+"ERROR"+RESET+"] "+message+"\nPrinting stack trace....");
        e.printStackTrace();
    }

    static void warn(String message) {
        System.out.println("["+YELLOW+"WARNING"+RESET+"] "+message);
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
