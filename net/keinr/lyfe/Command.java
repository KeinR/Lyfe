package net.keinr.lyfe;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

class Command {

    // Macros
    private static final byte DASH = 0x2D;

    private final Map<ByteString, ByteString> switches = new HashMap<ByteString, ByteString>();
    private final Map<ByteString, ByteString> params = new HashMap<ByteString, ByteString>();
    private final boolean takeParam;
    private final ByteString[] startPromtAliases;
    private final Executable func;
    private final String usage;

    Command(Executable func, boolean takeParam, String usage, String name, String... aliases) {
        this.func = func;
        this.takeParam = takeParam;
        this.startPromtAliases = new ByteString[aliases.length+1];
        for (int i = 0; i < aliases.length; i++) {
            this.startPromtAliases[i] = new ByteString(aliases[i].getBytes());
        }
        this.startPromtAliases[aliases.length] = new ByteString(name.getBytes());
        this.usage = usage;
    }

    void addSwitch(String sw, String... aliases) {
        final ByteString ref = new ByteString(sw.getBytes());
        switches.put(ref, ref);
        for (String al : aliases) {
            switches.put(new ByteString(al.getBytes()), ref);
        }
    }

    void addParam(String sw, String... aliases) {
        final ByteString ref = new ByteString(sw.getBytes());
        params.put(ref, ref);
        for (String al : aliases) {
            params.put(new ByteString(al.getBytes()), ref);
        }
    }

    boolean tryExecute(Token[] tokens) {
        boolean eq = false;
        for (ByteString a : startPromtAliases) {
            if (tokens[0].get().equalTo(a)) {
                eq = true;
                break;
            }
        }
        if (!eq) return false;
        String param = null;
        final Map<String, String> pams = new HashMap<String, String>();
        final Set<String> sws = new HashSet<String>();
        for (int i = 1; i < tokens.length; i++) {
            final Token t = tokens[i];
            if (t.isFlag()) {
                ByteString target;
                if ((target = switches.get(t.get())) != null) {
                    sws.add(target.toString());
                } else {
                    Logger.warn("Invalid parameter \""+t+"\"");
                    System.out.println("Incorrect command usage: ");
                    printUsage();
                    break;
                }
            } else if (t.isParam()) {
                ByteString target;
                if ((target = switches.get(t.get())) != null) {
                    pams.put(target.toString(), tokens[++i].get().toString());
                } else {
                    Logger.warn("Invalid parameter \""+t+"\"");
                    System.out.println("Incorrect command usage: ");
                    printUsage();
                    break;
                }
            } else if (param != null || !takeParam) {
                Logger.warn("Switches must be prefixed with a '-', and parameters with '--'");
                System.out.println("Incorrect command usage: ");
                printUsage();
                break;
            } else {
                param = t.toString();
            }
        }
        func.execute(sws, pams, param);
        return true; // TEMp
    }

    private void printUsage() {
        printUsage(0);
    }
    private void printUsage(int offset) {
        System.out.println(" ".repeat(offset)+usage);
    }

    interface Executable {
        void execute(Set<String> switches, Map<String, String> params, String parameter);
    }
}
