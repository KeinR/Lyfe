package net.keinr.util.cmd;

import java.util.Set;
import java.util.Map;

public class Args {
    private final Set<String> switches;
    private final Map<String, String> argSwitches;
    private final String value;
    Args(Set<String> switches, Map<String, String> argSwitches, String value) {
        this.switches = switches;
        this.argSwitches = argSwitches;
        this.value = value;
    }
    public boolean hasSwitch(String sw) {
        return switches.contains(sw);
    }
    public boolean hasArgSwitch(String sw) {
        return argSwitches.containsKey(sw);
    }
    public String getArgValue(String sw) {
        return argSwitches.get(sw);
    }
    public String getValue() {
        return value;
    }
}
