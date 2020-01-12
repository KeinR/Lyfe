package net.keinr.lyfe.sdk;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.io.IOException;

import net.keinr.util.cmd.Args;
import net.keinr.util.cmd.CommandLine;
import net.keinr.util.cmd.InvalidSwitchException;
import net.keinr.util.Util;

public class Main {
    public static void main(String[] args) {
        final CommandLine cmd = new CommandLine();
        final String outSwitch = "--out";
        cmd.addArgSwitch(outSwitch);

        Args params = null;
        try {
            params = cmd.parse(args);
        } catch(InvalidSwitchException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


        final Path manifestPath = Paths.get(params.getValue());
        final Path homeDir = manifestPath.getParent();
        final Path out = homeDir.resolve(params.hasArgSwitch(outSwitch) ? params.getArgValue(outSwitch) : Util.removeExtension(manifestPath.getFileName().toString())+".dat");

        byte[] compiledData = null;
        try {
            compiledData = new Compiler(manifestPath, homeDir).parse();
        } catch (IOException e) {
            System.out.println("Failed to load files: "+e);
            System.exit(1);
        }

        try {
            Files.write(out, compiledData, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.out.println("Failed to write compiled bytes: "+e);
            System.exit(1);
        }
    }
}
