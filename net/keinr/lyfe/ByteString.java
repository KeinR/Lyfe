package net.keinr.lyfe;

import java.util.Arrays;

/** Byte array wrapper */

class ByteString {
    private final byte[] source;
    private int hashcode;
    ByteString(byte[] source, Options... options) {
        for (Options o : options) {
            switch (o) {
                case LOWERCASE:
                    for (int i = 0; i < source.length; i++) {
                        if (0x40 < source[i] && 0x5B > source[i]) {
                            source[i] += 0x20;
                        }
                    }
                    break;
                default: throw new IllegalArgumentException("Invalid option \""+o+"\"");
            }
        }
        this.source = source.clone();
    }
    byte at(int index) {
        return source[index];
    }
    int length() {
        return source.length;
    }
    boolean indexEquals(int index, byte comparison) {
        return index < source.length && source[index] == comparison;
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

    @Override
    public String toString() {
        return new String(source);
    }

    enum Options { LOWERCASE }
}
