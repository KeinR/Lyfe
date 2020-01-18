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
            final byte[] buffer = new byte[300]; // I think that 300 bytes is more than necessary for cli input
            input:
            while (running) {
                // TODO: Read user input
                System.in.read(buffer);
                if (System.in.available() > 0) {
                    Logger.warn("Input exceeds buffer limit");
                    continue;
                }

                final List<ByteString> tokenList = new ArrayList<ByteString>();
                for (int i = 0, startcpy = 0; i < buffer.length && buffer[i] != 0xA; i++) {
                    startcpy = i;
                    while (++i < buffer.length && buffer[i] != 0xA && buffer[i] != 0x20);
                    final int tLen = i - startcpy;
                    final byte[] tok = new byte[tLen];
                    System.arraycopy(buffer, startcpy, tok, 0, tLen);
                    tokenList.add(new ByteString(tok));
                }
                final ByteString[] tokens = tokenList.toArray(ByteString[]::new);
                if (tokens.length == 0) continue;
                for (Command comm : commands) {
                    if (comm.tryExecute(tokens)) continue input;
                }
                Logger.warn("Command \""+tokens[0]+"\" not recognized");
            }
        } catch (IOException e) {
            Logger.error("Input reader experienced a critical error", e);
        }
    }

    private static class InputReader {
        private final byte[] buffer;
        private int p = -1;
        private InputReader(byte[] buffer) {
            this.buffer = buffer;
        }
        private ByteString nextToken() {
            final int start = p;
            int length = 0;
            while (move() && buffer[p] != 0x20) {
                length++;
            }
            final byte[] result = new byte[length];
            System.arraycopy(buffer, start, result, 0, length);
            return new ByteString(result);
        }
        private boolean move() {
            return ++p < buffer.length && buffer[p] != 0x0;
        }
        private byte get() {
            return buffer[p];
        }
        private byte getNext() {
            move();
            return get();
        }
    }
}

