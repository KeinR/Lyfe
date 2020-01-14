package net.keinr.lyfe;

import java.util.Map;
import java.util.HashMap;

abstract class LFile {

    private String name;
    private final LocalSystem system;

    private final Permissions everyone = new Permissions(); // Literally everyone
    private final Permissions users = new Permissions(); // Registered users on the local system
    private final Permissions administrator = new Permissions(); // Registered admin users on the local system
    private final Map<Integer, Permissions> userPermissions = new HashMap<Integer, Permissions>(); // User-specific permissions

    LFile(String name, LocalSystem system, short permissions) {
        this.name = name;
        this.system = system;
        setPerms(permissions);
    }

    void setPerms(short permissions) {
        assert permissions >= 0 : "Invalid range for permissions";
        // First set is everyone, then users, and finally, administrator
        this.everyone.set(
           (permissions >> Permissions.NUMBER_PERMISSIONS * 2) & Permissions.NUMBER_PERMISSIONS);
        this.users.set(
            (permissions >> Permissions.NUMBER_PERMISSIONS) & Permissions.NUMBER_PERMISSIONS);
        this.administrator.set(
            permissions & Permissions.NUMBER_PERMISSIONS);
    }

    void setToken(int token, byte perms) {
        if (userPermissions.containsKey(token)) {
            userPermissions.replace(token, new Permissions(perms));
        } else {
            userPermissions.put(token, new Permissions(perms));
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
    void setName(String name) { this.name = name; }

    LocalSystem getSystem() { return system; }

    abstract byte[] read();
    abstract void write(byte[] data);
    abstract void execute();
}
