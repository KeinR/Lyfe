package net.keinr.lyfe;

import java.util.Map;

class Dir extends LFile {
    private final String fullPath;
    private final Dir parent;
    private final Map<LFile> files;
    Dir(Dir parent, Map<LFile> files, String name, LocalSystem system, short permissions) {
        super(name, system, permissions);
        this.files = files;
        this.parent = parent;
        if (parent != null) {
            if (parent.getParent() != null) {
                this.fullPath = parent.getPath()+"/"+this.name;
            } else {
                this.fullPath = "/"+this.name;
            }
        } else {
            this.fullPath = "~";
        }
    }

    Dir getParent() { return parent; }
    String getPath() { return fullPath; }
    LFile getChild(String name) { return files.get(name); }

    @Override
    public void write(byte[] data) {
        // Err: cannot write to directory
    }

    @Override
    public byte[] read() {
        // Err: cannot read from directory
        // ....
        // Or perhaps open the dir
        return null;
    }

    @Override
    public void execute() {
        // Err: cannot execute directory
        // ....
        // Or perhaps open the dir
    }
}
