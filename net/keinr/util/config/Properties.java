package net.keinr.util.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

/**
 * A class for loading & parsing .ini files
 *
 * @author Orion Musselman (KeinR)
 * @version 1.1.0
 */

public class Properties {

    private final Map<String, Map<String, String>> sections;

    private Properties(Map<String, Map<String, String>> sections) {
        this.sections = sections;
    }

    /**
     * Gets the string value of a given property of a given tag
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @throws PropertyNFException if the tag or property is undefined
     * @return property String value
     */
    public String getString(String tag, String name) throws PropertyNFException {
        final Map<String, String> tagMap = sections.get(tag);
        if (tagMap == null) throw new PropertyNFException(tag);
        final String value = tagMap.get(name);
        if (value == null) throw new PropertyNFException(tag, name);
        return value;
    }

    /**
     * Gets the int value of a given property of a given tag
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @throws PropertyNFException if the tag or property is undefined
     * @throws NumberFormatException if the property isn't an integer
     * @return property int value
     */
    public int getInt(String tag, String name) throws PropertyNFException {
        return Integer.parseInt(getString(tag, name));
    }

    /**
     * Gets the long value of a given property of a given tag
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @throws PropertyNFException if the tag or property is undefined
     * @throws NumberFormatException if the property isn't a long
     * @return property long value
     */
    public int getLong(String tag, String name) throws PropertyNFException {
        return Long.parseLong(getString(tag, name));
    }

    /**
     * Gets the double value of a given property of a given tag
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @throws PropertyNFException if the tag or property is undefined
     * @throws NumberFormatException if the property isn't an double
     * @return property double value
     */
    public double getDouble(String tag, String name) throws PropertyNFException {
        return Double.parseDouble(getString(tag, name));
    }

    /**
     * Gets the String value of a given property of a given tag,
     * however returns a given default, "deflt", if the tag or property
     * is undefined
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @param deflt value to return should the tag or property be undefined
     * @return property String value, or the given "deflt" if undefined
     */
    public String getStringSafe(String tag, String name, String deflt) {
        try {
            return getString(tag, name);
        } catch (PropertyNFException e) {
            return deflt;
        }
    }

    /**
     * Gets the int value of a given property of a given tag,
     * however returns a given default, "deflt", if the tag or property
     * is undefined
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @param deflt value to return should the tag or property be undefined
     * @throws NumberFormatException if the property isn't an int
     * @return property int value, or the given "deflt" if undefined
     */
    public int getIntSafe(String tag, String name, int deflt) {
        try {
            return getInt(tag, name);
        } catch (PropertyNFException e) {
            return deflt;
        }
    }

    /**
     * Gets the long value of a given property of a given tag,
     * however returns a given default, "deflt", if the tag or property
     * is undefined
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @param deflt value to return should the tag or property be undefined
     * @throws NumberFormatException if the property isn't a long
     * @return property long value, or the given "deflt" if undefined
     */
    public long getLongSafe(String tag, String name, long deflt) {
        try {
            return getLong(tag, name);
        } catch (PropertyNFException e) {
            return deflt;
        }
    }

    /**
     * Gets the double value of a given property of a given tag,
     * however returns a given default, "deflt", if the tag or property
     * is undefined
     * @param tag encapsulating tag; "default" if none
     * @param name property name
     * @param deflt value to return should the tag or property be undefined
     * @throws NumberFormatException if the property isn't an double
     * @return property double value, or the given "deflt" if undefined
     */
    public double getDoubleSafe(String tag, String name, double deflt) {
        try {
            return getDouble(tag, name);
        } catch (PropertyNFException e) {
            return deflt;
        }
    }

    /**
     * Generate a new Properties instance from a given ini file
     * @param path path to file
     * @throws IOException if there was a problem reading the file
     * @throws PropertiesSyntaxException if there was a syntax error detected in the given file
     * @return a newly constructed Properties object
     */
    public static Properties load(String path) throws IOException, PropertiesSyntaxException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            return new Properties(new Parser(br).run());
        }
    }

    /** Internally used to parse given ini files */
    private static class Parser {
        private final Map<String, Map<String, String>> sections = new HashMap<String, Map<String, String>>();
        private Map<String, String> current = new HashMap<String, String>();
        private String lineRaw, tagName = "default";
        private int i, line;
        private char focus;
        private final BufferedReader reader;
        private Parser(BufferedReader reader) {
            this.reader = reader;
        }
        private Map<String, Map<String, String>> run() throws IOException, PropertiesSyntaxException {
            main:while (nextLine()) {
                try {
                    // Remove starting whitespace
                    while (focus == ' ') {
                        if (!move()) continue main;
                    }
                    if (focus == ';') continue;
                    if (focus == '[') {
                        // Process section tag
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            if (!move()) throw new ParsingFailure("Expected closing bracket \"]\" for section tag");
                            if (focus == ']') break;
                            if (escapedEOT()) break;
                            sb.append(focus);
                        }
                        while (move()) {
                            if (focus != ' ') throw new ParsingFailure("Unexpected char after section tag \""+focus+"\"");
                        }
                        if (sb.length() == 0) throw new ParsingFailure("Empty section tag");
                        sections.put(tagName, current);
                        tagName = sb.toString();
                        current = new HashMap<String, String>();
                    } else {
                        // Get var name
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            if (focus == '=') break;
                            if (escapedEOT()) break;
                            sb.append(focus);
                            if (!move() || focus == ';') throw new ParsingFailure("Dangling variable \""+sb+"\" with no setting");
                        }
                        if (sb.length() == 0) throw new ParsingFailure("Variables must be of length greater than zero");
                        final String varName = sb.toString();
                        // Get var value
                        sb = new StringBuilder();
                        while (move()) {
                            if (focus == ';') break;
                            if (escapedEOT()) break;
                            sb.append(focus);
                        }
                        current.put(varName, sb.toString());
                    }
                } catch (ParsingFailure e) {
                    // Catch and add debug info
                    throw e.convert(lineRaw, line, i);
                }
            }
            sections.put(tagName, current);
            return sections;
        }
        /** If it's an escaped char, is it also at the end of the file? */
        private boolean escapedEOT() {
            return focus == '\\' && !move();
        }
        /** Move the cursor forward one @return true if successful */
        private boolean move() {
            if (++i < lineRaw.length()) {
                focus = lineRaw.charAt(i);
                return true;
            }
            focus = '\2';
            return false;
        }
        /** @return true if there's another line. Ignores empty lines. */
        private boolean nextLine() throws IOException {
            i = 0;
            line++;
            if ((lineRaw = reader.readLine()) != null) {
                // Ignore empty line
                if (lineRaw.length() == 0) return nextLine();
                focus = lineRaw.charAt(i);
                return true;
            }
            return false;
        }

        /** Used as intermediary so as to avoid having to give debug info on each throw */
        private static class ParsingFailure extends Throwable {
            private final String message;
            private ParsingFailure(String message) {
                this.message = message;
            }
            private PropertiesSyntaxException convert(String lineRaw, int line, int column) {
                return new PropertiesSyntaxException(message, lineRaw, line, column);
            }
        }
    }
}
