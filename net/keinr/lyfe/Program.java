package net.keinr.lyfe;

class Program {

    static void start() {
        System.out.println("Ok");
        final LocalSystem system = new LocalSystem("def system", null);
        final User startUser = new User(system, false, "testUser", "qwerty");
        Console.start(startUser);
        while(true);
    }

    static void process(byte[] buffer) {
        if (buffer[0] == 'a') {
            System.out.println("Yo");
        }
        final InputReader stream = new InputReader(buffer);
        for (int i = 0; i < buffer.length && buffer[i] != 0x0; i++) {
            final ByteString data = stream.nextToken();
            if (data.equalTo())
        }
    }

    private static class InputReader {
        private final byte[] buffer;
        private int p = -1;
        private InputReader(byte[] buffer) {
            this.buffer = buffer;
        }
        private ByteString nextToken() {
            final int start = p;
            int length = 0;
            while (move() && buffer[i] != 0x20) {
                length++;
            }
            final byte[] result = new byte[length];
            System.arraycopy(buffer, start, result, 0, length);
            return new ByteString(result);
        }
        private boolean move() {
            return ++p < buffer.length && buffer[i] != 0x0;
        }
        private byte getNext() {
            move();
            return get();
        }
    }
}
