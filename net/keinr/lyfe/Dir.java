package net.keinr.lyfe;

import java.util.Map;

class Dir extends LFile {
    private final String name, fullPath;
    private final Dir parent;
    private final Map<LFile> files;
    Dir(Dir parent, String name, Map<LFile> files) {
        this.name = name;
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
    public String getName() { return name; }

    @Override
    public void open() {
        
    }
}
