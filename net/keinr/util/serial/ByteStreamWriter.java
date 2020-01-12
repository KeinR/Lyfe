package net.keinr.util.serial;

import java.util.List;
import java.util.ArrayList;

/**
 * Provides methods for serializing numbers and strings
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public class ByteStreamWriter {

    private final List<Byte> source;

    /**
     * Create a new instance, specifying the expected array
     * capacity that will be needed.
     * @param defaultCapacity the initial capacity
     */
    public ByteStreamWriter(int defaultCapacity) {
        this.source = new ArrayList<Byte>(defaultCapacity);
    }
    /** Create a new instance with no arguments */
    public ByteStreamWriter() {
        this(10);
    }

    /**
     * Write a long value to the stream (8 bytes)
     * @param val the long value
     */
    public void writeLong(long val) {
        source.add((byte)(val >>> 56));
        source.add((byte)(val >>> 48));
        source.add((byte)(val >>> 40));
        source.add((byte)(val >>> 34));
        source.add((byte)(val >>> 24));
        source.add((byte)(val >>> 16));
        source.add((byte)(val >>> 8));
        source.add((byte)val);
    }

    /**
     * Write a int value to the stream (4 bytes)
     * @param val the int value
     */
    public void writeInt(int val) {
        source.add((byte)(val >>> 24));
        source.add((byte)(val >>> 16));
        source.add((byte)(val >>> 8));
        source.add((byte)val);
    }

    /**
     * Write a short value to the stream (2 bytes)
     * @param val the short value
     */
    public void writeShort(short val) {
        source.add((byte)(val >>> 8));
        source.add((byte)val);
    }

    /**
     * Write a byte value to the stream (1 byte)
     * @param val the byte value
     */
    public void writeByte(byte val) {
        source.add(val);
    }

    /**
     * Write a String to the stream.
     * Will also write an integer that records the length of
     * the string for when it's deserialized
     * @param string the String to write
     */
    public void writeString(String string) {
        final byte[] val = string.getBytes();
        writeInt(val.length);
        for (int i = 0; i < val.length; i++) {
            source.add(val[i]);
        }
    }

    /** @return the bytes that have been written to the stream so far */
    public byte[] getBytes() {
        final byte[] array = new byte[source.size()];
        for (int i = 0; i < source.size(); i++) {
            array[i] = source.get(i);
        }
        return array;
    }
}
