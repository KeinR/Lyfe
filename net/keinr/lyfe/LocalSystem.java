package net.keinr.lyfe;

class LocalSystem {
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
