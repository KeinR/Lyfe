package net.keinr.util.serial;

/**
 * Provides methods for deserializing what ByteStreamWriter wrote
 * do be careful, for if you read/write incorrectly you can get some
 * really wacky numbers.
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public class ByteStreamReader {

    private final byte[] source;
    private int p = -1;
    private byte focus;

    /**
     * Create a new ByteStreamReader from a byte array
     * @param source byte array to read
     */
    public ByteStreamReader(byte[] source) {
        this.source = source;
    }

    /**
     * Read a long from the stream reading 8 bytes
     * @return long value
     */
    public long readLong() {
        long value = 0;
        value += (long)readUnsigned() << 56;
        value += (long)readUnsigned() << 48;
        value += (long)readUnsigned() << 40;
        value += (long)readUnsigned() << 32;
        value += (long)readUnsigned() << 24;
        value += (long)readUnsigned() << 16;
        value += (long)readUnsigned() << 8;
        value += (long)readUnsigned();
        return value;
    }

    /**
     * Read a int from the stream reading 4 bytes
     * @return int value
     */
    public int readInt() {
        int value = 0;
        value += readUnsigned() << 24;
        value += readUnsigned() << 16;
        value += readUnsigned() << 8;
        value += readUnsigned();
        return value;
    }

    /**
     * Read a short from the stream reading 2 bytes
     * @return short value
     */
    public short readShort(short val) {
        short value = 0;
        value += (short)(readUnsigned() << 8);
        value += (short)readUnsigned();
        return value;
    }

    /**
     * Converts what's read by readStringRaw() to a string
     * @return String value
     */
    public String readString() {
        return new String(readStringRaw());
    }

    /**
     * reads and integer value, then returns all bytes for that length.
     * for example, if it reads an int value of 100, it'd return
     * the next 100 bytes (minus the int that was read)
     * @return string raw
     */
    public byte[] readStringRaw() {
        final byte[] data = new byte[readInt()];
        for (int i = 0; i < data.length; i++) {
            data[i] = read();
        }
        return data;
    }

    /** @return the current byte in the stream */
    public byte get() {
        return focus;
    }

    /** @return the next byte in the stream */
    public byte read() {
        move();
        return get();
    }

    /** @return the current byte in the stream as it's UNSIGNED int value */
    public int getUnsigned() {
        return focus & 0xFF;
    }

    /** @return the next byte in the stream as it's UNSIGNED int value */
    public int readUnsigned() {
        move();
        return getUnsigned();
    }

    /**
     * Moves the cursor a set distance, in a positive or negative direction.
     * Is O(1), in case you were wondering...
     * @param distance the distance to jump; can be postive or negative
     * @return true if the stream hasn't ended as a result of the jump
     */
    public boolean jump(int distance) {
        p += distance;
        if (p >= 0 && p < source.length) {
            focus = source[p];
            return true;
        }
        focus = 0;
        return false;
    }

    /**
     * Move the pointer one forward in the stream.
     * @return true if the stream hasn't ended
     */
    public boolean move() {
        if (++p < source.length) {
            focus = source[p];
            return true;
        }
        focus = 0;
        return false;
    }
}
