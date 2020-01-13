package net.keinr.util.config;

/**
 * (Propterty Not Found Exception), thrown by Properties to indicate that
 * a requested property (or tag) doesn't exist
 *
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public class PropertyNFException extends PropertiesException {
    /** Can't find tag */
    PropertyNFException(String tag) {
        super("Tag \""+tag+"\" is not defined");
    }
    /** Can find tag, can't find var */
    PropertyNFException(String tag, String name) {
        super("Property \""+name+"\" of "+(tag.equals("default")?"[default tag]":"tag \""+tag+"\"")+" not found");
    }
}
