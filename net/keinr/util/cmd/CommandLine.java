package net.keinr.util.cmd;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

public class CommandLine {
    private Set<String> switches = new HashSet<String>();
    private Set<String> argSwitches = new HashSet<String>();
    public CommandLine() {}
    public void addSwitch(String sw) {
        switches.add(sw);
    }
    public void addAllSwitches(String... sws) {
        for (String sw : sws) switches.add(sw);
    }
    public void addArgSwitch(String sw) {
        argSwitches.add(sw);
    }
    public void addAllArgSwitches(String... sws) {
        for (String sw : sws) argSwitches.add(sw);
    }
    public Args parse(String[] args) throws InvalidSwitchException {
        final Set<String> availableSwitches = new HashSet<String>();
        final Map<String, String> availableArgSwitches = new HashMap<String, String>();
        String value = null;
        for (int i = 0; i < args.length; i++) {
            if (switches.contains(args[i])) {
                availableSwitches.add(args[i]);
            } else if (argSwitches.contains(args[i])) {
                if (i+1 >= args.length) throw new InvalidSwitchException("Arg switch \""+args[i]+"\" without value", args[i]);
                availableArgSwitches.put(args[i], args[i+1]);
                i++;
            } else if (value == null) {
                value = args[i];
            } else throw new InvalidSwitchException("Unrecognized switch \""+args[i]+"\"", args[i]);
        }
        if (value == null) throw new InvalidSwitchException("No value given for command", null);
        return new Args(availableSwitches, availableArgSwitches, value);
    }
}
