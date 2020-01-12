package net.keinr.lyfe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import static net.keinr.util.Ansi.RED;
import static net.keinr.util.Ansi.RESET;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println("Great");

        try {
            System.setErr(
                new PrintStream(
                    new BufferedOutputStream(
                        new FileOutputStream("lyfe.trace.log", true)
                    ),
                    true
                )
            );
        } catch (IOException e) {
            System.out.println("Failed to redirect std err stream to dump file");
            System.exit("Failed to redirect std err stream to dump file", e);
        }

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            Logger.logTrace(e);
            Logger.error(e.getName()+": "+e);
            Logger.warn("Program terminating...");
            System.exit(1);
        });

        new Thread(Program::start, "Main").start();
    }
    public static void main(String[] args) { launch(args); }
}
