package net.keinr.util;

/**
 * Misc functions that don't really fit anywhere
 *
 * @author Orion Musselman (KeinR)
 * @version 1.1.0
 */

public final class Util {
    /** No instantiating static classes */
    private Util() {}

    /**
     * Gets the extension given a file name
     * @param  name File name
     * @return      the end extension, excluding the period '.'. For example, given "words.txt", would return "txt"
     */
    public static String getExtension(String name) {
        int i = name.lastIndexOf('.');
        if (i > 0) {
            return name.substring(i+1);
        }
        return "";
    }

    /**
     * Removes the extension from a given file name
     * @param  name File name
     * @return      The file name with the extension omitted. For example, given "words.txt", would return "words"
     */
    public static String removeExtension(String name) {
        int i = name.lastIndexOf('.');
        if (i > 0) {
            return name.substring(0, i);
        }
        return name;
    }

    /**
     * ifNN (if Not Null) returns a default if the given value
     * is null, else it returns the (not null) value
     * @param value the value to test
     * @param def the default value that will be returned if value is null
     * @return a non-null value; `value` if it's not null, else `def`
     */
    public static <T> T ifNN(T value, T def) {
        return value != null ? value : def;
    }
}
