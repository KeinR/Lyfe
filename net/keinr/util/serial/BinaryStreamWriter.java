package net.keinr.serial;

import java.util.List;
import java.util.ArrayList;

public class BinaryStreamWriter {

    private final List<Byte> source;

    private int bit = 7; // Position from least significant bit

    public ByteStreamWriter(int defaultCapacity) {
        this.source = new ArrayList<Byte>(defaultCapacity);
        source.add(0);
    }
    public ByteStreamWriter() {
        this(10);
    }

    public void writeBoolean(boolean val) {

    }

    public void writeBit(boolean is1) {

    }
    public void writeBit(int oneBitInt) {
        source.
    }
    private void addToByte(int val) {
        source.set(source.size()-1, source.get(source.size()-1) + (byte)val);
        if (--bit < 0) {
            source.add(0);
            bit = 7;
        }
    }
}
