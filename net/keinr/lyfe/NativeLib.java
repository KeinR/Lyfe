package net.keinr.lyfe;

import com.sun.jna.Library;

interface NativeLib extends Library {
    int speak();
}
