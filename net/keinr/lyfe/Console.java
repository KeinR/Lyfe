package net.keinr.lyfe;

import static net.keinr.util.Ansi.GREEN;
import static net.keinr.util.Ansi.YELLOW;
import static net.keinr.util.Ansi.RESET;

class Console {
    private static User currentUser;
    private static Dir currentDirectory;

    private static boolean hasSetup = false;

    static void setup(User user) {
        if (hasSetup) Logger.error("Tried to init Console more than once");
        hasSetup = true;
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
        System.out.println(GREEN+currentUser.getName()+"@"+currentUser.getSystem().getName()+RESET+" "+YELLOW+currentDirectory.getPath()+RESET);
        System.out.print("$ ");
    }

    private static void in() {
        while (true) {
            // TODO: Read user input
        }
    }
}

