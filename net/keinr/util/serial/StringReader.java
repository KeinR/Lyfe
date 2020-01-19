package net.keinr.util.serial;

/**
 * Simple class for reading a string char by char
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public class StringReader {
    private final char[] chars;
    private int p = -1;
    private char focus;
    public StringReader(String source) {
        this.chars = source.toCharArray();
    }

    public char get() {
        return focus;
    }

    public char getNext() {
        move();
        return get();
    }

    public String readLine() {
        final int start = p;
        int truncate = 0;
        while (move()) {
            if (focus == '\n') {
                truncate = 1;
                break;
            } else if (focus == '\r') {
                move();
                truncate = 2;
                break;
            }
        }
        final char[] buff = new char[p-start-truncate];
        System.arraycopy(source, start, buff, 0, buff.length);
        return new String(buff);
    }

    public boolean move() {
        if (++p < chars.length) {
            focus = chars[i];
            return true;
        }
        focus = '\0';
        return false;
    }
}
