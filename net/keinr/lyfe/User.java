package net.keinr.lyfe;

import java.util.Map;
import java.util.HashMap;

class User {
    private static final Map<String, User> users = new HashMap<String, User>();

    private final LocalSystem system;
    private final String username;
    private final int token;
    private String password;
    private boolean admin;
    User(LocalSystem system, boolean admin, String username, String password) {
        this.system = system;
        this.admin = admin;
        this.username = username;
        this.password = password;
        this.token = Main.getRandom().nextInt();
        if (users.containsKey(username)) throw new IllegalArgumentException("User already exists in map");
        users.put(username, this);
    }
    String getPassword() { return password; }
    String getUsername() { return username; }
    String getSystem() { return system; }
    int getToken() { return token; }
    boolean isAdmin() { return admin; }

    void setPassword(String password) { this.password = password; }

    static User getUser(String username) {
        return users.get(username);
    }

    static boolean userExists(String username) {
        return users.containsKey(username);
    }
}
