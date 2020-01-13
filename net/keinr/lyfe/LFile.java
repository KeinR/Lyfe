package net.keinr.lyfe;

class LFile {

    private byte[] raw;
    private String name;
    private final LocalSystem system;

    private final Permissions everyone; // Literally everyone
    private final Permissions users; // Registered users on the local system
    private final Permissions administrator; // Registered admin users on the local system
    private final Map<Integer, Permissions> userPermissions = new HashMap<Integer, Permissions>(); // User-specific permissions

    LFile(byte[] raw, String name, LocalSystem system, byte everyonePerms, byte userPerms, byte administratorPerms) {
        this.raw = raw;
        this.name = name;
        this.system = system;
        this.everyone = new Permissions(everyonePerms);
        this.users = new Permissions(userPerms);
        this.administrator = new Permissions(administratorPerms);
    }

    void setToken(int token, Permissions perms) {
        if (userPermissions.containsKey(token)) {
            userPermissions.replace(token, perms);
        } else {
            userPermissions.put(token, perms);
        }
    }

    /** @param permission see the package-private int constants in Permissions */
    boolean can(User user, int permission) {
        if (userPermissions.get(user.getToken()) != null) {
            return userPermissions.get(user.getToken()).can(permission);
        } else if (user.getSystem() == system) {
            if (user.isAdmin()) {
                return administrator.can(permission);
            }
            return users.can(permission);
        }
        return everyone.can(permission);
    }

    String getName() { return name; }
    void setName(String name) { return this.name = name; }

    byte[] getRaw() { return raw; }
    void write(String data) { this.raw = data.getBytes(); }

    /** Yes- you can execute anything- granted, with varying results. */
    void execute() {
        system.execute(raw);
    }
}
