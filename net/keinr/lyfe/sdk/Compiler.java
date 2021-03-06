package net.keinr.lyfe.sdk;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import net.keinr.util.serial.ByteStreamWriter;
import net.keinr.util.serial.StringReader;

public class Compiler {

    private ByteStreamWriter stream;
    private StringReader reader;
    private int i;

    private final Path manifestPath;
    private final Path homeDir;

    Compiler(Path manifestPath, Path homeDir) {
        this.manifestPath = manifestPath;
        this.homeDir = homeDir;
    }

    byte[] parse() throws IOException {
        final StringBuilder build = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(manifestPath.toFile()))) {
            for(String file; (file = br.readLine()) != null; ) {
                build.append(Files.readString(homeDir.resolve(file)));
            }
        }
        stream = new ByteStreamWriter();
        reader = new StringReader(build.toString());
        i = 0;
        return doParse();
    }

    private byte[] doParse() {

        while (reader.move()) {
            
        }

        return new byte[0];
    }
}
