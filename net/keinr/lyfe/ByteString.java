package net.keinr.lyfe;

import java.util.Arrays;

/** Byte array wrapper */

class ByteString {
    private final byte[] source;
    private int hashcode;
    ByteString(byte[] source, Option... options) {
        this.source = source;
        for (Option o : options) {
            switch (o) {
                case LOWERCASE:
                    for (int i = 0; i < source.length; i++) {
                        if (0x40 < source[i] && 0x5B > source[i]) {
                            source[i] += 0x20;
                        }
                    }
                    break;
                default: throw new IllegalArgumentException("Invalid switch \""+o+"\"");
            }
        }
    }
    byte atIndex(int index) {
        return source[index];
    }
    byte[] getBytes() {
        // Don't share the original copy...
        // Or perhaps do, I dunno' dude
        final byte[] request = new byte[source.length];
        System.arraycopy(source, 0, request, 0, source.length);
        return request;
    }
    boolean equalTo(ByteString data) {
        if (data.source.length != this.source.length) return false;
        for (int i = 0; i < this.source.length; i++) {
            if (data.source[i] != this.source[i]) return false;
        }
        return true;
    }
    @Override
    public int hashCode() {
        if (hashcode == 0) {
            hashcode = Arrays.hashCode(source);
        }
        return hashcode;
    }
    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(ByteString.class) && obj.hashCode() == hashCode();
    }

    enum Option { LOWERCASE }
}
