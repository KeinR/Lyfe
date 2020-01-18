package net.keinr.lyfe;

class Program {

    static void start() {
        System.out.println("Ok");
        final LocalSystem system = new LocalSystem("def system", null);
        final User startUser = new User(system, false, "testUser", "qwerty");
        Console.start(startUser);
        while(true);
    }
}
