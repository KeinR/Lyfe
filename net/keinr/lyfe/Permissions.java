package net.keinr.lyfe;

class Permissions {
    // Constants used to access certain permissions
    // Now the cool thing about this is that you can use a short to represent
    // Three sets of  permissions: 5 bits * 3 permissions = 15 bits < 2 bytes
    static final int
        EXECUTE = 0,
        READ = 1,
        WRITE = 2,
        MODIFY = 3,
        FULL_CONTROL = 4
    ;
    // Did you know that primitive constants function as macros? I didn't either.
    static final int NUMBER_PERMISSIONS = 5; // Must not exceed 8, for 1 byte
    private static final int MAX_INDEX = NUMBER_PERMISSIONS - 1;
    //    2...    execute, read, write, modify, move, full control <--- represented in the last 6 bits in a java byte
    private byte perms;
    Permissions(byte perms) {
        set(perms);
    }
    /** For when initializing later, permissions set to all zero (all denied) */
    Permissions() {}

    boolean can(int permissionIndex) {
        assert permissionIndex <= MAX_INDEX : "Permission index out of bounds; must use constants";
        return ofIndex(permissionIndex) || ofIndex(FULL_CONTROL);
    }

    void set(byte value) {
        assert value <= 31 && value >= 0 : "Permissions range out of bounds";
        this.perms = value;
    }

    private boolean ofIndex(int index) {
        return (perms >>> MAX_INDEX - index & 1) == 1;
    }
}
