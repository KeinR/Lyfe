package net.keinr.util.config;

/**
 * Thrown by Properties to indicate that a given properties file has bad syntax
 *
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public class PropertiesSyntaxException extends PropertiesException {
    private final String lineRaw;
    private final int line, column;
    PropertiesSyntaxException(String message, String lineRaw, int line, int column) {
        super("Line "+line+", column "+column+": "+message);
        this.lineRaw = lineRaw;
        this.line = line;
        this.column = column;
    }
    public String getLineRaw() { return lineRaw; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
}
