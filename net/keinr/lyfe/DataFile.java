package net.keinr.lyfe;

class DataFile extends LFile {
    private byte[] content;
    DataFile(byte[] content, String name, LocalSystem system, short permissions) {
        super(name, system, permissions);
        this.content = content;
    }

    @Override
    void write(byte[] data) {
        this.content = data;
    }

    @Override
    byte[] read() {
        return content;
    }

    /** Yes, you can execute anything- granted, with varying results. */
    @Override
    void execute() {
        getSystem().execute(content);
    }
}
