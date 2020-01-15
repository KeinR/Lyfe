package net.keinr.lyfe;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface NativeLib extends Library {
    NativeLib INSTANCE = (NativeLib) Native.loadLibrary("test", NativeLib.class);
    int test(int arg);
}
