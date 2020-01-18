package net.keinr.lyfe;

import java.io.IOException;

import static net.keinr.util.Ansi.GREEN;
import static net.keinr.util.Ansi.YELLOW;
import static net.keinr.util.Ansi.RESET;

class Console {
    private static User currentUser;
    private static Dir currentDirectory;

    private static boolean running = false;

    static void start(User user) {
        if (running) Logger.error("Tried to start Console more than once");
        running = true;
        currentDirectory = user.getSystem().getHomeDir();
        new Thread(Console::in, "Input").start();
    }

    static void print(String content) {
        remPromt();
        System.out.print(content);
        printPrompt();
    }

    static void println(String content) {
        print(content+"\n");
    }

    static void remPromt() {
        System.out.print("\r\033[A\033[J"); // http://www.termsys.demon.co.uk/vtansi.htm
    }

    static void printPrompt() {
        System.out.println(GREEN+currentUser.getUsername()+"@"+currentUser.getSystem().getName()+RESET+" "+YELLOW+currentDirectory.getPath()+RESET);
        System.out.print("$ ");
    }

    private static void in() {
        try {
            final byte[] buffer = new byte[500]; // I think that 500 bytes is more than necessary for cli input
            while (running) {
                // TODO: Read user input
                System.in.read(buffer);
                Program.process(buffer);
            }
        } catch (IOException e) {
            Logger.error("Input reader experienced a critical error", e);
        }
    }
}

