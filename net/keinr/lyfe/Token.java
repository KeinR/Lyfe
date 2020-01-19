package net.keinr.lyfe;

class Token {
    private final ByteString source;
    private final boolean flag, param;
    Token(byte[] source, boolean first) {
        if (source.length > 0 && source[0] == 0x2D) {
            int offset;
            if (source.length > 1 && source[1] == 0x2D) {
                offset = 2;
                this.flag = false;
                this.param = true;
            } else {
                offset = 1;
                this.flag = true;
                this.param = false;
            }
            final byte[] revSource = new byte[source.length-offset];
            System.arraycopy(source, offset, revSource, 0, revSource.length);
            this.source = new ByteString(revSource, ByteString.Options.LOWERCASE);
        } else {
            if (first) {
                this.source = new ByteString(source, ByteString.Options.LOWERCASE);
                System.out.println("FIRST _> "+this.source);
            } else {
                System.out.println("Second....");
                this.source = new ByteString(source);
            }
            this.flag = false;
            this.param = false;
        }
    }
    boolean isFlag() { return flag; }
    boolean isParam() { return param; }
    ByteString get() { return source; }
    @Override
    public String toString() {
        return source.toString();
    }
}
