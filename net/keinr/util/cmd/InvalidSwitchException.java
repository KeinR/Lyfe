package net.keinr.util.cmd;

public class InvalidSwitchException extends Exception {
    private final String subjectSwitch;
    InvalidSwitchException(String message, String subjectSwitch) {
        super(message);
        this.subjectSwitch = subjectSwitch;
    }
    public String getSwitch() { return subjectSwitch; }
}
