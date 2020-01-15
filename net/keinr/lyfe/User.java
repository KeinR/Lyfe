package net.keinr.lyfe;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import net.keinr.util.serial.ByteStreamWriter;

class User {
    private static final Path serializationPath = Paths.get("resources/serial/users.dat");
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
    LocalSystem getSystem() { return system; }
    int getToken() { return token; }
    boolean isAdmin() { return admin; }

    void setPassword(String password) { this.password = password; }

    static User getUser(String username) {
        return users.get(username);
    }

    static boolean userExists(String username) {
        return users.containsKey(username);
    }

    static void serialize() {

        final ByteStreamWriter stream = new ByteStreamWriter();

        int total = 0;
        for (String key : users.keySet()) {
            total++;
            final User user = users.get(key);
            stream.writeString(user.getUsername());
            stream.writeString(user.getPassword());
            stream.writeString(user.getSystem().getName());
            stream.writeBoolean(user.isAdmin());
            stream.writeInt(user.getToken());
        }
        assert total <= 255 : "Invalid total range";
        stream.insert(0, (byte)total);

        try {
            Files.createDirectories(serializationPath.getParent());
            Files.write(serializationPath, stream.getBytes());
        } catch (IOException e) {
            Logger.warn("Was unable to serialize user data to disk", e);
        }
    }
}
