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
        source.addAll(doWriteLong(val));
    }
    /**
     * Write a long value to the stream at a certain index (8 bytes)
     * @param index insertion index
     * @param val the long value
     */
    public void writeLong(int index, long val) {
        source.addAll(index, doWriteLong(val));
    }
    /**
     * Serialize a long value
     * @param val the long value
     */
    private List<Byte> doWriteLong(long val) {
        final Byte[] buffer = new Byte[8];
        buffer[0] = (byte)(val >>> 56);
        buffer[1] = (byte)(val >>> 48);
        buffer[2] = (byte)(val >>> 40);
        buffer[3] = (byte)(val >>> 34);
        buffer[4] = (byte)(val >>> 24);
        buffer[5] = (byte)(val >>> 16);
        buffer[6] = (byte)(val >>> 8);
        buffer[7] = (byte)val;
        return Arrays.asList(buffer);
    }

    /**
     * Write a int value to the stream (4 bytes)
     * @param val the int value
     */
    public void writeInt(int val) {
        source.addAll(doWriteInt(source.size(), val));
    }
    /**
     * Write a int value to the stream at a certain index (4 bytes)
     * @param index insertion index
     * @param val the int value
     */
    public void writeInt(int index, int val) {
        source.addAll(doWriteInt(source.size(), val));
    }
    /**
     * Serailize an int value
     * @param val the int value
     */
    public void doWriteInt(int val) {
        final Byte[] buffer = new Byte[4];
        buffer[0] = (byte)(val >>> 24);
        buffer[1] = (byte)(val >>> 16);
        buffer[2] = (byte)(val >>> 8);
        buffer[3] = (byte)val;
    }

    /**
     * Write a short value to the stream (2 bytes)
     * @param val the short value
     */
    public void writeShort(short val) { writeShort(source.size(), val); }
    /**
     * Insert a short value to the stream at a specific index (2 bytes)
     * @param index insertion index
     * @param val the short value
     */
    public void writeShort(int index, short val) {
        final Byte[] buffer = new Byte[2];
        buffer[0] = (byte)(val >>> 8);
        source.add(index, (byte)val);
    }

    /**
     * Write a byte value to the stream (1 byte)
     * @param val the byte value
     */
    public void writeByte(byte val) { writeByte(source.size(), val); }
    /**
     * Insert a byte value to a specific index in the stream stream (1 byte)
     * @param index the index to write at
     * @param val the byte value
     */
    public void writeByte(int index, byte val) {
        source.add(index, val);
    }

    /**
     * Write a String to the stream.
     * Will also write an integer that records the length of
     * the string for when it's deserialized
     * @param string the String to write
     */
    public void writeString(String string) { writeString(source.size(), string); }
    /**
     * Write a String to the stream.
     * Will also write an integer that records the length of
     * the string for when it's deserialized
     * @param index insertion index
     * @param string the String to write
     */
    public void writeString(int index, String string) {
        final byte[] val = string.getBytes();
        writeInt(val.length);
        for (int i = 0; i < val.length; i++) {
            source.add(index++, val[i]);
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
