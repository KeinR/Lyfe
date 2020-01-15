package net.keinr.lyfe;

import java.util.Map;
import java.util.HashMap;

class LocalSystem {
    private final Map<String, LocalSystem> systems = new HashMap<String, LocalSystem>();

    private final String name;
    private final Dir homeDir;
    LocalSystem(String name, Dir homeDir) {
        this.name = name;
        this.homeDir = homeDir;
    }

    String getName() { return name; }
    Dir getHomeDir() { return homeDir; }

    void execute(byte[] source) {
        // TODO execute the bytecode string
        // For now, this'll suffice
        Console.println("EXECUTING BYTECODE -> "+ new String(source));
    }
}
