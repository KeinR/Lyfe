package net.keinr.lyfe;

import com.sun.jna.Library;

interface NativeLib extends Library {
    String speak();
}
