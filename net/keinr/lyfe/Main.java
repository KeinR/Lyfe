package net.keinr.lyfe;

import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.util.Random;

import net.keinr.util.config.Properties;
import net.keinr.util.config.PropertiesSyntaxException;

import static net.keinr.util.Ansi.RED;
import static net.keinr.util.Ansi.RESET;

public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) {

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
            System.exit(1);
        }

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            Logger.logTrace(e);
            Logger.error(t.getName()+": "+e);
        });

        long seed = random.nextLong();
        try {
            final Properties properties = Properties.load("resources/launch.ini");
            seed = properties.getLongSafe("default", "seed", seed);
        } catch (IOException e) {
            Logger.warn("Could not load launch.ini: "+e);
        } catch (PropertiesSyntaxException e) {
            Logger.warn("Failed to parse launch.ini: "+e.getMessage());
        }
        random.setSeed(seed);

        Program.start();
    }

    static Random getRandom() { return random; }

}
