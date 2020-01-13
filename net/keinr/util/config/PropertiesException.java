package net.keinr.util.config;

/**
 * A simple wrapper class for all exceptions thrown by Properties
 * Note: stack trace is disabled, as it's liable to be thrown lots.
 *
 * @author Orion Musselman (KeinR)
 * @version 1.0.0
 */

public class PropertiesException extends Exception {
    PropertiesException(String message) {
        super(message);
    }
}
