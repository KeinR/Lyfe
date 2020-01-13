package net.keinr.lyfe;

class LocalSystem {
    private final String name;
    private final Dir homeDir;
    LocalSystem(String name, Dir homeDir) {
        this.homeDir = homeDir;
    }
    void execute(byte[] source) {
        // TODO execute the bytecode string
        // For now, this'll suffice
        Console.println("EXECUTING BYTECODE -> "+ new String(source));
    }
}
