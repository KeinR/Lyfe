package net.keinr.util;

import java.util.List;
import java.util.ArrayList;

/**
 * Provides methods for converting byte arrays to lists and back
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public final class ByteArrays {
    public static List<Byte> asList(byte[] array) {
        final List<Byte> list = new ArrayList<Byte>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public static byte[] asArray(List<Byte> list) {
        final byte[] array = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
