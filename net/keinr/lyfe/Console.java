package net.keinr.lyfe;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

import static net.keinr.util.Ansi.GREEN;
import static net.keinr.util.Ansi.YELLOW;
import static net.keinr.util.Ansi.RESET;

class Console {
    private static final Command[] commands;

    private static User currentUser;
    private static Dir currentDirectory;

    private static boolean running = false;

    static {
        final List<Command> coms = new ArrayList<Command>();
        coms.add(new Command((s, p, v) -> {
            System.out.println("Hummmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        }, false, "good", "good"));
        commands = coms.toArray(Command[]::new);
    }

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
            final byte[] buffer = new byte[200]; // I think that 200 bytes is far more than necessary for cli input
            input:
            while (running) {
                // TODO: Read user input
                System.in.read(buffer);
                if (System.in.available() > 0) {
                    Logger.warn("Input exceeds buffer limit");
                    System.in.skip(System.in.available());
                    continue;
                }

                System.out.println("READ -> "+new String(buffer)+" ; ---");

                final List<Token> tokenList = new ArrayList<Token>();
                for (int i = 0, startcpy = 0; i < buffer.length && buffer[i] != 0x00 && buffer[i] != 0x0D && buffer[i] != 0x0A; i++) {
                    startcpy = i;
                    System.out.println("Fp..> "+Integer.toString(buffer[i], 16));
                    while (++i < buffer.length && buffer[i] != 0x00 && buffer[i] != 0x20 && buffer[i] != 0x0D) {
                        System.out.println("Fa..> "+Integer.toString(buffer[i], 16));
                    }
                    final int tLen = i - startcpy;
                    final byte[] tok = new byte[tLen];
                    System.arraycopy(buffer, startcpy, tok, 0, tLen);
                    tokenList.add(new Token(tok, startcpy == 0));
                    System.out.println("i = "+i);
                }
                final Token[] tokens = tokenList.toArray(Token[]::new);
                if (tokens.length == 0) continue;
                for (Command comm : commands) {
                    if (comm.tryExecute(tokens)) continue input;
                }
                System.out.println("Token# = "+tokens.length);
                Logger.warn("Command \""+tokens[0].get()+"\" not recognized");
            }
        } catch (IOException e) {
            Logger.error("Input reader experienced a critical error", e);
        }
    }
}

