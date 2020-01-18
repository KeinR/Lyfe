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
    private final ByteString[] startPromtAliases;

    private final String usage;
    
    Command(String... aliases, String usage) {
        this.startPromtAliases = new ByteString[aliases.length];
        for (int i = 0; i < aliases.length; i++) {
            this.startPromtAliases[i] = new ByteString(aliases.getBytes());
        }
        this.usage = usage;
    }

    void addSwitch(String sw, String... aliases) {
        final ByteString start = new ByteString(a.getBytes());
        switches.add(new Param(start, start));
        for (String al : a) {
            switches.add(new ByteString(a.getBytes()));
        }
    }

    void addParam(String sw, String... aliases) {
        final ByteString start = new ByteString(a.getBytes());
        params.add(new Param(start, start));
        for (String al : a) {
            params.add(new ByteString(a.getBytes()));
        }
    }

    boolean tryExecute(ByteString[] tokens) {
        if (!tokens[0].equalTo(startPromt)) return false;
        String param;
        final Map<String, String> pams = new HashMap<String, String>();
        final Set<String> sws = new HashSet<String>();
        for (int i = 1; i < tokens.length; i++) {
            final ByteString t = tokens[i];
            if (t.indexEquals(0, DASH)) {
                ByteString target;
                if (t.indexEquals(1, DASH)) {
                    if ((target = params.get(t.sub(2))) != null) {
                        pams.add(target.toString());
                    }
                } else {
                    if ((target = switches.get(t.sub(1))) != null) {
                        sws.add(target.toString());
                    }
                }
            } else if (param != null) {
                Logger.warn("Switches must be prefixed with a '-', and parameters with '--'");
                System.out.println("Incorrect command usage: ");
                printUsage();
            } else {
                param = t.toString();
            }
        }
        return false; // TEMp
    }

    private void printUsage(int offset) {
        System.out.println(" ".repeat(offset)+usage);
    }

    private interface Executable {
        void execute(Map<String, String> switchParamMap, Set<String> switchMap, String parameter);
    }

    private class Param {
        private final ByteString name, controller;
        private Param(ByteString name, ByteString controller) {
            this.name = name;
            this.controller = controller;
        }
        @Override
        public boolean equals(Object obj) {
            return name.equals(obj);
        }
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
