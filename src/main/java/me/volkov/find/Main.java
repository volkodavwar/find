package me.volkov.find;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser(args);
        parser.parse();

        Find finder = new Find(parser.getDirectory(), parser.isRecursive());

        List<File> files = finder.find(parser.getFilename());
        if (files.isEmpty()) {
            System.out.println("Matches with filename (" + parser.getFilename() + ") not found.");
        } else {
            System.out.println("Found file(s):");
            files.forEach(file -> System.out.println("\t" + file.getAbsolutePath()));
        }
    }
}
