package net.keinr.lyfe;

class Permissions {
    // Constants used to access certain permissions
    static final int
        EXECUTE = 0,
        READ = 1,
        WRITE = 2,
        MODIFY = 3,
        MOVE = 4,
        FULL_CONTROL = 5
    ;
    // Did you know that primitive constants function as macros? I didn't either.
    private static final int MAX_INDEX = 5; // Must not exceed 7, for 8 bits
    //    2...    execute, read, write, modify, move, full control <--- represented in the last 6 bits in a java byte
    private byte perms;
    Permissions(byte perms) {
        this.perms = perms;
    }

    boolean can(int permissionIndex) {
        assert permissionIndex <= MAX_INDEX : "Permission index out of bounds; must use constants";
        return ofIndex(permissionIndex) || ofIndex(FULL_CONTROL);
    }

    private boolean ofIndex(int index) {
        return (perms >>> MAX_INDEX - index & 1) == 1;
    }
}
