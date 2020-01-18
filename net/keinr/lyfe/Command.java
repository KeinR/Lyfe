package net.keinr.lyfe;

class Command {
    private final Set<ByteString> switches = new HashMap<ByteString>;
    private final Set<ByteString> params = new HashMap<ByteString>;
    private final ByteString startPromt;
    
    Command(String startPromt) {
        this.startPromt = new ByteString(startPromt.getBytes());
    }

    void addSwitch(String s) {
        switches.add(new ByteString(s.getBytes()));
    }

    void addParam(String s) {
        params.add(new ByteString(s.getBytes()));
    }

    boolean compare(ByteString[] tokens) {
        if (!tokens[0].equalTo(startPromt)) return false;
        String param;
        final Map<String, String> parameters = new HashMap<String, String>();
        final Set<String> switches = new HashSet<String>();
        for (int i = 1; i < tokens.length; i++) {
            
        }
    }

    private interface Executable {
        void execute(Map<String, String> switchParamMap, Set<String> switchMap, String parameter);
    }
}
